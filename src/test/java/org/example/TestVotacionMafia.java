package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestVotacionMafia {

    @Test
    void laMafiaPuedeElegirComoVictimaAUnCiudadanoVivo() {
        ListaJugadores jugadores = new ListaJugadores();
        Jugador ciudadano = new Jugador();
        ciudadano.cambiarRol(new Ciudadano());
        jugadores.agregar(ciudadano);

        VotacionMafia votacion = new VotacionMafia(jugadores);
        votacion.votar(ciudadano);

        assertEquals(ciudadano, votacion.victimaElegida());
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAOtroMafioso() {
        ListaJugadores jugadores = new ListaJugadores();
        Jugador mafioso = new Jugador();
        mafioso.cambiarRol(new Mafioso());
        jugadores.agregar(mafioso);

        VotacionMafia votacion = new VotacionMafia(jugadores);

        assertThrows(VictimaInvalidaException.class, () -> votacion.votar(mafioso));
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        ListaJugadores jugadores = new ListaJugadores();
        Jugador eliminado = new Jugador();
        eliminado.cambiarRol(new Ciudadano());
        eliminado.eliminar();
        jugadores.agregar(eliminado);

        VotacionMafia votacion = new VotacionMafia(jugadores);

        assertThrows(VictimaInvalidaException.class, () -> votacion.votar(eliminado));
    }
}