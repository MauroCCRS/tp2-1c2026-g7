package org.example.model;

public class Padrino extends Mafioso {
    @Override
    public Bando resultadoAlSerInvestigado() {
        return new BandoCiudadano();
    }
}