package com.souldev.authservice.mapper;

import com.souldev.authservice.dto.UserCreateRequest;
import com.souldev.authservice.dto.UserResponse;
import com.souldev.authservice.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserCreateRequest dto) {   //remember that it makes new entity so should not be used for update
        User user = new User();
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setUsername(dto.username());
        user.setPassword(dto.password());
        return user;
    }

    public UserResponse toDto(User user) {
        return new UserResponse(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword());
    }

}
