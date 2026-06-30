package org.example;

import org.example.model.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestSheriff {

    @Test
    public void elSheriffPuedeDevolverElJugadorInvestigado() {
    Jugador sheriff = new Jugador("Sheriff", new Sheriff());
    Jugador mafioso = new Jugador("Mafioso", new Mafioso());

    sheriff.elegirInvestigar(mafioso);

    assertEquals(mafioso,sheriff.revelarJugador());
    }


    @Test
    public void soloElSheriffPuedeDevolverElJugadorInvestigado() {
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());
        Detective detective = new Detective();
        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(ciudadano);
        detective.actuarEnNoche(resolucion);

        assertThrows(RevelacionInvalidaException.class, () -> ciudadano.revelarJugador());
    }


    @Test
    public void elSheriffPerteneceAlBandoCiudadano() {
        Jugador sheriff = new Jugador("Sheriff", new Sheriff());

        assertTrue(sheriff.bando().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elSheriffPuedeRevelarse() {
        Jugador sheriff = new Jugador("Sheriff", new Sheriff());
        sheriff.revelarse();
        assertEquals("Sheriff",sheriff.descripcionDeCarta());

    }


    @Test
    public void soloElSheriffPuedeRevelarse(){
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());

        assertThrows(RevelacionInvalidaException.class, ciudadano::revelarse);
    }
}
