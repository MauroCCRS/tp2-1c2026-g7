package org.example.model;
public class Sheriff extends Rol {


    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    @Override
    public String nombre() {
        return "Sheriff";
    }

    @Override
    public void revelarse() {

    }
}