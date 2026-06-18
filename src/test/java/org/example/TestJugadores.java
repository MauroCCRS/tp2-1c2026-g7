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
    @Test
    void unMafiosoPuedeConocerASusComplicesMafiososVivos() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador padrino = new Jugador("Padrino", new Padrino());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(padrino);
        jugadores.agregar(ciudadano);

        List<Jugador> complices = jugadores.complicesMafiososDe(mafioso);

        assertEquals(1, complices.size());
        assertTrue(complices.contains(padrino));
        assertFalse(complices.contains(mafioso));
        assertFalse(complices.contains(ciudadano));
    }

    @Test
    void unCiudadanoNoObtieneComplicesMafiosos() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(ciudadano);
        jugadores.agregar(mafioso);

        assertThrows(JugadoresException.class, () -> {
            jugadores.complicesMafiososDe(ciudadano);
        });
    }

    @Test
    void noSeDevuelvenMafiososEliminadosComoComplices() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador compliceEliminado = new Jugador("MafiosoEliminado", new Mafioso());
        compliceEliminado.eliminar();

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(compliceEliminado);

        assertTrue(jugadores.complicesMafiososDe(mafioso).isEmpty());
    }
}