package org.example.model;

public class RolOculto extends Rol {

    @Override
    public Bando bando() {
        throw new UnsupportedOperationException("Un rol oculto no expone su bando");
    }

    @Override
    public boolean esVisible() {return false;}
}