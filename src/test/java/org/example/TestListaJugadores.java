package org.example;

import org.example.model.Jugador;
import org.example.model.ListaJugadores;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

}

