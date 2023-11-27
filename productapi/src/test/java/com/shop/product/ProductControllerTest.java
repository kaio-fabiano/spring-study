package com.shop.product;

import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.product.dtos.CreateProductDTO;
import com.shop.product.dtos.UpdateProductDTO;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

@QuarkusTest
@Transactional
class ProductControllerTest {

  @Inject ProductController underTest;
  @InjectMock ProductRepository productRepository;

  public static String asJsonString(final Object obj) {
    try {
      final ObjectMapper mapper = new ObjectMapper();
      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static JsonNode fromJsonString(final String json) {
    try {
      return new ObjectMapper().readTree(json);
    } catch (Exception e) {
      throw new RuntimeException("Error parsing JSON response", e);
    }
  }

  @BeforeEach
  void setUp() {}

  /** Method under test: {@link ProductController#createProduct(CreateProductDTO, UriInfo)} */
  @Test
  void canCreateProduct() {
    // given
    CreateProductDTO dto =
        CreateProductDTO.builder()
            .name("Product 1")
            .description("Product 1 description")
            .price(10.0)
            .quantity(10)
            .userId(UUID.randomUUID())
            .build();

    // when
    Response response = this.underTest.createProduct(dto, Mockito.mock(UriInfo.class));

    // then
    assertNotNull(response);
    assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
  }

  /** Method under test: {@link ProductController#update(UpdateProductDTO, UUID)} */
  @Test
  void canUpdateProduct() {
    // given
    UpdateProductDTO updateProductDTO =
        UpdateProductDTO.builder()
            .name("Product 1 updated")
            .description("Product 1 description")
            .price(10.0)
            .quantity(10)
            .build();

    UUID uuid = UUID.randomUUID();

    Product product =
        Product.builder()
            .id(uuid)
            .quantity(5)
            .price(5.0)
            .description("Product 1 description")
            .name("Product 1")
            .userId(UUID.randomUUID())
            .build();

    given(this.productRepository.findByUUID(uuid)).willReturn(Optional.of(product));

    // when
    Response response = this.underTest.update(updateProductDTO, uuid);

    // then
    Assertions.assertNotNull(response);
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

    JsonNode jsonNode = fromJsonString(asJsonString(response.getEntity()));

    assertEquals(uuid.toString(), jsonNode.get("id").asText());
    assertEquals(updateProductDTO.name(), jsonNode.get("name").asText());
    assertEquals(updateProductDTO.description(), jsonNode.get("description").asText());
    assertEquals(updateProductDTO.price(), jsonNode.get("price").asDouble());
    assertEquals(updateProductDTO.quantity(), jsonNode.get("quantity").asInt());
  }

  /** Method under test: {@link ProductController#delete(UUID)} */
  @Test
  void canDeleteProduct() {
    // given
    final UUID uuid = UUID.randomUUID();

    given(this.productRepository.findByUUID(uuid))
        .willReturn(Optional.of(Product.builder().build()));
    // when
    Response response = this.underTest.delete(uuid);

    // then
    assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
  }

  /** Method under test: {@link ProductController#findById(UUID)} */
  @Test
  void canFindProductById() {
    // given
    final UUID uuid = UUID.randomUUID();

    given(this.productRepository.findByUUID(uuid))
        .willReturn(Optional.of(Product.builder().build()));
    // when
    Response response = this.underTest.findById(uuid);

    // then
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }
}
