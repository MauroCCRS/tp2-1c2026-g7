package org.example;
import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestRolNombre {
    @Test
    void cadaRolDiceSuNombre() {
        assertEquals("Ciudadano", new Ciudadano().nombre());
        assertEquals("Mafioso", new Mafioso().nombre());
        assertEquals("Detective", new Detective().nombre());
        assertEquals("Medico", new Medico().nombre());
        assertEquals("Sheriff", new Sheriff().nombre());
    }

    @Test
    void elPadrinoSeRevelaComoPadrinoAunqueHeredeDeMafioso() {
        assertEquals("Padrino", new Padrino().nombre());
    }
}