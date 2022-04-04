package com.virtuslab.internship.domain.exceptions;


public class EntityNotFoundException extends NullPointerException {

    public EntityNotFoundException() {
        super("Entity not found.");
    }

    public EntityNotFoundException(String s) {
        super(String.format("Entity %s not found", s));
    }

}
