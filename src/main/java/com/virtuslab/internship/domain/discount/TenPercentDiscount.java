package com.virtuslab.internship.domain.discount;

import com.virtuslab.internship.domain.receipt.Receipt;

import java.math.BigDecimal;

public class TenPercentDiscount implements Discount {

    public static final String NAME = "TenPercentDiscount";

    @Override
    public Receipt apply(Receipt receipt) {
        if (shouldApply(receipt)) {
            var totalPrice = receipt.totalPrice().multiply(BigDecimal.valueOf(0.9));
            var discounts = receipt.discounts();
            discounts.add(NAME);
            return new Receipt(receipt.entries(), discounts, totalPrice);
        }
        return receipt;
    }

    private boolean shouldApply(Receipt receipt) {
        return receipt.totalPrice().compareTo(BigDecimal.valueOf(50)) >= 0;
    }
}
