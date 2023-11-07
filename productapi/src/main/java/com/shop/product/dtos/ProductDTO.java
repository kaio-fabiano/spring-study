package com.shop.product.dtos;

public record ProductDTO(
    String id,
    String name,
    String description,
    Double price,
    Integer quantity,
    String createdAt,
    String updatedAt) {}
