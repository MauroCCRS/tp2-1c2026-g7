package org.example.model;

public class BandoMafia implements Bando {
    @Override
    public boolean esMismoBando(Bando otro) {
        return otro.esMismoBandoQueMafia(this);
    }

    @Override
    public boolean esMismoBandoQueMafia(BandoMafia mafia) {
        return true;
    }

    @Override
    public boolean esMismoBandoQueCiudadano(BandoCiudadano ciudadano) {
        return false;
    }

    @Override
    public boolean ganoSegun(Jugadores jugadores) {
        int mafiososVivos = jugadores.cantidadVivosDelBando(this);
        int totalVivos = jugadores.cantidadDeVivos();
        return mafiososVivos >= totalVivos - mafiososVivos;
    }

    @Override
    public String nombre() {
        return "Mafia";
    }
}