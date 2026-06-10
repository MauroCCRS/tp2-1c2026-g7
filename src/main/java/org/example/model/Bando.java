package org.example.model;

import java.util.List;

public interface Bando {
    List<Jugador> complices(ListaJugadores listaJugadores);
    boolean esMismoBando(Bando otro);
    boolean esMafia();
}