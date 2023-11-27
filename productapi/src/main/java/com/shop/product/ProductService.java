package com.shop.product;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.UUID;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ProductService {
  public static final String PRODUCT_NOT_FOUND_MESSAGE = "O produto não existe!";
  ProductRepository productRepository;

  ProductMapper productMapper;

  public Product create(Product product) {
    if (this.productRepository.findDuplicated(product).isPresent()) {
      throw new RuntimeException("O produto já existe!");
    }

    this.productRepository.persist(product);
    return product;
  }

  public Product update(Product product, UUID id) {
    Product persistedProduct =
        this.productRepository
            .findByUUID(id)
            .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND_MESSAGE));

    this.productMapper.mergeProduct(product, persistedProduct);

    this.productRepository.persist(persistedProduct);
    return persistedProduct;
  }

  public void delete(UUID id) {
    Product product =
        this.productRepository
            .findByUUID(id)
            .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND_MESSAGE));

    this.productRepository.delete(product);
  }

  public Product findById(UUID id) {
    return productRepository
        .findByUUID(id)
        .orElseThrow(() -> new RuntimeException(PRODUCT_NOT_FOUND_MESSAGE));
  }
}
