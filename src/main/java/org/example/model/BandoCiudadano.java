package org.example.model;

import java.util.List;

public class BandoCiudadano implements Bando {
    @Override
    public List<Jugador> complices(Jugadores listaJugadores) {
        return listaJugadores.delBando(this);
    }

    @Override
    public boolean esMismoBando(Bando otro) {
        return !otro.esMafia();
    }

    @Override
    public boolean esMafia() {
        return false;
    }

    @Override
    public boolean ganoSegun(Jugadores jugadores) {
        int ciudadanosVivos = jugadores.vivosDelBando(this).size();
        return ciudadanosVivos == jugadores.obtenerVivos().size();
    }
}