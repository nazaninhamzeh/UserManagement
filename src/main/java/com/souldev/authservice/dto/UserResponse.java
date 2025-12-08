package com.souldev.authservice.dto;

public record UserResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String password
) {}
