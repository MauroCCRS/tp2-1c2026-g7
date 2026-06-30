package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestVotacionMafia {

    @Test
    void laMafiaPuedeElegirComoVictimaAUnCiudadanoVivo() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador mafioso = new Jugador("caro", new Mafioso());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());
        votacion.votar(mafioso,ciudadano);

        assertEquals(Optional.of(ciudadano), votacion.victimaElegida());
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAOtroMafioso() {
        Jugador mafioso = new Jugador("Beto", new Mafioso());
        Jugador otroMafioso = new Jugador("Caro", new Mafioso());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(otroMafioso, mafioso));
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        Jugador eliminado = new Jugador("Caro", new Ciudadano());
        eliminado.eliminar();
        Jugador mafioso = new Jugador("Caro", new Mafioso());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(mafioso, eliminado));
    }
    @Test
    void laMafiaNoPuedeElegirComoVictimaAUnPadrino() {
        Jugador padrino = new Jugador("Don", new Padrino());
        Jugador mafioso = new Jugador("Caro", new Mafioso());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(mafioso, padrino));
    }
}