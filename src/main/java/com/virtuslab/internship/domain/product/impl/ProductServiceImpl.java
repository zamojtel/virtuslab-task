package com.virtuslab.internship.domain.product.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.virtuslab.internship.domain.product.Product;
import com.virtuslab.internship.domain.product.ProductDb;
import com.virtuslab.internship.domain.product.ProductService;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;


@Service
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    ProductDb productRepository;

    @Override
    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }

    @Override
    public Optional<Product> getProductByName(String name) {
        return productRepository.getProduct(name);
    }

    @Override
    public void deleteProductByName(String name) {
        productRepository.deleteProductByName(name);
    }

    @Override
    public Product addProduct(Product product) {
        return productRepository.addProduct(product);
    }
}
