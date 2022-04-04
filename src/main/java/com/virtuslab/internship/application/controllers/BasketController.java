package com.virtuslab.internship.application.controllers;

import static java.util.Objects.isNull;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virtuslab.internship.application.dto.BasketDTO;
import com.virtuslab.internship.application.dto.ProductDTO;
import com.virtuslab.internship.domain.basket.Basket;
import com.virtuslab.internship.domain.basket.BasketMapper;
import com.virtuslab.internship.domain.basket.BasketService;
import com.virtuslab.internship.domain.exceptions.RequestNotSatisfiedException;
import com.virtuslab.internship.domain.exceptions.EntityNotFoundException;
import com.virtuslab.internship.domain.exceptions.ProductMalformedException;
import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductMapper;

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
@RequestMapping("/basket")
public class BasketController {

    public static final String NOT_SPECIFIED = "not specified";
    BasketService basketService;
    BasketMapper basketMapper;
    ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<Basket>> getAllBaskets() {

        return ResponseEntity.ok(basketService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BasketDTO> getBasket(@PathVariable String id) throws RequestNotSatisfiedException {
        if (isNull(id) || id.isBlank()) {
            throw new RequestNotSatisfiedException();
        }
        var uuid = UUID.fromString(id);
        BasketDTO basketDTO =
                basketMapper.mapToDto(basketService.findByUuid(uuid).orElseThrow(EntityNotFoundException::new));
        return ResponseEntity.ok(basketDTO);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDTO> addProductToBasket(@PathVariable String id, @RequestBody ProductDTO productDTO)
            throws RequestNotSatisfiedException, ProductMalformedException {
        if (isNull(id) || id.isBlank() || isNull(productDTO)) {
            throw new RequestNotSatisfiedException();
        }
        var uuid = UUID.fromString(id);
        Product product = productMapper.mapToEntity(productDTO);

        return ResponseEntity.ok(basketMapper.mapToDto(basketService.addProductIntoBasketById(uuid, product)));
    }

    @PostMapping("/new")
    public ResponseEntity<BasketDTO> addNewBasket() throws ProductMalformedException {
        return ResponseEntity.ok(basketMapper.mapToDto(basketService.createBasket()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasketDTO> addOrSaveBasket(@RequestBody BasketDTO basketDTO)
            throws RequestNotSatisfiedException, ProductMalformedException {

        if (isNull(basketDTO)) {
            throw new RequestNotSatisfiedException();
        }
        Basket basket = basketMapper.mapToEntity(basketDTO);
        return ResponseEntity.ok(basketMapper.mapToDto(basketService.save(basket)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBasket(@PathVariable String id) throws RequestNotSatisfiedException {
        if (isNull(id) || id.isBlank()) {
            throw new RequestNotSatisfiedException();
        }
        var uuid = UUID.fromString(id);
        basketService.deleteById(uuid);
        return ResponseEntity.noContent().build();
    }

}
