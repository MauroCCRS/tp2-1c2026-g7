package org.example.model;

public class Revelada implements EstadoCarta {
    @Override
    public String describir(Rol rol) {
        return rol.nombre();
    }
}