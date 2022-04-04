package com.virtuslab.internship.domain.exceptions;

public class ProductNotFoundException extends NullPointerException {

    public ProductNotFoundException() {
        super("Entity not found.");
    }

    public ProductNotFoundException(String s) {
        super(String.format("Entity %s not found", s));
    }

}
