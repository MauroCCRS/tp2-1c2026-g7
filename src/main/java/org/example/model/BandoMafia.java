package org.example.model;

import java.util.List;

public class BandoMafia implements Bando {
    @Override
    public List<Jugador> complices(Jugadores listaJugadores) {
        return listaJugadores.delBando(this);
    }

    @Override
    public boolean esMismoBando(Bando otro) {
        return otro.esMafia();
    }

    @Override
    public boolean esMafia() {
        return true;
    }

    @Override
    public boolean ganoSegun(Jugadores jugadores) {
        int mafiososVivos = jugadores.vivosDelBando(this).size();
        int totalVivos = jugadores.obtenerVivos().size();
        return mafiososVivos >= totalVivos - mafiososVivos;
    }
}