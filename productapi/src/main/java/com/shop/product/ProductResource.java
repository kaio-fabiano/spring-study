package com.shop.product;

import com.shop.product.dtos.CreateProductDTO;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

@Path("/products")
@Produces("application/json")
@Consumes("application/json")
public class ProductResource {
  @Inject ProductService productService;
  @Inject ProductMapper productMapper;

  @POST
  @Transactional
  @Path("/create")
  public Response createProduct(@NotNull @Valid CreateProductDTO dto, @Context UriInfo uriInfo) {
    Product persistedEntity = this.productService.create(this.productMapper.toProduct(dto));
    return Response.created(
            uriInfo.getAbsolutePathBuilder().path(persistedEntity.getId().toString()).build())
        .build();
  }

  public Product update(Product product) {
    return this.productService.update(product);
  }

  public void delete(Product product) {
    this.productService.delete(product);
  }
}
