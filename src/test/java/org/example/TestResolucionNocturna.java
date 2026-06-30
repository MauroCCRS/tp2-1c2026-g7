package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Tag("fases")
class TestResolucionNocturna {

    @Test
    void siLaVictimaDeLaMafiaEstaProtegidaNoHayVictima() {
        ResolucionNocturna resolucion = new ResolucionNocturna();
        Jugador victima = new Jugador("Ana", new Ciudadano());

        resolucion.registrarAtaque(victima);
        resolucion.registrarProteccion(victima);

        assertTrue(resolucion.resolver().isEmpty());
        assertTrue(victima.estaVivo());
    }

    @Test
    void siLaVictimaDeLaMafiaNoEstaProtegidaEsLaVictimaResultante() {
        ResolucionNocturna resolucion = new ResolucionNocturna();
        Jugador victima = new Jugador("Ana", new Ciudadano());

        resolucion.registrarAtaque(victima);

        assertEquals(Optional.of(victima), resolucion.resolver());
    }

    @Test
    void siElMedicoProtegeAOtroJugadorLaVictimaSigueSiendoElObjetivo() {
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Jugador protegido = new Jugador("Beto", new Ciudadano());

        ResolucionNocturna resolucion = new ResolucionNocturna();
        resolucion.registrarAtaque(victima);
        resolucion.registrarProteccion(protegido);

        assertEquals(Optional.of(victima), resolucion.resolver());
    }
}