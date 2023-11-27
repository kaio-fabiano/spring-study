package com.shop.product.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;

@Builder
public record CreateProductDTO(
    @NotEmpty String name,
    @NotEmpty String description,
    @NotNull Double price,
    @NotNull Integer quantity,
    @NotNull UUID userId) {}
