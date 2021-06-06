package com.company.pm.interactionservice.domain.services.mapper;

import com.company.pm.common.enumeration.Visionable;
import com.company.pm.domain.interactionservice.Post;
import com.company.pm.interactionservice.domain.services.dto.PostDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostMapper {
    
    private final ModelMapper mapper;
    
    public Post postDTOToPost(PostDTO postDTO) {
        Post post = mapper.map(postDTO, Post.class);
        post.setVisionable(Visionable.valueOf(postDTO.getVisionable()));
        
        return post;
    }
}
