package org.example.model;

public class BandoCiudadano implements Bando {
    @Override
    public boolean esMismoBando(Bando otro) {
        return otro.esMismoBandoQueCiudadano(this);
    }

    @Override
    public boolean esMismoBandoQueMafia(BandoMafia mafia) {
        return false;
    }

    @Override
    public boolean esMismoBandoQueCiudadano(BandoCiudadano ciudadano) {
        return true;
    }

    @Override
    public boolean ganoSegun(Jugadores jugadores) {
        int ciudadanosVivos = jugadores.cantidadVivosDelBando(this);
        return ciudadanosVivos == jugadores.cantidadDeVivos();
    }

    @Override
    public String nombre() {
        return "Ciudadanos";
    }
}