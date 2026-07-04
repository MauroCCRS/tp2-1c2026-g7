package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAnuncio {

    @Test
    public void partidaEnCursoNoTieneAnuncio() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(new Jugador("M1", new Mafioso()));
        jugadores.agregar(new Jugador("Ana", new Ciudadano()));
        jugadores.agregar(new Jugador("Beto", new Ciudadano()));
        jugadores.agregar(new Jugador("Caro", new Ciudadano()));

        Partida partida = new Partidas().clasica(jugadores);

        assertTrue(partida.anuncio().isEmpty());
    }

    @Test
    public void alGanarLaMafiaElAnuncioLaNombra() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(ana);

        Partida partida = new Partidas().clasica(jugadores);
        partida.registrarVotoMafia(mafioso, ana);
        partida.resolverFaseActual();

        assertTrue(partida.anuncio().isPresent());
        assertTrue(partida.anuncio().get().contains("Mafia"));
    }
}