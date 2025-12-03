package com.souldev.authservice.service;

import com.souldev.authservice.entity.User;
import com.souldev.authservice.exception.ConflictException;
import com.souldev.authservice.exception.NotFoundException;
import com.souldev.authservice.repository.UserRepository;
import com.souldev.authservice.util.Messages;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(String.format(Messages.USER_NOT_FOUND_BY_USERNAME, username)));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(Messages.USER_NOT_FOUND_BY_ID, id)));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        try {
            return userRepository.save(user);
        } catch (DataIntegrityViolationException ex){
            throw new ConflictException(Messages.USER_ALREADY_EXISTS);
        }
    }

}
