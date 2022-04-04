package com.virtuslab.internship.domain.receipt;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.virtuslab.internship.domain.basket.Basket;


public class ReceiptGenerator {

    public Receipt generate(Basket basket) {
        List<ReceiptEntry> receiptEntries = new CopyOnWriteArrayList<>();
        basket.getProducts()
                .forEach(product -> receiptEntries.add(new ReceiptEntry(product,
                        (int) basket.getProducts()
                                .stream()
                                .filter(productFromBasket -> productFromBasket.getName().equals(product.getName()))
                                .count())));

        return new Receipt(receiptEntries.stream().distinct().toList());
    }
}
