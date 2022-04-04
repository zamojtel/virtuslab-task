package com.virtuslab.internship.domain.basket;

import com.virtuslab.internship.application.dto.BasketDTO;
import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductMapper;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-04-04T15:42:38+0200",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class BasketMapperImpl implements BasketMapper {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public Basket mapToEntity(BasketDTO basketDTO) {
        if ( basketDTO == null ) {
            return null;
        }

        Basket basket = new Basket();

        basket.setUuid( basketDTO.getUuid() );
        if ( basket.getProducts() != null ) {
            List<Product> list = productMapper.mapToEntityList( basketDTO.getProducts() );
            if ( list != null ) {
                basket.getProducts().addAll( list );
            }
        }

        return basket;
    }

    @Override
    public List<Basket> mapToEntityList(List<BasketDTO> basketDTOS) {
        if ( basketDTOS == null ) {
            return null;
        }

        List<Basket> list = new ArrayList<Basket>( basketDTOS.size() );
        for ( BasketDTO basketDTO : basketDTOS ) {
            list.add( mapToEntity( basketDTO ) );
        }

        return list;
    }

    @Override
    public BasketDTO mapToDto(Basket basket) {
        if ( basket == null ) {
            return null;
        }

        BasketDTO basketDTO = new BasketDTO();

        basketDTO.setUuid( basket.getUuid() );
        basketDTO.setProducts( productMapper.mapToDtoList( basket.getProducts() ) );

        return basketDTO;
    }

    @Override
    public List<BasketDTO> mapToDtoList(List<Basket> baskets) {
        if ( baskets == null ) {
            return null;
        }

        List<BasketDTO> list = new ArrayList<BasketDTO>( baskets.size() );
        for ( Basket basket : baskets ) {
            list.add( mapToDto( basket ) );
        }

        return list;
    }
}
