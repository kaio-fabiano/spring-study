package com.shop.userapi.user.dtos;

import lombok.Builder;

@Builder
public record UpdateUserDTO(String name, String email, String password, String phone) {}
