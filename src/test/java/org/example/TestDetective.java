package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDetective {

    @Test
    public void elDetectivePuedeInvestigarAMafiosoYCiudadano() {
        Detective detective = new Detective();

        Jugador jugadorCiudadano = new Jugador("Ciudadano", new Ciudadano());
        Jugador jugadorMafioso = new Jugador("Mafioso", new Mafioso());

        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(jugadorCiudadano);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));

        detective.elegirInvestigar(jugadorMafioso);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoMafia()));
    }

    @Test
    public void elDetectiveRecibeCiudadanoAlInvestigarAlPadrino() {
        Detective detective = new Detective();

        Jugador padrino = new Jugador("Padrino", new Padrino());

        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(padrino);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elDetectiveNoPuedeInvestigarAlMismoJugadorDosNochesSeguidas() {
        Detective detective = new Detective();
        Jugador jugadorInvestigado = new Jugador("Jugador investigado", new Ciudadano());
        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(jugadorInvestigado);
        detective.actuarEnNoche(resolucion);

        assertThrows(InvestigacionInvalidaException.class, () -> {
            detective.elegirInvestigar(jugadorInvestigado);
        });
    }

    @Test
    public void alInvestigarUnJugadorEliminadoDaError() {

        Detective detective = new Detective();

        Jugador jugadorEliminado = new Jugador("Jugador eliminado", new Ciudadano());
        jugadorEliminado.eliminar();

        assertThrows(InvestigacionInvalidaException.class, () -> {
            detective.elegirInvestigar(jugadorEliminado);
        });

    }

}
