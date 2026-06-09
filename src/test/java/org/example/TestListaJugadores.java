package org.example;

import org.example.model.BandoMafia;
import org.example.model.Jugador;
import org.example.model.ListaJugadores;
import org.example.model.Rol;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TestListaJugadores {

    @Test
    void alCrearListaDeJugadoresDeberiaExistir() {
        ListaJugadores listaJugadores = new ListaJugadores();

        assertNotNull(listaJugadores);
    }

    @Test
    void alAgregarUnJugadorALaListaDeberiaEstarEnLaLista() {
        ListaJugadores listaJugadores = new ListaJugadores();
        Jugador jugadorMock = mock(Jugador.class);

        listaJugadores.agregar(jugadorMock);

        assertTrue(listaJugadores.obtenerListaCompleta().contains(jugadorMock));
    }

    @Test
    void alEliminarUnJugadorDeLaListaDeberiaNoEstarEnLaLista() {
        ListaJugadores listaJugadores = new ListaJugadores();
        Jugador jugadorMock = mock(Jugador.class);
        listaJugadores.agregar(jugadorMock);
        listaJugadores.eliminar(jugadorMock);
        assertTrue(!listaJugadores.obtenerListaCompleta().contains(jugadorMock));
    }

    @Test
    void puedoObtenerLaListaDeMafiosos() {
        ListaJugadores listaJugadores = new ListaJugadores();
        BandoMafia bandoMock = mock(BandoMafia.class);

        Rol rolMafia = new Rol();
        Rol rolCiudadano = new Rol();

        rolMafia.ingresarBando(bandoMock);
        rolMafia.ingresarBando(bandoMock);

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();


        jugador1.cambiarRol(rolCiudadano);
        jugador2.cambiarRol(rolMafia);


        listaJugadores.agregar(jugador1);
        listaJugadores.agregar(jugador2);

        List<Jugador> mafiosos = listaJugadores.obtenerMafiosos();

        assertEquals(1, mafiosos.size());
        assertEquals(jugador2, mafiosos.get(0));
    }

}

