package com.shop.product.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateProductDTO(
    @NotEmpty String name,
    @NotEmpty String description,
    @NotNull Double price,
    @NotNull Integer quantity) {}
