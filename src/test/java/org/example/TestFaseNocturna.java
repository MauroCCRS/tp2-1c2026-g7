package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFaseNocturna {

    @Test
    public void laVictimaSinProteccionEsEliminadaYQuedaRegistrada() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();

        assertFalse(victima.estaVivo());
        assertTrue(partida.resumen().contains("Ana"));
    }

    @Test
    public void siLaMafiaAtacaYElMedicoProtegeAOtroJugadorLaVictimaMuere() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Jugador otroCiudadano = new Jugador("Beto", new Ciudadano());
        Jugador medico = new Jugador("Medico", new Medico());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);
        jugadores.agregar(otroCiudadano);
        jugadores.agregar(medico);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victima);
        partida.elegirProteger(medico, otroCiudadano);
        partida.resolverFaseActual();

        assertFalse(victima.estaVivo());
        assertTrue(otroCiudadano.estaVivo());
        assertTrue(medico.estaVivo());
        assertTrue(partida.resumen().contains("Ana"));
    }


    @Test
    public void siElMedicoProtegeALaVictimaNadieMuere() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Jugador medico = new Jugador("Doc", new Medico());
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);
        jugadores.agregar(medico);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victima);
        partida.elegirProteger(medico, victima);
        partida.resolverFaseActual();

        assertTrue(victima.estaVivo());
        assertTrue(partida.resumen().toLowerCase().contains("nadie"));
    }

    @Test
    public void elDetectiveInvestigaAUnMafiosoDuranteLaNocheYRecibeMafia() {
        Detective detectiveRol = new Detective();
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Jugador detective = new Jugador("Sherlock", detectiveRol);
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);
        jugadores.agregar(detective);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victima);
        partida.elegirInvestigar(detective, mafioso);
        partida.resolverFaseActual();

        assertTrue(detectiveRol.resultadoInvestigacion().esMismoBando(new BandoMafia()));
    }

    @Test
    public void elDetectiveInvestigaAUnCiudadanoDuranteLaNocheYRecibeCiudadano() {
        Detective detectiveRol = new Detective();
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador detective = new Jugador("Detective", detectiveRol);

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);
        jugadores.agregar(detective);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(ciudadano);
        partida.elegirInvestigar(detective, ciudadano);
        partida.resolverFaseActual();

        assertTrue(detectiveRol.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elDetectiveInvestigaAlPadrinoDuranteLaNocheYRecibeCiudadano() {
        Detective detectiveRol = new Detective();
        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador detective = new Jugador("Detective", detectiveRol);

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(padrino);
        jugadores.agregar(ciudadano);
        jugadores.agregar(detective);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(ciudadano);
        partida.elegirInvestigar(detective, padrino);
        partida.resolverFaseActual();

        assertTrue(detectiveRol.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elDetectiveNoPuedeInvestigarAUnJugadorEliminadoDuranteLaNoche() {
        Detective detectiveRol = new Detective();
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador detective = new Jugador("Detective", detectiveRol);
        Jugador eliminado = new Jugador("Ana", new Ciudadano());
        eliminado.eliminar();

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(detective);
        jugadores.agregar(eliminado);

        Partida partida = new Partida(jugadores);

        assertThrows(InvestigacionInvalidaException.class, () -> partida.elegirInvestigar(detective, eliminado));
    }

    @Test
    public void laMafiaNoPuedeElegirComoVictimaAUnMafiosoDuranteLaNoche() {
        Jugador mafioso1 = new Jugador("Mafioso1", new Mafioso());
        Jugador mafioso2 = new Jugador("Mafioso2", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso1);
        jugadores.agregar(mafioso2);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () ->
                partida.registrarVotoMafia(mafioso2));
    }




    @Test
    public void laMafiaNoPuedeElegirComoVictimaAUnPadrinoDuranteLaNoche() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(padrino);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () ->
                partida.registrarVotoMafia(padrino));
    }


    @Test
    public void laMafiaNoPuedeElegirComoVictimaAUnJugadorEliminadoDuranteLaNoche() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador eliminado = new Jugador("Ana", new Ciudadano());
        eliminado.eliminar();

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(eliminado);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () ->
                partida.registrarVotoMafia(eliminado));
    }
}