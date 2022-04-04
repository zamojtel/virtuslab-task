package com.virtuslab.internship.application.controllers;

import static java.util.Objects.isNull;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.basket.BasketService;
import com.virtuslab.internship.domain.discount.DiscountsUtils;
import com.virtuslab.internship.domain.exceptions.EntityNotFoundException;
import com.virtuslab.internship.domain.exceptions.RequestNotSatisfiedException;
import com.virtuslab.internship.domain.receipt.Receipt;
import com.virtuslab.internship.domain.receipt.ReceiptGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
// web:
@CrossOrigin(allowedHeaders = { "*" }, origins = { "*" })
@RestController
@RequestMapping("/receipt")
public class ReceiptController {

    BasketService basketService;

    @GetMapping("/{basketId}")
    public ResponseEntity<Receipt> getReceiptByBasketId(@PathVariable String basketId) throws RequestNotSatisfiedException {
        if (isNull(basketId) || basketId.isBlank()) {
            throw new RequestNotSatisfiedException();
        }
        var uuid = UUID.fromString(basketId);
        var receiptGenerator = new ReceiptGenerator();
        Optional<Basket> basketOptional = basketService.findByUuid(uuid);
        if (basketOptional.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Receipt receipt = receiptGenerator.generate(basketOptional.get());
        return ResponseEntity.ok(DiscountsUtils.applyAllDiscounts(receipt));
    }
}
