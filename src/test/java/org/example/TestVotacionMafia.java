package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestVotacionMafia {

    @Test
    void laMafiaPuedeElegirComoVictimaAUnCiudadanoVivo() {
        Jugadores jugadores = new Jugadores();
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        jugadores.agregar(ciudadano);

        VotacionMafia votacion = new VotacionMafia(jugadores);
        votacion.votar(ciudadano);

        assertEquals(ciudadano, votacion.victimaElegida());
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAOtroMafioso() {
        Jugadores jugadores = new Jugadores();
        Jugador mafioso = new Jugador("Beto", new Mafioso());
        jugadores.agregar(mafioso);

        VotacionMafia votacion = new VotacionMafia(jugadores);

        assertThrows(VictimaInvalidaException.class, () -> votacion.votar(mafioso));
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        Jugadores jugadores = new Jugadores();
        Jugador eliminado = new Jugador("Caro", new Ciudadano());
        eliminado.eliminar();
        jugadores.agregar(eliminado);

        VotacionMafia votacion = new VotacionMafia(jugadores);

        assertThrows(VictimaInvalidaException.class, () -> votacion.votar(eliminado));
    }
}