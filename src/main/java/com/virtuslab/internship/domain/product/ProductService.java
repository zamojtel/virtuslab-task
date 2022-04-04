package com.virtuslab.internship.domain.product;

import java.util.List;
import java.util.Optional;


public interface ProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductByName(String name);
    void deleteProductByName(String name);
    Product addProduct(Product product);
}
