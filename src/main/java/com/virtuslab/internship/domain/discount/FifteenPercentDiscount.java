package com.virtuslab.internship.domain.discount;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

import com.virtuslab.internship.domain.receipt.Receipt;


public class FifteenPercentDiscount implements Discount {


    public static final String NAME = "FifteenPercentDiscount";

    @Override
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.85));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        AtomicInteger counter = new AtomicInteger();
        receipt.entries().forEach(entry -> counter.addAndGet(entry.quantity()));
        return counter.get() >= 3;
    }
}
