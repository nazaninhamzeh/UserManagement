package com.souldev.authservice.dto;

public record UserResponse(

        String username,
        String firstName,
        String lastName,
        String password
) {}
