package com.virtuslab.internship.domain.basket;

import java.util.List;

import org.mapstruct.Mapper;

import com.virtuslab.internship.application.dto.BasketDTO;
import com.virtuslab.internship.domain.product.ProductMapper;


@Mapper(componentModel = "spring", uses = { ProductMapper.class })
public interface BasketMapper {

    Basket mapToEntity(BasketDTO basketDTO);
    List<Basket> mapToEntityList(List<BasketDTO> basketDTOS);

    BasketDTO mapToDto(Basket basket);
    List<BasketDTO> mapToDtoList(List<Basket> baskets);

}
