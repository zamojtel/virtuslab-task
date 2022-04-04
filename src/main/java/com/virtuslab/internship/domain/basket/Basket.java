package com.virtuslab.internship.domain.basket;

import com.virtuslab.internship.domain.product.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;


@Getter
public class Basket {
    @Setter
    private UUID uuid;
    private final List<Product> products = new CopyOnWriteArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }
    public void addProducts(List<Product> products) {
        this.products.addAll(products);
    }
}
