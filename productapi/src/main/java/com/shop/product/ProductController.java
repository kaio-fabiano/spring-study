package com.shop.product;

import com.shop.product.dtos.CreateProductDTO;
import com.shop.product.dtos.UpdateProductDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Path("/products")
@Produces("application/json")
@Consumes("application/json")
@ApplicationScoped
@Transactional
@AllArgsConstructor
public class ProductController {
  ProductService productService;
  ProductMapper productMapper;
  ProductRepository productRepository;

  @POST
  @Path("/create")
  public Response createProduct(@NotNull @Valid CreateProductDTO dto, @Context UriInfo uriInfo) {
    this.productService.create(this.productMapper.toProduct(dto));

    return Response.created(uriInfo.getBaseUri()).build();
  }

  @PUT
  @Path("/update/{id}")
  public Response update(@NotNull @Valid UpdateProductDTO dto, @PathParam("id") UUID id) {
    Product product = this.productService.update(this.productMapper.toProduct(dto), id);

    return Response.ok(this.productMapper.toProductDTO(product)).build();
  }

  @DELETE
  @Path("/delete")
  public Response delete(@Valid @NotNull UUID id) {
    this.productService.delete(id);

    return Response.noContent().build();
  }

  @GET
  @Path("/find/{id}")
  public Response findById(@PathParam("id") UUID id) {
    return Response.ok(this.productMapper.toProductDTO(this.productService.findById(id))).build();
  }
}
