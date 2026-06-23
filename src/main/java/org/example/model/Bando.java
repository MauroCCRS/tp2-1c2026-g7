package org.example.model;

public interface Bando {
    boolean esMismoBando(Bando otro);
    boolean ganoSegun(Jugadores jugadores);

    boolean esMismoBandoQueMafia(BandoMafia mafia);
    boolean esMismoBandoQueCiudadano(BandoCiudadano ciudadano);

    String nombre();
}