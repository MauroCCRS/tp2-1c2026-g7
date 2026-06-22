package org.example.model;
public class Sheriff extends Rol {
    private boolean revelado = false;


    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    @Override
    public String nombre() {
        return "Sheriff";
    }

    @Override
    public void revelarseComoSheriff() {
        revelarse();
    }


    public void revelarse() {
        this.revelado = true;
    }

    public boolean estaRevelado() {
        return revelado;
    }
}