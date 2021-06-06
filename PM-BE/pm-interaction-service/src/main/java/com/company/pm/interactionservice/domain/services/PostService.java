package com.company.pm.interactionservice.domain.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.company.pm.common.enumeration.RelStatus;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.companyservice.domain.repositories.CompanyRepository;
import com.company.pm.domain.Attachment;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.domain.repositories.AttachmentRepository;
import com.company.pm.interactionservice.domain.repositories.PostRepository;
import com.company.pm.interactionservice.domain.services.dto.PostDTO;
import com.company.pm.interactionservice.domain.services.mapper.PostMapper;
import com.company.pm.socialservice.domain.repositories.FollowRepository;
import com.company.pm.socialservice.domain.repositories.RelationshipRepository;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    
    private static final String ENTITY_NAME = "post";
    
    private static final String UPLOAD_FOLDER = "personal-management-collection";
    
    private final PostMapper mapper;
    
    private final Cloudinary cloudinary;
    
    private final PostRepository postRepository;
    
    private final UserRepository userRepository;
    
    private final RelationshipRepository relationshipRepository;
    
    private final FollowRepository followRepository;
    
    private final CompanyRepository companyRepository;
    
    private final AttachmentRepository attachmentRepository;
    
    @Transactional(readOnly = true)
    public Flux<Post> getPostsOfUser(String userId) {
        return postRepository.findByAuthorAndCompanyIsNull(userId);
    }
    
    @Transactional(readOnly = true)
    public Mono<Post> getPostOfUser(String userId, Long postId) {
        return postRepository.findByIdAndAuthorAndCompanyIsNull(postId, userId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    
    @Transactional(readOnly = true)
    public Flux<Post> getPostsOfUserByAnotherUser(String userId, String anotherId) {
        return relationshipRepository.findByRequesterIdAndAddresseeId(userId, anotherId)
            .flatMapMany(relationship -> {
                if (!relationship.getStatus().equals(RelStatus.BLOCKED)) {
                    if (relationship.getStatus().equals(RelStatus.ACCEPTED)) {
                        return postRepository.findVisiblePostsOfUser(userId);
                    } else {
                        return postRepository.findPublicPostsOfUser(userId);
                    }
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                }
            });
    }
    
    @Transactional(readOnly = true)
    public Mono<Post> getPostOfUserByAnotherUser(String userId, String anotherId, Long postId) {
        return relationshipRepository.findByRequesterIdAndAddresseeId(userId, anotherId)
            .flatMap(relationship -> {
                if (!relationship.getStatus().equals(RelStatus.BLOCKED)) {
                    if (relationship.getStatus().equals(RelStatus.ACCEPTED)) {
                        return postRepository.findVisiblePostByIdOfUser(userId, postId);
                    } else {
                        return postRepository.findPublicPostByIdOfUser(userId, postId);
                    }
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.FORBIDDEN));
                }
            });
    }
    
    @Transactional(readOnly = true)
    public Flux<Post> getPostsOfCompanyByAdmin(String userId, Long companyId) {
        return postRepository.findByAuthorAndCompany(userId, companyId);
    }
    
    @Transactional(readOnly = true)
    public Mono<Post> getPostOfCompanyByAdmin(String userId, Long postId, Long companyId) {
        return postRepository.findByIdAndAuthorAndCompany(postId, userId, companyId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    
    @Transactional(readOnly = true)
    public Flux<Post> getPublicPostsOfCompany(Long companyId) {
        return postRepository.findPublicPostsOfCompany(companyId);
    }
    
    @Transactional(readOnly = true)
    public Mono<Post> getPublicPostOfCompany(Long companyId, Long postId) {
        return postRepository.findPublicPostByIdOfCompany(companyId, postId)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }
    
    @Transactional(readOnly = true)
    public Flux<Post> getPostsInNewsFeedByUser(String userId) {
        return followRepository.findAllByFollowerId(userId)
            .flatMap(follow -> postRepository.findPublicPostsOfUserOrCompany(follow.getFollowedId()));
    }
    
    @Transactional
    public Mono<Post> createUserPostByUser(String userId, PostDTO postDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")))
            .flatMap(user -> {
                Post post = mapper.postDTOToPost(postDTO);
                post.setAuthor(user);
                post.setAuthorId(user.getId());
                post.setCreatedAt(Instant.now());
                post.setUpdatedAt(Instant.now());
                
                return postRepository.save(post);
            });
    }
    
    @Transactional
    public Mono<Post> updateUserPostByUser(String userId, Long postId, PostDTO postDTO) {
        return getPostOfUser(userId, postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound")))
            .flatMap(post -> postRepository.save(updatePost(post, postDTO)));
    }
    
    @Transactional
    public Mono<Void> deleteUserPostByUser(String userId, Long postId) {
        return getPostOfUser(userId, postId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound")))
            .flatMap(postRepository::delete);
    }
    
    @Transactional
    public Mono<Post> createCompanyPostByUser(String userId, Long companyId, PostDTO postDTO) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotofound")))
            .flatMap(user -> companyRepository.findByAdminAndId(user.getId(), companyId)
                .flatMap(company -> {
                    Post post = mapper.postDTOToPost(postDTO);
                    post.setCompany(company);
                    post.setCompanyId(company.getId());
                    post.setAuthor(user);
                    post.setAuthorId(user.getId());
                    post.setCreatedAt(Instant.now());
                    post.setUpdatedAt(Instant.now());
                    
                    return postRepository.save(post);
                })
            );
    }
    
    @Transactional
    public Mono<Post> updateCompanyPostByUser(String userId, Long companyId, Long postId, PostDTO postDTO) {
        return getPostOfCompanyByAdmin(userId, postId, companyId)
            .flatMap(post -> postRepository.save(updatePost(post, postDTO)));
    }
    
    private Post updatePost(Post post, PostDTO postDTO) {
        Post update = mapper.postDTOToPost(postDTO);
    
        if (update.getContent() != null) {
            post.setContent(update.getContent());
            post.setUpdatedAt(Instant.now());
        }
        if (update.getVisionable() != null) {
            post.setVisionable(update.getVisionable());
            post.setUpdatedAt(Instant.now());
        }
        
        return post;
    }
    
    @Transactional
    public Mono<Void> deleteCompanyPostByUser(String userId, Long companyId, Long postId) {
        return getPostOfCompanyByAdmin(userId, companyId, postId)
            .flatMap(postRepository::delete);
    }
    
    @Transactional
    public Mono<Attachment> uploadUserPostImage(String userId, Long postId, byte[] bytes) {
        if (bytes.length == 0) {
            return Mono.empty();
        }
        
        return getPostOfUser(userId, postId)
            .map(post -> {
                try {
                    return cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
                        "public_id", UPLOAD_FOLDER + "/attachments/posts/" + postId + "-" + userId + "/" + Instant.now().getEpochSecond(),
                        "overwrite", true,
                        "format", "png",
                        "resource_type", "auto",
                        "tags", List.of("user_post_attachment")
                    ));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            })
            .flatMap(response -> saveMediaAttachmentOfPost(postId, response));
    }
    
    @Transactional
    public Mono<Attachment> uploadCompanyPostImage(String userId, Long companyId, Long postId, byte[] bytes) {
        if (bytes.length == 0) {
            return Mono.empty();
        }
        
        return getPostOfCompanyByAdmin(userId, postId, companyId)
            .map(post -> {
                try {
                    return cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
                        "public_id", UPLOAD_FOLDER + "/attachments/posts/" + postId + "-" + companyId + "/" + Instant.now().getEpochSecond(),
                        "overwrite", true,
                        "format", "png",
                        "resource_type", "auto",
                        "tags", List.of("company_post_attachment")
                    ));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            })
            .flatMap(response -> saveMediaAttachmentOfPost(postId, response));
    }
    
    private Mono<Attachment> saveMediaAttachmentOfPost(Long postId, Map response) {
        String thumbUrl = response.get("secure_url").toString();
        Attachment attachment = Attachment.builder()
            .postId(postId)
            .createdAt(Instant.now())
            .thumbUrl(thumbUrl)
            .build();
    
        return attachmentRepository.save(attachment);
    }
}
