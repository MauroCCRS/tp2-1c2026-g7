package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TestJugador {

    @Test
    void alCrearJugadorDeberiaExistir() {
        Jugador jugador = new Jugador();
        assertNotNull(jugador);
    }

    @Test
    void alCrearUnJugadorSePuedeCambiarSuEstado() {
        Jugador jugador = new Jugador();
        Estado estadoMock = mock(Estado.class);
        jugador.cambiarEstado(estadoMock);
        assertEquals(estadoMock, jugador.devolverEstado());
    }

    @Test
    void unJugadorPuedeSaberSuBando() {
        Jugador jugador = new Jugador();
        jugador.cambiarRol(new Mafioso());

        Bando bando = jugador.devolverRol(jugador).bando();

        assertTrue(bando instanceof BandoMafia);
    }

    @Test
    void unJugadorMafiosoConoceATodosLosMafiosos() {
        ListaJugadores listaJugadores = new ListaJugadores();

        Jugador mafioso1 = new Jugador();
        Jugador mafioso2 = new Jugador();
        Jugador ciudadano = new Jugador();

        mafioso1.cambiarRol(new Mafioso());
        mafioso2.cambiarRol(new Mafioso());
        ciudadano.cambiarRol(new Ciudadano());

        listaJugadores.agregar(ciudadano);
        listaJugadores.agregar(mafioso2);
        listaJugadores.agregar(mafioso1);

        int cantidadDeComplices =
                mafioso1.devolverRol(mafioso1).bando().complices(listaJugadores).size();

        assertEquals(2, cantidadDeComplices);
    }

    @Test
    void unJugadorRecienCreadoEstaVivo() {
        Jugador jugador = new Jugador();
        assertTrue(jugador.devolverEstado().estaVivo());
    }

    @Test
    void unJugadorEliminadoDejaDeEstarVivo() {
        Jugador jugador = new Jugador();
        jugador.eliminar();
        assertFalse(jugador.devolverEstado().estaVivo());
    }
}