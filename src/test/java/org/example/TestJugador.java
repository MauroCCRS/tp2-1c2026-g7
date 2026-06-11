package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJugador {

    @Test
    void unJugadorPuedeSaberSuBando() {
        Jugador jugador = new Jugador("Ana", new Mafioso());
        assertTrue(jugador.perteneceA(new BandoMafia()));
        assertFalse(jugador.perteneceA(new BandoCiudadano()));
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