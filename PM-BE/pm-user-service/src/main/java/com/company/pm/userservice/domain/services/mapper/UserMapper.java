package com.company.pm.userservice.domain.services.mapper;

import com.company.pm.domain.userservice.Authority;
import com.company.pm.domain.userservice.User;
import com.company.pm.userservice.domain.services.dto.AdminUserDTO;
import com.company.pm.userservice.domain.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMapper {
    
    private final ModelMapper mapper;
    
    public List<UserDTO> usersToUserDtos(List<User> users) {
        return users.stream().map(user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }
    
    public UserDTO userToUserDto(User user) {
        return mapper.map(user, UserDTO.class);
    }
    
    public List<AdminUserDTO> usersToAdminUserDtos(List<User> users) {
        return users.stream().map(user -> mapper.map(user, AdminUserDTO.class)).collect(Collectors.toList());
    }
    
    public AdminUserDTO userToAdminUserDto(User user) {
        AdminUserDTO adminUserDto = mapper.map(user, AdminUserDTO.class);
        adminUserDto.setAuthorities(
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet())
        );
        
        return adminUserDto;
    }
    
    public List<User> adminUserDtosToUsers(List<AdminUserDTO> userDtos) {
        return userDtos.stream().map(userDto -> mapper.map(userDto, User.class)).collect(Collectors.toList());
    }
    
    public User adminUserDtoToUser(AdminUserDTO userDto) {
        if (userDto == null) {
            return null;
        } else {
            User user = mapper.map(userDto, User.class);
            user.setAuthorities(this.authoritiesFromStrings(userDto.getAuthorities()));
            
            return user;
        }
    }
    
    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();
        
        if (authoritiesAsString != null) {
            authorities =
                authoritiesAsString
                    .stream()
                    .map(string -> {
                        Authority auth = new Authority(string);
                        
                        return auth;
                    })
                    .collect(Collectors.toSet());
        }
        
        return authorities;
    }
}
