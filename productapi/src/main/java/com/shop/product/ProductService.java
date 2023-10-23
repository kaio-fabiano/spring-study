package com.shop.product;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductService {
  @Inject
  ProductRepository productRepository;


  public Product create(Product product) {
    this.productRepository.persist(product);
    return product;
  }

  public Product update(Product product) {
    this.productRepository.persist(product);
    return product;
  }

  public void delete(Product product) {
    this.productRepository.delete(product);
  }

  public Product findById(Long id) {
    return productRepository.findById(id);
  }
}
