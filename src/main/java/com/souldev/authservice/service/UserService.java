package com.souldev.authservice.service;

import com.souldev.authservice.dto.UserCreateRequest;
import com.souldev.authservice.dto.UserResponse;
import com.souldev.authservice.dto.UserUpdateRequest;
import com.souldev.authservice.entity.User;
import com.souldev.authservice.exception.NotFoundException;
import com.souldev.authservice.mapper.UserMapper;
import com.souldev.authservice.repository.UserRepository;
import com.souldev.authservice.util.Messages;
import com.souldev.authservice.util.RepositoryUtils;
import com.souldev.authservice.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor //this makes constructor with all final fields - necessary for Spring DI
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidator userValidator;
    private final RepositoryUtils repositoryUtils;


    public UserResponse createUser(UserCreateRequest request) {
        userValidator.validateCreateUser(request);
        User entity = userMapper.toEntity(request);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        User saved = repositoryUtils.saveWithConflictCheck(userRepository, entity, Messages.USER_ALREADY_EXISTS);
        return userMapper.toDto(saved);
    }


    public UserResponse getUserByUsername(String username) {
        return userMapper.toDto(findByUsernameOrThrow(username));
    }


    public UserResponse getUserById(Long id) {
        return userMapper.toDto(findByIdOrThrow(id));
    }


    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserResponse updateUser(UserUpdateRequest request) {
        User existingUser = userRepository.findById(request.id())
                .orElseThrow(() -> new NotFoundException(Messages.USER_NOT_FOUND));
        userValidator.validateUpdateUser(request, existingUser);
        existingUser.setUsername(request.username());
        existingUser.setFirstName(request.firstName());
        existingUser.setLastName(request.lastName());
        existingUser.setPassword(passwordEncoder.encode(request.password()));
        User saved = userRepository.save(existingUser);
        return userMapper.toDto(saved);
    }

    //----private helper methods----

    private User findByUsernameOrThrow(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format(Messages.USER_NOT_FOUND_BY_USERNAME, username)));
    }

    private User findByIdOrThrow(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(Messages.USER_NOT_FOUND_BY_ID, id)));
    }

}
