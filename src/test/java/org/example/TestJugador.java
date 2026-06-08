package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.example.model.Estado;
import org.example.model.Jugador;
import org.example.model.Rol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;


class TestJugador {
    @Test
    void alCrearJugadorDeberiaExistir() {
        Jugador jugador = new Jugador();
        assertNotNull(jugador);
    }

    @Test
    void alCrearUnJugadorSePuedeCambiarSuRol() {
        Jugador jugador = new Jugador();
        Rol rolMock = mock(Rol.class);
        jugador.cambiarRol(rolMock);

        assertEquals( rolMock, jugador.devolverRol());
    }
    @Test
    void alCrearUnJugadorSePuedeCambiarSuEstado() {
        Jugador jugador = new Jugador();
        Estado estadoMock = mock(Estado.class);
        jugador.cambiarEstado(estadoMock);
        assertEquals( estadoMock, jugador.devolverEstado());
    }


}


