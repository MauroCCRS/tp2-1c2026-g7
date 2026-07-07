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

        Jugador mafiosoVotante = new Jugador("Mafioso", new Mafioso());
        jugadores.agregar(mafiosoVotante);
        Partida partida = new Partidas().clasica(jugadores);
        partida.registrarVotoMafia(mafiosoVotante, ana);
        partida.resolverFaseActual();

        assertFalse(ana.estaVivo());
    }

    @Test
    public void elMedicoAnulaLaEliminacionAlProtegerALaVictima() {
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador medico = new Jugador("Med", new Medico());
        Jugador caro = new Jugador("Caro", new Ciudadano());
        Jugadores jugadores = conMafiosoY(ana, medico, caro);

        Jugador mafiosoVotante = new Jugador("Mafioso", new Mafioso());
        jugadores.agregar(mafiosoVotante);

        Partida partida = new Partidas().clasica(jugadores);
        partida.registrarVotoMafia(mafiosoVotante, ana);
        partida.elegirProteger(medico, ana);
        partida.resolverFaseActual();

        assertTrue(ana.estaVivo());
    }

    @Test
    public void resolverDevuelveRegistroNocturnoCuandoAlguienEsEliminado() {
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(ana);
        jugadores.agregar(mafioso);

        FaseNocturna fase = new FaseNocturna(1, jugadores, new Mayoria());

        fase.registrar(new AccionVotoMafia(mafioso, ana));
        RegistroRonda registro = fase.resolver();

        assertFalse(ana.estaVivo());
    }

    @Test
    public void resolverDevuelveRegistroNocheTranquilaCuandoElMedicoProtege() {
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador medico = new Jugador("Med", new Medico());
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(ana);
        jugadores.agregar(medico);
        jugadores.agregar(mafioso);

        FaseNocturna fase = new FaseNocturna(1, jugadores, new Mayoria());

        fase.registrar(new AccionProteger(medico, ana));
        fase.registrar(new AccionVotoMafia(mafioso, ana));


        RegistroRonda registro = fase.resolver();

        assertTrue(ana.estaVivo());
    }
}