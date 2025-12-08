package com.souldev.authservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
    @NotNull String username,
    @Min(8) String password,
    @NotNull String firstName,
    @NotNull String lastName
) {}
