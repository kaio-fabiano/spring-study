package com.shop.product;


import jakarta.inject.Inject;

public class ProductResource {
  @Inject ProductService productService;

  public Product create(Product product) {
    return this.productService.create(product);
  }

  public Product update(Product product) {
    return this.productService.update(product);
  }

  public void delete(Product product) {
    this.productService.delete(product);
  }



}
