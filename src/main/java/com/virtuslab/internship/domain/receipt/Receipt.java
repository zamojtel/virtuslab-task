package com.virtuslab.internship.domain.receipt;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public record Receipt(
        List<ReceiptEntry> entries,
        List<String> discounts,
        BigDecimal totalPrice) {

    public Receipt(List<ReceiptEntry> entries) {
        this(entries, new CopyOnWriteArrayList<>(),
                entries.stream()
                        .map(ReceiptEntry::totalPrice)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
    }
}
