package com.virtuslab.internship.domain.basket;

import com.virtuslab.internship.domain.exceptions.ProductMalformedException;
import com.virtuslab.internship.domain.product.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface BasketService {

    Basket addProductIntoBasketById(UUID id, Product product) throws ProductMalformedException;

    Basket save(Basket basket) throws ProductMalformedException;

    Basket createBasket() throws ProductMalformedException;

    List<Basket> saveAll(List<Basket> baskets);

    Optional<Basket> findByUuid(UUID uuid);

    List<Basket> findAll();

    List<Basket> findAllByIds(List<UUID> uuids);

    long count();

    void deleteById(UUID uuid);

    void deleteByBasket(Basket basket);

    void deleteAllById(List<UUID> uuids);

    void deleteAllByBaskets(List<Basket> baskets);

    void deleteAll();


}
