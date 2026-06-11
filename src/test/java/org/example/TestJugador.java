package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJugador {

    @Test
    void unJugadorPuedeSaberSuBando() {
        Jugador jugador = new Jugador("Ana", new Mafioso());
        assertTrue(jugador.bando() instanceof BandoMafia);
    }

    @Test
    void unJugadorMafiosoConoceATodosLosMafiosos() {
        Jugadores listaJugadores = new Jugadores();
        Jugador mafioso1 = new Jugador("M1", new Mafioso());
        Jugador mafioso2 = new Jugador("M2", new Mafioso());
        Jugador ciudadano = new Jugador("C1", new Ciudadano());
        listaJugadores.agregar(ciudadano);
        listaJugadores.agregar(mafioso2);
        listaJugadores.agregar(mafioso1);

        int cantidadDeComplices = mafioso1.bando().complices(listaJugadores).size();

        assertEquals(2, cantidadDeComplices);
    }

    @Test
    void unJugadorRecienCreadoEstaVivo() {
        Jugador jugador = new Jugador("Ana", new Ciudadano());
        assertTrue(jugador.estaVivo());
    }

    @Test
    void unJugadorEliminadoDejaDeEstarVivo() {
        Jugador jugador = new Jugador("Ana", new Ciudadano());
        jugador.eliminar();
        assertFalse(jugador.estaVivo());
    }
}