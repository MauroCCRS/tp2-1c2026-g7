package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestDetective {

    @Test
    public void elDetectiveRecibeCiudadanoAlInvestigarAUnCiudadano() {
        Detective detective = new Detective();
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());
        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(ciudadano);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));
    }


    @Test
    public void elDetectiveRecibeMafiaAlInvestigarAUnMafioso() {
        Detective detective = new Detective();
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(mafioso);
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
