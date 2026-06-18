package org.example;
import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestCarta {
    @Test
    void unaCartaReciénCreadaEstaOcultaYNoMuestraElRol() {
        Carta carta = new Carta(new Mafioso());

        assertEquals("Carta oculta", carta.descripcion());
    }

    @Test
    void unaCartaReveladaMuestraElNombreDelRol() {
        Carta carta = new Carta(new Mafioso());

        carta.revelar();

        assertEquals("Mafioso", carta.descripcion());
    }

    @Test
    void elPadrinoReveladoMuestraQueEsPadrino() {
        Carta carta = new Carta(new Padrino());

        carta.revelar();

        assertEquals("Padrino", carta.descripcion());
    }
    @Test
    void laCartaDeUnJugadorEliminadoSeRevela() {
        Jugador ana = new Jugador("Ana", new Mafioso());
        Jugador beto = new Jugador("Beto", new Ciudadano());

        assertEquals("Carta oculta", ana.cartaVistaPor(beto).descripcion());

        ana.eliminar();

        assertEquals("Mafioso", ana.cartaVistaPor(beto).descripcion());
    }
}