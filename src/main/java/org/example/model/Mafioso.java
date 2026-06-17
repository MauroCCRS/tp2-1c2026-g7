package org.example.model;
public class Mafioso extends Rol {
    @Override
    public Bando bando() {
        return new BandoMafia();
    }
    
    @Override
    public String nombre() {
        return "Mafioso";
    }
}