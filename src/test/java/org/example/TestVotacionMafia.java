package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("votacion")
@Tag("mafia")
class TestVotacionMafia {

    @Test
    void laMafiaPuedeElegirComoVictimaAUnCiudadanoVivo() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador mafioso = new Jugador("caro", new Mafioso());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());
        votacion.votar(mafioso, ciudadano);

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
    @Tag("padrino")
    void laMafiaNoPuedeElegirComoVictimaAUnPadrino() {
        Jugador padrino = new Jugador("Don", new Padrino());
        Jugador mafioso = new Jugador("Caro", new Mafioso());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(mafioso, padrino));
    }

    @Test
    void variosMafiososQueCoincidenEligenAEsaVictimaPorMayoria() {
        Jugador mafioso1 = new Jugador("M1", new Mafioso());
        Jugador mafioso2 = new Jugador("M2", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        votacion.votar(mafioso1, victima);
        votacion.votar(mafioso2, victima);

        assertEquals(Optional.of(victima), votacion.victimaElegida());
    }

    @Test
    void siLosMafiososNoCoincidenYNoHayPadrinoNoHayVictima() {
        Jugador mafioso1 = new Jugador("M1", new Mafioso());
        Jugador mafioso2 = new Jugador("M2", new Mafioso());
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador beto = new Jugador("Beto", new Ciudadano());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        votacion.votar(mafioso1, ana);
        votacion.votar(mafioso2, beto);

        assertTrue(votacion.victimaElegida().isEmpty());
    }

    @Test
    @Tag("padrino")
    void elVotoDelPadrinoTienePrioridadCuandoLaMafiaNoConsensua() {
        Jugador padrino = new Jugador("Don", new Padrino());
        Jugador mafioso = new Jugador("Caro", new Mafioso());
        Jugador victimaDelPadrino = new Jugador("Ana", new Ciudadano());
        Jugador victimaDelMafioso = new Jugador("Beto", new Ciudadano());
        VotacionMafia votacion = new VotacionMafia(new Mayoria());

        votacion.votar(mafioso, victimaDelMafioso);
        votacion.votar(padrino, victimaDelPadrino);

        assertEquals(Optional.of(victimaDelPadrino), votacion.victimaElegida());
    }
}