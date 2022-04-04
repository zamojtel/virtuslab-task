package com.virtuslab.internship.domain.product;

import com.virtuslab.internship.application.dto.ProductDTO;
import com.virtuslab.internship.domain.product.Product.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-04T15:42:38+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public Product mapToEntity(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        String name = null;
        Type type = null;
        BigDecimal price = null;

        name = productDTO.getName();
        type = productDTO.getType();
        price = productDTO.getPrice();

        Product product = new Product( name, type, price );

        return product;
    }

    @Override
    public List<Product> mapToEntityList(List<ProductDTO> productDTOList) {
        if ( productDTOList == null ) {
            return null;
        }

        List<Product> list = new ArrayList<Product>( productDTOList.size() );
        for ( ProductDTO productDTO : productDTOList ) {
            list.add( mapToEntity( productDTO ) );
        }

        return list;
    }

    @Override
    public ProductDTO mapToDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDTO productDTO = new ProductDTO();

        productDTO.setName( product.getName() );
        productDTO.setType( product.getType() );
        productDTO.setPrice( product.getPrice() );

        return productDTO;
    }

    @Override
    public List<ProductDTO> mapToDtoList(List<Product> productList) {
        if ( productList == null ) {
            return null;
        }

        List<ProductDTO> list = new ArrayList<ProductDTO>( productList.size() );
        for ( Product product : productList ) {
            list.add( mapToDto( product ) );
        }

        return list;
    }
}
