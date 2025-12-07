package com.souldev.authservice.dto;


public record UserUpdateRequest(
        Long id,
        String username,
        String password,
        String firstName,
        String lastName
) {}
