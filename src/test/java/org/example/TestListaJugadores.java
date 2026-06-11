package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestListaJugadores {

    @Test
    void alAgregarUnJugadorALaListaDeberiaEstarEnLaLista() {
        Jugadores listaJugadores = new Jugadores();
        Jugador jugador = new Jugador("Ana", new Ciudadano());

        listaJugadores.agregar(jugador);

        assertTrue(listaJugadores.obtenerListaCompleta().contains(jugador));
    }

    @Test
    void alEliminarUnJugadorDeLaListaDeberiaNoEstarEnLaLista() {
        Jugadores listaJugadores = new Jugadores();
        Jugador jugador = new Jugador("Ana", new Ciudadano());

        listaJugadores.agregar(jugador);
        listaJugadores.eliminar(jugador);

        assertFalse(listaJugadores.obtenerListaCompleta().contains(jugador));
    }

    @Test
    void puedoObtenerLaListaDeMafiosos() {
        Jugadores listaJugadores = new Jugadores();
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador mafioso = new Jugador("Beto", new Mafioso());

        listaJugadores.agregar(ciudadano);
        listaJugadores.agregar(mafioso);

        List<Jugador> mafiosos = listaJugadores.obtenerMafiosos();

        assertEquals(1, mafiosos.size());
        assertEquals(mafioso, mafiosos.get(0));
    }
}