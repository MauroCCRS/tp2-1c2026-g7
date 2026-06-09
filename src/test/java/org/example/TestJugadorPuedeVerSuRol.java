package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestJugadorPuedeVerSuRol {

    @Test
    void unJugadorPuedePedirSuPropioRol() {
        Jugador jugador = new Jugador();
        Rol rol = new Ciudadano();

        jugador.cambiarRol(rol);

        assertSame(rol, jugador.devolverRol(jugador));
        assertTrue(jugador.devolverRol(jugador).esVisible());
    }

    @Test
    void unJugadorNoPuedePedirElRolDeOtroJugador() {
        Jugador ana = new Jugador();
        Jugador beto = new Jugador();

        Rol rolDeAna = new Mafioso();
        ana.cambiarRol(rolDeAna);

        Rol rolQueVeBeto = ana.devolverRol(beto);

        assertFalse(rolQueVeBeto.esVisible());
    }
}
