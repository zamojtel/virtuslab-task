package com.virtuslab.internship.domain.exceptions;

import com.virtuslab.internship.domain.product.Product;


public class ProductMalformedException extends Exception {
    public ProductMalformedException() {
        super("Product malformed.");
    }

    public ProductMalformedException(Product product) {
        super(String.format("Entity '%s' malformed", product.getName()));
    }
}
