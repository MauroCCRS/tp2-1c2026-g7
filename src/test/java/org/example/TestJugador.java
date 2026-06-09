package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


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
        assertEquals( estadoMock, jugador.devolverEstado());
    }
    @Test
    void unJugadorPuedePedirSuPropioRol() {
        Jugador jugador = new Jugador();
        Rol rolMock = mock(Rol.class);
        jugador.cambiarRol(rolMock);

        assertEquals( rolMock, jugador.devolverRol(jugador));
    }

    @Test
    void unJugadorNoPuedePedirElRolDeOtroJugador() {
        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        Rol rolMock = mock(Rol.class);

        jugador1.cambiarRol(rolMock);

        assertEquals( null, jugador2.devolverRol(jugador1));
    }

    @Test
    void unJugadorPuedeSaberSuBando() {
        Jugador jugador = new Jugador();
        Rol rol = new Rol();
        Bando bandoMock = mock(Bando.class);

        rol.ingresarBando(bandoMock);
        jugador.cambiarRol(rol);

        Bando bandoDevuelto = jugador.devolverRol(jugador).devolverBando();
        assertEquals( bandoMock, bandoDevuelto);
    }


    @Test
    void unJugadorMafiosoConoceATodosLosMafiosos() {
        ListaJugadores listaJugadores = new ListaJugadores();

        Rol rolCiudadano = new Rol();
        Rol rolMafioso = new Rol();
        BandoMafia bandoMafioso = new BandoMafia();
        rolMafioso.ingresarBando(bandoMafioso);

        Jugador jugadorMafioso1 = new Jugador();
        Jugador jugadorMafioso2 = new Jugador();
        Jugador jugadorCiudadano = new Jugador();

        jugadorCiudadano.cambiarRol(rolCiudadano);
        jugadorMafioso1.cambiarRol(rolMafioso);
        jugadorMafioso2.cambiarRol(rolMafioso);


        listaJugadores.agregar(jugadorCiudadano);
        listaJugadores.agregar(jugadorMafioso2);
        listaJugadores.agregar(jugadorMafioso1);


        int cantidadDeComplices = 2;
        int cantidadDeComplicesDevueltos = jugadorMafioso1.devolverRol(jugadorMafioso1).
                devolverBando().complices(listaJugadores).size();


        assertEquals(cantidadDeComplices, cantidadDeComplicesDevueltos);
    }
}


