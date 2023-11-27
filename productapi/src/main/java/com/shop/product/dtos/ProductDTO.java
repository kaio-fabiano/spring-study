package com.shop.product.dtos;

import java.util.UUID;

public record ProductDTO(
    String id,
    String name,
    String description,
    Double price,
    Integer quantity,
    String createdAt,
    String updatedAt,
    UUID userId) {}
