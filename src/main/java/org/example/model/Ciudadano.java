package org.example.model;

public class Ciudadano extends Rol{
    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }
}
