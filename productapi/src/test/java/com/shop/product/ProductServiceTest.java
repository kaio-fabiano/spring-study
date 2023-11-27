package com.shop.product;

import static com.shop.product.ProductService.PRODUCT_NOT_FOUND_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@QuarkusTest
@Transactional
class ProductServiceTest {
  @Inject ProductService underTest;
  private Product product;
  @InjectMock private ProductRepository productRepository;

  private static Product buildProduct() {
    return Product.builder()
        .id(UUID.randomUUID())
        .name("Product 1")
        .description("Product 1 description")
        .price(10.0)
        .quantity(10)
        .userId(UUID.randomUUID())
        .build();
  }

  @BeforeEach
  void setUp() {}

  /** Method under test: {@link ProductService#create(Product)} */
  @Test
  void canCreateProduct() {
    // given
    this.product = buildProduct();

    // when
    this.underTest.create(product);

    // then
    ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

    verify(this.productRepository).persist(productArgumentCaptor.capture());

    Product capturedProduct = productArgumentCaptor.getValue();

    assertEquals(capturedProduct, product);
  }

  /** Method under test: {@link ProductService#create(Product)} */
  @Test
  void willThrowWhenProductAlreadyExists() {
    // given
    this.product = buildProduct();

    given(this.productRepository.findDuplicated(product)).willReturn(Optional.of(product));

    // when
    // then
    assertThrows(RuntimeException.class, () -> this.underTest.create(product));

    verify(this.productRepository, never()).persist(product);
  }

  /** Method under test: {@link ProductService#update(Product, UUID)} */
  @Test
  void canUpdate() {
    // given
    this.product = buildProduct();

    given(this.productRepository.findByUUID(product.getId())).willReturn(Optional.of(product));

    Product updateProduct = buildProduct();
    updateProduct.setName("Product 2");
    updateProduct.setDescription("Product 2 description");

    // when
    this.underTest.update(updateProduct, product.getId());

    // then
    ArgumentCaptor<Product> productArgumentCaptor = ArgumentCaptor.forClass(Product.class);

    verify(this.productRepository).persist(productArgumentCaptor.capture());

    Product capturedProduct = productArgumentCaptor.getValue();

    assertEquals(capturedProduct, product);
    assertEquals(capturedProduct.getName(), updateProduct.getName());
    assertEquals(capturedProduct.getDescription(), updateProduct.getDescription());
  }

  /** Method under test: {@link ProductService#update(Product, UUID)} */
  @Test
  void willThrowWhenUpdateNonExistingProduct() {
    // given
    this.product = buildProduct();

    given(this.productRepository.findByUUID(UUID.randomUUID())).willReturn(Optional.empty());

    // when
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> this.underTest.update(this.product, this.product.getId()));

    // then
    assertEquals(PRODUCT_NOT_FOUND_MESSAGE, exception.getMessage());
    verify(this.productRepository).findByUUID(this.product.getId());
    verify(this.productRepository, never()).persist((Product) any());
  }

  /** Method under test: {@link ProductService#delete(UUID)} */
  @Test
  void canDelete() {
    // given
    UUID id = UUID.randomUUID();
    this.product = buildProduct();

    given(this.productRepository.findByUUID(id)).willReturn(Optional.of(product));

    // when
    this.underTest.delete(id);

    // then
    verify(this.productRepository).delete(product);
  }

  /** Method under test: {@link ProductService#delete(UUID)} */
  @Test
  void willThrowWhenProductNotFoundInDelete() {
    // given
    UUID id = UUID.randomUUID();
    given(this.productRepository.findByUUID(id)).willReturn(Optional.empty());

    // when
    // then
    assertThrows(
        RuntimeException.class, () -> this.underTest.delete(id), PRODUCT_NOT_FOUND_MESSAGE);

    verify(this.productRepository, never()).deleteById(any());
  }

  /** Method under test: {@link ProductService#findById(UUID)} */
  @Test
  void canFindByIdExistingProduct() {
    // given
    this.product = buildProduct();

    given(this.productRepository.findByUUID(this.product.getId()))
        .willReturn(Optional.of(this.product));

    // when
    Product result = this.underTest.findById(this.product.getId());

    // then
    assertEquals(product, result);
  }

  /** Method under test: {@link ProductService#findById(UUID)} */
  @Test
  void willThrowWhenFindByIdNonExistingProduct() {
    // given
    UUID id = UUID.randomUUID();

    given(this.productRepository.findByUUID(id)).willReturn(Optional.empty());

    // when
    // then
    assertThrows(
        RuntimeException.class, () -> this.underTest.findById(id), PRODUCT_NOT_FOUND_MESSAGE);
  }
}
