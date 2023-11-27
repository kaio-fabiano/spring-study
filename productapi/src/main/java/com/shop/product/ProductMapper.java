package com.shop.product;

import com.shop.product.dtos.CreateProductDTO;
import com.shop.product.dtos.ProductDTO;
import com.shop.product.dtos.UpdateProductDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "jakarta")
public interface ProductMapper {
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Product toProduct(CreateProductDTO createProductDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Product toProduct(UpdateProductDTO updateProductDTO);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  ProductDTO toProductDTO(Product product);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  Product mergeProduct(Product updateProduct, @MappingTarget Product product);
}
