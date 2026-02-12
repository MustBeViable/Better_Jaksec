package com.api.login.mapper;

import com.api.login.User;
import com.api.login.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDto dto){
        return null;
    }
    public UserDto toDto(User user){
        return new UserDto(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole());
    }

}
