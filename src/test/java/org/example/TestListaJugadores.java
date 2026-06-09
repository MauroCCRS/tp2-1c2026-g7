package org.example;

import org.example.model.*;
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

        assertFalse(listaJugadores.obtenerListaCompleta().contains(jugadorMock));
    }

    @Test
    void puedoObtenerLaListaDeMafiosos() {
        ListaJugadores listaJugadores = new ListaJugadores();

        Jugador ciudadano = new Jugador();
        Jugador mafioso = new Jugador();

        ciudadano.cambiarRol(new Ciudadano());
        mafioso.cambiarRol(new Mafioso());

        listaJugadores.agregar(ciudadano);
        listaJugadores.agregar(mafioso);

        List<Jugador> mafiosos = listaJugadores.obtenerMafiosos();

        assertEquals(1, mafiosos.size());
        assertEquals(mafioso, mafiosos.get(0));
    }
}