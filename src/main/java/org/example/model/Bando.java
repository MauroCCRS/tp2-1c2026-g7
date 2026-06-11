package org.example.model;

import java.util.List;

public interface Bando {
    List<Jugador> complices(Jugadores listaJugadores);
    boolean esMismoBando(Bando otro);
    boolean esMafia();
    boolean ganoSegun(Jugadores jugadores);
}