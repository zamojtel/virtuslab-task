package com.virtuslab.internship.domain.receipt;

import com.virtuslab.internship.domain.product.Product;

import java.math.BigDecimal;

public record ReceiptEntry(
        Product product,
        int quantity,
        BigDecimal totalPrice) {

    public ReceiptEntry(Product product, int quantity) {
        this(product, quantity, product.getPrice().multiply(BigDecimal.valueOf(quantity)));
    }
}
