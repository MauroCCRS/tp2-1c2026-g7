package org.example.model;

public class Detective extends Rol{
    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }
}
