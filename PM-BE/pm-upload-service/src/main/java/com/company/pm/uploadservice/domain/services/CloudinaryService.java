package com.company.pm.uploadservice.domain.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.company.pm.common.web.errors.BadRequestAlertException;
import com.company.pm.companyservice.domain.repositories.CompanyRepository;
import com.company.pm.domain.userservice.User;
import com.company.pm.userservice.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    
    private static final String UPLOAD_FOLDER = "personal-management-collection";
    
    private final Cloudinary cloudinary;
    
    private final CompanyRepository companyRepository;
    
    private final UserRepository userRepository;
    
    @Transactional(readOnly = true)
    public Mono<Map> uploadBgImgOfUser(String userId, byte[] bytes) {
        return findUser(userId)
            .map(user -> {
                try {
                    return cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
                        "public_id", UPLOAD_FOLDER + "/" + userId + "/" + Instant.now().getEpochSecond(),
                        "overwrite", true,
                        "format", "png",
                        "resource_type", "image",
                        "tags", List.of("background")
                    ));
                } catch (IOException e) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                }
            });
    }
    
    @Transactional(readOnly = true)
    public Mono<Map> uploadLogoImgOfCompany(String userId, Long companyId, byte[] bytes) {
        return uploadCompanyImage(userId, companyId, bytes, List.of("logo"));
    }
    
    @Transactional(readOnly = true)
    public Mono<Map> uploadBgImgOfCompany(String userId, Long companyId, byte[] bytes) {
        return uploadCompanyImage(userId, companyId, bytes, List.of("background"));
    }
    
    private Mono<Map> uploadCompanyImage(String userId, Long companyId, byte[] bytes, List<String> tags) {
        return findUser(userId)
            .flatMap(user -> companyRepository.findByAdminAndId(userId, companyId)
                .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "company", "idnotfound")))
                .map(company -> {
                    try {
                        return cloudinary.uploader().upload(bytes, ObjectUtils.asMap(
                            "public_id", UPLOAD_FOLDER + "/company/" + companyId + "/" + Instant.now().getEpochSecond(),
                            "overwrite", true,
                            "format", "png",
                            "resource_type", "image",
                            "tags", tags
                        ));
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
                    }
                })
            );
    }
    
    private Mono<User> findUser(String userId) {
        return userRepository.findById(userId)
            .switchIfEmpty(Mono.error(new BadRequestAlertException("Entity not found", "user", "idnotfound")));
    }
}
