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
        ListaJugadores jugadores = new ListaJugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);

        FaseNocturna fase = new FaseNocturna(1, jugadores);
        fase.votarVictima(victima);

        RegistroRonda registro = fase.resolver();

        assertFalse(victima.estaVivo());
        assertTrue(registro.describir().contains("Ana"));
    }

    @Test
    public void siElMedicoProtegeALaVictimaNadieMuere() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Medico medicoRol = new Medico();
        Jugador medico = new Jugador("Doc", medicoRol);
        ListaJugadores jugadores = new ListaJugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);
        jugadores.agregar(medico);

        FaseNocturna fase = new FaseNocturna(1, jugadores);
        fase.votarVictima(victima);
        medicoRol.elegirProteger(victima);

        RegistroRonda registro = fase.resolver();

        assertTrue(victima.estaVivo());
        assertTrue(registro.describir().toLowerCase().contains("nadie"));
    }

    @Test
    public void elDetectiveInvestigaDuranteLaNocheYRecibeElBandoReal() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Detective detectiveRol = new Detective();
        Jugador detective = new Jugador("Sherlock", detectiveRol);
        ListaJugadores jugadores = new ListaJugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victima);
        jugadores.agregar(detective);

        FaseNocturna fase = new FaseNocturna(1, jugadores);
        fase.votarVictima(victima);
        detectiveRol.elegirInvestigar(mafioso);

        fase.resolver();

        assertTrue(detectiveRol.resultadoInvestigacion().esMafia());
    }
}