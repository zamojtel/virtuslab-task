package com.virtuslab.internship.domain.basket.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.basket.BasketService;
import com.virtuslab.internship.domain.basket.InMemoryBasketRepository;
import com.virtuslab.internship.domain.exceptions.EntityNotFoundException;
import com.virtuslab.internship.domain.exceptions.ProductMalformedException;
import com.virtuslab.internship.domain.exceptions.ProductNotFoundException;
import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductDb;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BasketServiceImpl implements BasketService {

    InMemoryBasketRepository basketRepository;
    ProductDb productDb;

    @Override
    public Basket addProductIntoBasketById(UUID id, Product product) throws ProductMalformedException {
        Optional<Product> productOptional = productDb.getProduct(product.getName());
        productValidation(productOptional, product);
        Optional<Basket> basketOptional = basketRepository.findById(id);
        if (basketOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Basket basket = basketOptional.get();
        basket.addProduct(product);
        return basket;
    }

    @Override
    public Basket save(Basket basket) throws ProductMalformedException {
        checkThatAllProductsAreFromDB(basket);
        return basketRepository.save(basket);
    }

    @Override
    public Basket createBasket() throws ProductMalformedException {
        Basket basket = new Basket();
        save(basket);
        return basket;
    }

    @Override
    public List<Basket> saveAll(List<Basket> baskets) {
        List<Basket> savedBaskets = new ArrayList<>();
        basketRepository.saveAll(baskets).forEach(savedBaskets::add);
        return savedBaskets;
    }

    @Override
    public Optional<Basket> findByUuid(UUID uuid) {
        return basketRepository.findById(uuid);
    }

    @Override
    public List<Basket> findAll() {
        List<Basket> baskets = new ArrayList<>();
        basketRepository.findAll().forEach(baskets::add);
        return baskets;
    }

    @Override
    public List<Basket> findAllByIds(List<UUID> uuids) {
        List<Basket> baskets = new ArrayList<>();
        basketRepository.findAllById(uuids).forEach(baskets::add);
        return baskets;
    }

    @Override
    public long count() {
        return basketRepository.count();
    }

    @Override
    public void deleteById(UUID uuid) {
        basketRepository.deleteById(uuid);
    }

    @Override
    public void deleteByBasket(Basket basket) {
        basketRepository.delete(basket);
    }

    @Override
    public void deleteAllById(List<UUID> uuids) {
        basketRepository.deleteAllById(uuids);
    }

    @Override
    public void deleteAllByBaskets(List<Basket> baskets) {
        basketRepository.deleteAll(baskets);
    }

    @Override
    public void deleteAll() {
        basketRepository.deleteAll();
    }

    private void productValidation(Optional<Product> optionalProduct, Product product)
            throws ProductMalformedException {
        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(product.getName());
        } else {
            Product productFromDb = optionalProduct.get();
            if (!productFromDb.equals(product)) {
                throw new ProductMalformedException(product);
            }
        }
    }

    private void checkThatAllProductsAreFromDB(Basket basket) throws ProductMalformedException {
        for (Product product : basket.getProducts()) {
            Optional<Product> optionalProduct = productDb.getProduct(product.getName());
            if (optionalProduct.isEmpty()) {
                throw new ProductNotFoundException(product.getName());
            } else {
                Product productFromDb = optionalProduct.get();
                if (!productFromDb.equals(product)) {
                    throw new ProductMalformedException(product);
                }
            }
        }
    }
}
