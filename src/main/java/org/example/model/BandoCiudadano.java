package org.example.model;

import java.util.List;

public class BandoCiudadano implements Bando {
    @Override
    public List<Jugador> complices(ListaJugadores listaJugadores) {
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
}