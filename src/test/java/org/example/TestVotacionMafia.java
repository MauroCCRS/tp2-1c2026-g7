package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestVotacionMafia {

    @Test
    void laMafiaPuedeElegirComoVictimaAUnCiudadanoVivo() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        VotacionMafia votacion = new VotacionMafia();
        votacion.votar(ciudadano);

        assertEquals(ciudadano, votacion.victimaElegida());
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAOtroMafioso() {
        Jugador mafioso = new Jugador("Beto", new Mafioso());

        VotacionMafia votacion = new VotacionMafia();

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(mafioso));
    }

    @Test
    void laMafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        Jugador eliminado = new Jugador("Caro", new Ciudadano());
        eliminado.eliminar();

        VotacionMafia votacion = new VotacionMafia();

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(eliminado));
    }
    @Test
    void laMafiaNoPuedeElegirComoVictimaAUnPadrino() {
        Jugador padrino = new Jugador("Don", new Padrino());

        VotacionMafia votacion = new VotacionMafia();

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(padrino));
    }
}