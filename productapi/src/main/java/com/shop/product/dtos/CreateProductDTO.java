package com.shop.product.dtos;

import io.smallrye.common.constraint.NotNull;

public record CreateProductDTO (@NotNull String name){}
