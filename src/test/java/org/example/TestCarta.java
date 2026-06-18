package org.example;
import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCarta {
    @Test
    void unaCartaRecienCreadaEstaOcultaYNoMuestraElRol() {
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
    void unMafiosoPuedeVerLaCartaDeSuCompliceMafioso() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador padrino = new Jugador("Padrino", new Padrino());

        assertEquals("Padrino", padrino.cartaVistaPor(mafioso).descripcion());
    }

    @Test
    void unCiudadanoNoPuedeVerLaCartaDeUnMafioso() {
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        assertEquals("Carta oculta", mafioso.cartaVistaPor(ciudadano).descripcion());
    }
    @Test
    void unMafiosoNoPuedeVerLaCartaDeUnCidadano() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());

        assertEquals("Carta oculta", ciudadano.cartaVistaPor(mafioso).descripcion());
    }
    @Test
    void unCiudadanoNoPuedeVerLaCartaDeOtroCiudadano() {
        Jugador ciudadano = new Jugador("Ciudadano", new Ciudadano());
        Jugador otroCiudadano = new Jugador("Otro ciudadano", new Ciudadano());

        assertEquals("Carta oculta", otroCiudadano.cartaVistaPor(ciudadano).descripcion());
    }

    @Test
    void unMafiosoPuedeConsultarSusComplicesYVerSusCartas() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador otroMafioso = new Jugador("Otro mafioso", new Mafioso());
        Jugador padrino = new Jugador("Padrino", new Padrino());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(otroMafioso);
        jugadores.agregar(padrino);

        List<Jugador> complices = jugadores.complicesMafiososDe(mafioso);

        assertEquals(2, complices.size());
        assertTrue(complices.contains(otroMafioso));
        assertTrue(complices.contains(padrino));
        assertEquals("Mafioso", otroMafioso.cartaVistaPor(mafioso).descripcion());
        assertEquals("Padrino", padrino.cartaVistaPor(mafioso).descripcion());
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