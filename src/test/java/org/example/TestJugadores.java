package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TestJugadores {

    @Test
    void alAgregarUnJugadorVivoAumentaLaCantidadDeVivos() {
        Jugadores jugadores = new Jugadores();
        Jugador jugador = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(jugador);

        assertEquals(1, jugadores.cantidadDeVivos());
    }

    @Test
    void cuentaLosVivosDeCadaBandoPorSeparado() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(new Jugador("Ana", new Ciudadano()));
        jugadores.agregar(new Jugador("Carla", new Ciudadano()));
        jugadores.agregar(new Jugador("Beto", new Mafioso()));

        assertEquals(1, jugadores.cantidadVivosDelBando(new BandoMafia()));
        assertEquals(2, jugadores.cantidadVivosDelBando(new BandoCiudadano()));
    }

    @Test
    void unJugadorEliminadoNoCuentaComoVivo() {
        Jugadores jugadores = new Jugadores();
        Jugador jugador = new Jugador("Ana", new Ciudadano());
        jugadores.agregar(jugador);

        jugador.eliminar();

        assertEquals(0, jugadores.cantidadDeVivos());
    }

    @Test
    void porCadaVivoSoloRecorreLosVivos() {
        Jugadores jugadores = new Jugadores();
        Jugador viva = new Jugador("Ana", new Ciudadano());
        Jugador muerto = new Jugador("Beto", new Ciudadano());
        jugadores.agregar(viva);
        jugadores.agregar(muerto);
        muerto.eliminar();

        List<Jugador> recorridos = new ArrayList<>();
        jugadores.porCadaVivo(recorridos::add);

        assertEquals(1, recorridos.size());
        assertSame(viva, recorridos.get(0));
    }
}