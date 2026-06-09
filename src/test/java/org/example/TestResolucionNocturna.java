package org.example;

import org.example.model.Jugador;
import org.example.model.ResolucionNocturna;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestResolucionNocturna {

    @Test
    void siLaVictimaDeLaMafiaEstaProtegidaNoEsEliminada() {
        ResolucionNocturna resolucion = new ResolucionNocturna();
        Jugador victima = new Jugador();

        resolucion.registrarAtaque(victima);
        resolucion.registrarProteccion(victima);

        resolucion.resolver();

        assertTrue(victima.devolverEstado().estaVivo());
    }

    @Test
    void siLaVictimaDeLaMafiaNoEstaProtegidaEsEliminada() {
        ResolucionNocturna resolucion = new ResolucionNocturna();
        Jugador victima = new Jugador();

        resolucion.registrarAtaque(victima);

        resolucion.resolver();

        assertFalse(victima.devolverEstado().estaVivo());
    }
}