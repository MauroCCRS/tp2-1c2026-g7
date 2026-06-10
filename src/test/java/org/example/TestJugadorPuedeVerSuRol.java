package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJugadorPuedeVerSuRol {

    @Test
    void unJugadorPuedePedirSuPropioRol() {
        Rol rol = new Ciudadano();
        Jugador jugador = new Jugador("Ana", rol);

        assertSame(rol, jugador.rolVistoPor(jugador));
        assertTrue(jugador.rolVistoPor(jugador).esVisible());
    }

    @Test
    void unJugadorNoPuedePedirElRolDeOtroJugador() {
        Jugador ana = new Jugador("Ana", new Mafioso());
        Jugador beto = new Jugador("Beto", new Ciudadano());

        assertFalse(ana.rolVistoPor(beto).esVisible());
    }
}