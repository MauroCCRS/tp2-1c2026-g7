package org.example.model;

public class Oculta implements EstadoCarta {
    @Override
    public String describir(Rol rol) {
        return "Carta oculta";
    }
}