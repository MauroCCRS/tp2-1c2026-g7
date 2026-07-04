package org.example.model;

public class Mafioso extends Rol implements VotanteMafia {
    @Override
    public Bando bando() {
        return new BandoMafia();
    }
    
    @Override
    public String nombre() {
        return "Mafioso";
    }

    @Override
    public  VotoMafia crearVotoMafia(Jugador objetivo) {
        return new VotoNormal(objetivo);
    }

    @Override
    public void ejecutar(AccionDeRol accion) {
        accion.ejecutarPara(this);
    }
}
