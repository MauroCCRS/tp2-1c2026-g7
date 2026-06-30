package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@Tag("detective")
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
    @Tag("padrino")
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
    public void elDetectivePuedeInvestigarAlMismoJugadorSiInvestigoAOtroEnLaNocheIntermedia() {
        Detective detective = new Detective();
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador beto = new Jugador("Beto", new Ciudadano());

        detective.elegirInvestigar(ana);
        detective.actuarEnNoche(new ResolucionNocturna());

        detective.elegirInvestigar(beto);
        detective.actuarEnNoche(new ResolucionNocturna());

        assertDoesNotThrow(() -> detective.elegirInvestigar(ana));
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