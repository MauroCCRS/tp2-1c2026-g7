package org.example.model;

public class Mafioso extends Rol {
    @Override
    public Bando bando() {
        return new BandoMafia();
    }
}