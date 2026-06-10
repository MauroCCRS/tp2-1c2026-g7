package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestResolucionNocturna {

    @Test
    void siLaVictimaDeLaMafiaEstaProtegidaNoEsEliminada() {
        ResolucionNocturna resolucion = new ResolucionNocturna();
        Jugador victima = new Jugador("Ana", new Ciudadano());

        resolucion.registrarAtaque(victima);
        resolucion.registrarProteccion(victima);
        resolucion.resolver();

        assertTrue(victima.estaVivo());
    }

    @Test
    void siLaVictimaDeLaMafiaNoEstaProtegidaEsEliminada() {
        ResolucionNocturna resolucion = new ResolucionNocturna();
        Jugador victima = new Jugador("Ana", new Ciudadano());

        resolucion.registrarAtaque(victima);
        resolucion.resolver();

        assertFalse(victima.estaVivo());
    }
}