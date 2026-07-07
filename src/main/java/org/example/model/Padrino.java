package org.example.model;
public class Padrino extends Mafioso {
    @Override
    public Bando resultadoAlSerInvestigado() {
        return new BandoCiudadano();
    }
    @Override
    public String nombre() {
        return "Padrino";
    }
    @Override
    public String descripcionAlSerInvestigado() {
        return "Ciudadano";
    }
    @Override
    public  VotoMafia crearVotoMafia(Jugador objetivo) {
        return new VotoPadrino(objetivo);
    }
}
