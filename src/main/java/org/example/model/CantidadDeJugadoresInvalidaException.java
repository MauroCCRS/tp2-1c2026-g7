package org.example.model;

public class CantidadDeJugadoresInvalidaException extends RuntimeException {
    public CantidadDeJugadoresInvalidaException(int cantidad) {
        super("Cantidad de jugadores no soportada: " + cantidad);
    }
}