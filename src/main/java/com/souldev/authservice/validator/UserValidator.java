package com.souldev.authservice.validator;

import com.souldev.authservice.dto.UserRequest;
import com.souldev.authservice.entity.User;
import com.souldev.authservice.exception.ConflictException;
import com.souldev.authservice.repository.UserRepository;
import com.souldev.authservice.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component  //if you don't declare class as component spring can not inject it in UserService class
public class UserValidator {

    private final UserRepository userRepository;

    public void validateCreateUser(UserRequest request) {
        if(userRepository.findByUsername(request.username()).isEmpty()) {
            throw new ConflictException(Messages.USER_ALREADY_EXISTS);
        }
    }

    public void validateUpdateUser(UserRequest request, User existingUser) {  //existingUser is got to avoid double database hit
        if (userRepository.existsByUsernameAndIdNot(request.username(), existingUser.getId())) {
            throw new ConflictException(Messages.USER_ALREADY_EXISTS);
        }
    }
}
