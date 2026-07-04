package org.example;

import org.example.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartidaCompletaTest {

    private void narrar(String mensaje) {
        Logger.log(mensaje);
    }

    private void banner(String titulo) {
        Logger.log("");
        Logger.log("################################################################");
        Logger.log("###  " + titulo);
        Logger.log("################################################################");
        Logger.log("");
    }

    @BeforeEach
    void habilitarLog() {
        Logger.setEnabled(true);
    }

    @Test
    void losCiudadanosGananLuegoDeUnaRondaCompletaCuandoVotanAlUnicoMafioso() {
        banner("PARTIDA COMPLETA: ganan los CIUDADANOS");

        Jugador mafioso = new Jugador("Vito", new Mafioso());
        Jugador detective = new Jugador("Dino", new Detective());
        Jugador medico = new Jugador("Mica", new Medico());
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador beto = new Jugador("Beto", new Ciudadano());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(detective);
        jugadores.agregar(medico);
        jugadores.agregar(ana);
        jugadores.agregar(beto);

        Partida partida = new Partidas().clasica(jugadores);

        narrar("Noche 1: Dino investiga a Vito, Mica protege a Ana, la Mafia ataca a Beto.");
        partida.elegirInvestigar(detective, mafioso);
        partida.elegirProteger(medico, ana);
        partida.registrarVotoMafia(mafioso, beto);
        partida.resolverFaseActual();
        narrar(partida.resumen());

        assertFalse(beto.estaVivo());
        assertTrue(ana.estaVivo());
        assertFalse(partida.terminada());

        narrar("Dia 1: los Ciudadanos votan a Vito.");
        partida.nominar(mafioso);
        partida.votar(detective, mafioso);
        partida.votar(medico, mafioso);
        partida.votar(ana, mafioso);
        partida.resolverFaseActual();
        narrar(partida.resumen());

        assertFalse(mafioso.estaVivo());
        assertTrue(partida.terminada());
        assertTrue(partida.resultado().get().esMismoBando(new BandoCiudadano()));
        assertTrue(partida.anuncio().isPresent());
        narrar(partida.anuncio().get());
    }

    @Test
    void laMafiaGanaTrasDosNochesCuandoQuedaIgualadaEnNumeroALosCiudadanos() {
        banner("PARTIDA COMPLETA: gana la MAFIA");

        Jugador mafioso = new Jugador("Vito", new Mafioso());
        Jugador medico = new Jugador("Mica", new Medico());
        Jugador beto = new Jugador("Beto", new Ciudadano());
        Jugador caro = new Jugador("Caro", new Ciudadano());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(medico);
        jugadores.agregar(beto);
        jugadores.agregar(caro);

        Partida partida = new Partidas().clasica(jugadores);

        narrar("Noche 1: Mica protege a Caro, la Mafia ataca a Beto.");
        partida.elegirProteger(medico, caro);
        partida.registrarVotoMafia(mafioso, beto);
        partida.resolverFaseActual();
        narrar(partida.resumen());

        assertFalse(beto.estaVivo());
        assertFalse(partida.terminada());

        narrar("Dia 1: empate entre Mica y Caro, no se elimina a nadie.");
        partida.nominar(medico);
        partida.nominar(caro);
        partida.votar(medico, caro);
        partida.votar(caro, medico);
        partida.resolverFaseActual();
        narrar(partida.resumen());

        assertTrue(medico.estaVivo());
        assertTrue(caro.estaVivo());
        assertFalse(partida.terminada());

        narrar("Noche 2: Mica se protege a si misma, la Mafia ataca a Caro.");
        partida.elegirProteger(medico, medico);
        partida.registrarVotoMafia(mafioso, caro);
        partida.resolverFaseActual();
        narrar(partida.resumen());

        assertFalse(caro.estaVivo());
        assertTrue(partida.terminada());
        assertTrue(partida.resultado().get().esMismoBando(new BandoMafia()));
        narrar(partida.anuncio().get());
    }
}
