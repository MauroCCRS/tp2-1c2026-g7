package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJugadorPuedeVerSuRol {
    @Test
    void unJugadorVeElRolEnSuPropiaCarta() {
        Jugador jugador = new Jugador("Ana", new Ciudadano());

        assertEquals("Ciudadano", jugador.cartaVistaPor(jugador).descripcion());
    }

    @Test
    void unJugadorNoVeElRolEnLaCartaDeOtroJugadorVivo() {
        Jugador ana = new Jugador("Ana", new Mafioso());
        Jugador beto = new Jugador("Beto", new Ciudadano());

        assertEquals("Carta oculta", ana.cartaVistaPor(beto).descripcion());
    }

    @Test
    void laCartaDeUnJugadorEliminadoQuedaReveladaParaLosDemas() {
        Jugador ana = new Jugador("Ana", new Mafioso());
        Jugador beto = new Jugador("Beto", new Ciudadano());
        beto.eliminar();

        assertEquals("Ciudadano", beto.cartaVistaPor(ana).descripcion());
    }
}