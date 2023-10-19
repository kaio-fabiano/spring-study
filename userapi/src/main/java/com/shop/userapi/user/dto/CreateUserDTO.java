package com.shop.userapi.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.br.CPF;

@Builder
public record CreateUserDTO(
    @CPF @NotBlank String CPF,
    @NotBlank String name,
    @Email String email,
    @NotBlank String password,
    @Pattern(regexp = "^\\([1-9]{2}\\) [9]{0,1}[6-9]{1}[0-9]{3}\\-[0-9]{4}$") String phone) {}
