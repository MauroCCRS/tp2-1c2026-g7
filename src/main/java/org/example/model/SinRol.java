package org.example.model;

public class SinRol extends Rol {

    @Override
    public Bando bando() {
        throw new UnsupportedOperationException("Un jugador sin rol asignado no tiene bando");
    }

    @Override
    public boolean esRolAsignado() {return false;}
}