package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFaseNocturna {

    private Jugadores conMafiosoY(Jugador... ciudadanos) {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(new Jugador("M1", new Mafioso()));
        for (Jugador c : ciudadanos) {
            jugadores.agregar(c);
        }
        return jugadores;
    }

    @Test
    public void laVictimaNoProtegidaMuereAlResolverLaFaseNocturna() {
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador beto = new Jugador("Beto", new Ciudadano());
        Jugador caro = new Jugador("Caro", new Ciudadano());
        Jugadores jugadores = conMafiosoY(ana, beto, caro);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(ana);
        partida.resolverFaseActual();

        assertFalse(ana.estaVivo());
    }

    @Test
    public void elMedicoAnulaLaEliminacionAlProtegerALaVictima() {
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador medico = new Jugador("Med", new Medico());
        Jugador caro = new Jugador("Caro", new Ciudadano());
        Jugadores jugadores = conMafiosoY(ana, medico, caro);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(ana);
        partida.elegirProteger(medico, ana);
        partida.resolverFaseActual();

        assertTrue(ana.estaVivo());
    }
}