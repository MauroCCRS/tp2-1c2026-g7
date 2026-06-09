package org.example.model;

public class VictimaInvalidaException extends RuntimeException {
    public VictimaInvalidaException(String mensaje) {
        super(mensaje);
    }
}