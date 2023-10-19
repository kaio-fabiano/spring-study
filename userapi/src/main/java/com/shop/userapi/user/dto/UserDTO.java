package com.shop.userapi.user.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserDTO(
    UUID id,
    String CPF,
    String name,
    String email,
    String password,
    String phone,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {}
