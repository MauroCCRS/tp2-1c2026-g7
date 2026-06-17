package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    public void elDetectiveInvestigaDuranteLaNocheYRecibeElBandoReal() {
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
}