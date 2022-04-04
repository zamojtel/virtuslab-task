package com.virtuslab.internship.domain.product;

import com.virtuslab.internship.application.dto.ProductDTO;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product mapToEntity(ProductDTO productDTO);
    List<Product> mapToEntityList(List<ProductDTO> productDTOList);

    ProductDTO mapToDto(Product product);
    List<ProductDTO> mapToDtoList(List<Product> productList);
}
