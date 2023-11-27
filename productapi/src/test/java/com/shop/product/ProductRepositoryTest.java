package com.shop.product;

import static io.smallrye.common.constraint.Assert.assertFalse;
import static io.smallrye.common.constraint.Assert.assertTrue;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
@Transactional
class ProductRepositoryTest {

  @Inject ProductRepository underTest;

  private Product product;

  @BeforeEach
  public void setUp() {
    this.product =
        Product.builder()
            .name("Product 1")
            .description("Product 1 description")
            .price(10.0)
            .quantity(10)
            .userId(UUID.randomUUID())
            .build();
  }

  /** Method under test: {@link ProductRepository#findByUUID(UUID)} */
  @Test
  void itShouldCheckIfProductExistsByUUID() {
    // given
    this.underTest.persistAndFlush(product);

    // when
    boolean expected = this.underTest.findByUUID(product.getId()).isPresent();

    // then
    assertTrue(expected);
  }

  /** Method under test: {@link ProductRepository#findByUUID(UUID)} */
  @Test
  void itShouldCheckIfProductDoesNotExistByUUID() {
    // given
    this.underTest.persistAndFlush(product);

    // when
    UUID uuid = UUID.randomUUID();

    if (uuid.equals(product.getId())) {
      uuid = UUID.randomUUID();
    }

    boolean expected = this.underTest.findByUUID(uuid).isPresent();
    // then
    assertFalse(expected);
  }

  /** Method under test: {@link ProductRepository#findDuplicated(Product)} */
  @Test
  void itShouldCheckIfExistsADuplicatedProduct() {
    // given
    this.underTest.persistAndFlush(product);

    // when
    Optional<Product> expected = this.underTest.findDuplicated(product);

    // then
    assertTrue(expected.isPresent());
  }

  /** Method under test: {@link ProductRepository#findDuplicated(Product)} */
  @Test
  void itShouldCheckIfDoesNotExistsADuplicatedProduct() {
    // given
    this.underTest.persistAndFlush(product);

    // when
    Product product2 =
        Product.builder()
            .name("Product 2")
            .description("Product 2 description")
            .price(10.0)
            .quantity(10)
            .userId(UUID.randomUUID())
            .build();
    Optional<Product> expected = this.underTest.findDuplicated(product2);

    // then
    assertTrue(expected.isEmpty());
  }
}
