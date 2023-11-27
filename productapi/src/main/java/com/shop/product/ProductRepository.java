package com.shop.product;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

  public Optional<Product> findByUUID(UUID id) {
    return this.find("id", id).firstResultOptional();
  }

  public Optional<Product> findDuplicated(Product product) {
    return this.find(
            "name = :name and description = :description",
            Parameters.with("name", product.getName()).and("description", product.getDescription()))
        .firstResultOptional();
  }
}
