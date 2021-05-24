package com.company.pm.socialservice.domain.services.mapper;

import com.company.pm.domain.socialservice.Follow;
import com.company.pm.socialservice.domain.services.dto.FollowDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FollowMapper {
    
    private final ModelMapper mapper;
    
    public Follow followDTOToFollow(FollowDTO followDTO) {
        return mapper.map(followDTO, Follow.class);
    }
}
