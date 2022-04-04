package com.virtuslab.internship.domain.discount;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import com.virtuslab.internship.domain.receipt.Receipt;

public class DiscountsUtils {

    private static final List<? extends Discount> discounts =
            Stream.of(new FifteenPercentDiscount(), new TenPercentDiscount()).toList();

    private DiscountsUtils() {
    }

    public static Receipt applyAllDiscounts(Receipt receipt) {
        AtomicReference<Receipt> result = new AtomicReference<>(receipt);
        discounts.forEach(discount -> result.set(discount.apply(result.get())));
        return result.get();
    }

}
