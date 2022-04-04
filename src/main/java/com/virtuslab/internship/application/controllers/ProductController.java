package com.virtuslab.internship.application.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.virtuslab.internship.application.dto.ProductDTO;
import com.virtuslab.internship.domain.exceptions.ProductNotFoundException;
import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductMapper;
import com.virtuslab.internship.domain.product.ProductService;

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
@RequestMapping("/product")
public class ProductController {

    ProductService productService;
    ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok(productMapper.mapToDtoList(productService.getAllProducts()));
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductDTO> getProductByName(@PathVariable String name) {
        Optional<Product> productOptional = productService.getProductByName(name);
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException(name);
        }
        return ResponseEntity.ok(productMapper.mapToDto(productOptional.get()));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDTO> putProduct(@RequestBody ProductDTO productDTO) {
        Product product = productMapper.mapToEntity(productDTO);
        return ResponseEntity.ok(productMapper.mapToDto(productService.addProduct(product)));
    }

}
