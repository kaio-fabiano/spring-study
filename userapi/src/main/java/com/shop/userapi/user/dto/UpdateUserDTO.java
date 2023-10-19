package com.shop.userapi.user.dto;

import lombok.Builder;

@Builder
public record UpdateUserDTO(String name, String email, String password, String phone) {}
