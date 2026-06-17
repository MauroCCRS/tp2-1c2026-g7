package org.example.model;

public class ProtegidoInvalidoException extends RuntimeException{
    public ProtegidoInvalidoException(String mensage){
        super(mensage);
    }
}
