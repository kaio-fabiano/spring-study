package com.shop.product;

import com.shop.product.dtos.CreateProductDTO;
import com.shop.product.dtos.ProductDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ProductMapper {
  Product toProduct(CreateProductDTO createProductDTO);

  ProductDTO toProductDTO(Product product);
}
