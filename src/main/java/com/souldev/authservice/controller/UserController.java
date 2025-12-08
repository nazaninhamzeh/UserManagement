package com.souldev.authservice.controller;


import com.souldev.authservice.dto.UserRequest;
import com.souldev.authservice.dto.UserResponse;
import com.souldev.authservice.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Validated // this is mandatory for validation processing on @RequestParam, for bodies @RestController does it by default
public class UserController {

    private final UserService userService;


    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody UserRequest request,
            UriComponentsBuilder uriComponentsBuilder) {

        UserResponse createdUser = userService.createUser(request);

        URI location = uriComponentsBuilder
                .path("/api/{id}")
                .buildAndExpand(createdUser.id())
                .toUri();

        return ResponseEntity.created(location).body(createdUser);
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getById(@PathVariable Long id) {
        UserResponse response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/by-username")
    public ResponseEntity<UserResponse> getByUsername(@RequestParam @NotBlank String username) {
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserRequest updateRequest) {

        UserResponse updated = userService.updateUser(id, updateRequest);
        return ResponseEntity.ok(updated);
    }

}
