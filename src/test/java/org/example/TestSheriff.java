package org.example;

import org.example.model.BandoCiudadano;


import org.example.model.Sheriff;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSheriff {

    @Test
    public void elSheriffPerteneceAlBandoCiudadano() {
        Sheriff sheriff = new Sheriff();

        assertTrue(sheriff.bando().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elSheriffPuedeRevelarseUnaVez() {
        Sheriff sheriff = new Sheriff();

        sheriff.revelarse();

        assertTrue(sheriff.estaRevelado());
    }

}
