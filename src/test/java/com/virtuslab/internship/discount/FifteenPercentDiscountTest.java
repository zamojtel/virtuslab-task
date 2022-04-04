package com.virtuslab.internship.discount;

import com.virtuslab.internship.domain.discount.FifteenPercentDiscount;
import com.virtuslab.internship.domain.discount.TenPercentDiscount;
import com.virtuslab.internship.domain.product.ProductDb;
import com.virtuslab.internship.domain.receipt.Receipt;
import com.virtuslab.internship.domain.receipt.ReceiptEntry;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FifteenPercentDiscountTest {

    @Test
    void shouldApply15PercentDiscountWith3ProductsOfSameCategory(){
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();

        var expectedTotalPrice = bread.getPrice().multiply(BigDecimal.valueOf(3)).multiply(BigDecimal.valueOf(0.85));
        var receiptAfterDiscount = discount.apply(receipt);

        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
    }

    @Test
    void shouldApplyBothDiscounts(){
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        var steak =productDb.getProduct("Steak").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 3));
        receiptEntries.add(new ReceiptEntry(steak,2));
        //when
        var receipt = new Receipt(receiptEntries);
        var discount15Percent = new FifteenPercentDiscount();
        var expectedTotalPrice = bread.getPrice().multiply(BigDecimal.valueOf(3))
                .add(steak.getPrice().multiply(BigDecimal.valueOf(2))).multiply(BigDecimal.valueOf(0.85)).multiply(BigDecimal.valueOf(0.9));
        var receiptAfterDiscountFirstDiscount = discount15Percent.apply(receipt);
        var discount10Percent=new TenPercentDiscount();
        var receiptAfterSecondDiscount = discount10Percent.apply(receiptAfterDiscountFirstDiscount);
        //then
        assertEquals(expectedTotalPrice, receiptAfterSecondDiscount.totalPrice());

    }
    @Test
    void shouldNotApply15PercentDiscountWithoutAtLeast3ProductsOfSameCategory() {
        // Given
        var productDb = new ProductDb();
        var bread = productDb.getProduct("Bread").get();
        List<ReceiptEntry> receiptEntries = new ArrayList<>();
        receiptEntries.add(new ReceiptEntry(bread, 2));

        var receipt = new Receipt(receiptEntries);
        var discount = new FifteenPercentDiscount();
        // When
        var receiptAfterDiscount = discount.apply(receipt);
        var expectedTotalPrice= bread.getPrice().multiply(BigDecimal.valueOf(2));
        // Then
        assertEquals(expectedTotalPrice, receiptAfterDiscount.totalPrice());
        assertEquals(0, receiptAfterDiscount.discounts().size());
    }
}
