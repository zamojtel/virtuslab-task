package com.virtuslab.internship.domain.exceptions;

public class RequestNotSatisfiedException extends Exception {

    public RequestNotSatisfiedException() {
        super("Body not specified.");
    }
}
