package org.example.model;

import java.util.List;

public class BandoCiudadano implements Bando {
    @Override
    public List<Jugador> complices(ListaJugadores listaJugadores){
        return listaJugadores.obtenerCiudadanos();
    }


}
