package org.example.model;

public class ProteccionInvalidaException extends RuntimeException {
    public ProteccionInvalidaException(String message) {
        super(message);
    }
}
