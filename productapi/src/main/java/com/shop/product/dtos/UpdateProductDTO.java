package com.shop.product.dtos;

import lombok.Builder;

@Builder
public record UpdateProductDTO(String name, String description, Double price, Integer quantity) {}
