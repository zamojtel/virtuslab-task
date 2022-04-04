package com.virtuslab.internship.receipt;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.basket.InMemoryBasketRepository;
import com.virtuslab.internship.domain.basket.impl.BasketServiceImpl;
import com.virtuslab.internship.domain.exceptions.ProductMalformedException;
import com.virtuslab.internship.domain.exceptions.ProductNotFoundException;
import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductDb;
import com.virtuslab.internship.domain.receipt.ReceiptGenerator;

class ReceiptGeneratorTest {

    @Test
    void shouldGenerateReceiptForGivenBasket() {
        // Given
        var productDb = new ProductDb();
        var cart = new Basket();
        var milk = productDb.getProduct("Milk").get();
        var bread = productDb.getProduct("Bread").get();
        var apple = productDb.getProduct("Apple").get();
        var expectedTotalPrice =
                milk.getPrice().multiply(BigDecimal.valueOf(2)).add(bread.getPrice()).add(apple.getPrice());

        cart.addProduct(milk);
        cart.addProduct(milk);
        cart.addProduct(bread);
        cart.addProduct(apple);

        var receiptGenerator = new ReceiptGenerator();

        // When
        var receipt = receiptGenerator.generate(cart);

        // Then
        assertNotNull(receipt);
        assertEquals(3, receipt.entries().size());
        assertEquals(expectedTotalPrice, receipt.totalPrice());
        assertEquals(0, receipt.discounts().size());
    }

    @Test
    void tryOfAddingTheArticleThatIsNotInProductDb() throws ProductMalformedException {
        // Given
        var productDb = new ProductDb();
        var inMemoryRepo= new InMemoryBasketRepository();
        var basketService= new BasketServiceImpl(inMemoryRepo,productDb);
        var water =new Product("Water",Product.Type.DAIRY, new BigDecimal("3.30"));
        var cart = basketService.createBasket();

        // When
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class,
                () -> basketService.addProductIntoBasketById(cart.getUuid(), water));

        // Then
        String actualMessage =exception.getMessage();
        assertTrue(actualMessage.contains("Entity Water not found"));
    }

    @Test
    void tryOfAddingTheArticleWithDifferentPriceThanInProductDb() throws ProductMalformedException {
        // Given
        var productDb = new ProductDb();
        var inMemoryRepo= new InMemoryBasketRepository();
        var basketService= new BasketServiceImpl(inMemoryRepo,productDb);
        var bread =new Product("Bread", Product.Type.GRAINS, new BigDecimal(6));
        var cart = basketService.createBasket();
        // When
        ProductMalformedException exception = assertThrows(ProductMalformedException.class,
                () -> basketService.addProductIntoBasketById(cart.getUuid(), bread));
        // Then
        String actualMessage =exception.getMessage();
        assertTrue(actualMessage.contains("Entity 'Bread' malformed"));
    }

}
