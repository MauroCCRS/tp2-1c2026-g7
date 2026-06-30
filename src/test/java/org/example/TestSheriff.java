package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("sheriff")
public class TestSheriff {

    @Test
    public void elSheriffPerteneceAlBandoCiudadano() {
        Sheriff sheriff = new Sheriff();

        assertTrue(sheriff.bando().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elSheriffQuedaReveladoLuegoDeRevelarse() {
        Sheriff sheriff = new Sheriff();

        sheriff.revelarse();

        assertTrue(sheriff.estaRevelado());
    }

}