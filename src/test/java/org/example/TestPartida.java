package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestPartida {

    private Jugador mafioso(String nombre) {
        return new Jugador(nombre, new Mafioso());
    }

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    @Test
    public void laPartidaArrancaEnFaseNocturna() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        jugadores.agregar(ciudadano("C1"));
        jugadores.agregar(ciudadano("C2"));

        Partida partida = new Partida(jugadores);

        assertTrue(partida.faseActual() instanceof FaseNocturna);
    }

    @Test
    public void resolverLaNocheHaceAvanzarALaFaseDiurna() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        Jugador c1 = ciudadano("C1");
        jugadores.agregar(c1);
        jugadores.agregar(ciudadano("C2"));
        jugadores.agregar(ciudadano("C3"));

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(c1);
        partida.resolverFaseActual();

        assertTrue(partida.faseActual() instanceof FaseDiurna);
    }

    @Test
    public void noHayGanadorMientrasAmbosBandosSiguenEnJuego() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        jugadores.agregar(ciudadano("C1"));
        jugadores.agregar(ciudadano("C2"));
        jugadores.agregar(ciudadano("C3"));

        Partida partida = new Partida(jugadores);

        assertFalse(partida.resultado().isPresent());
    }

    @Test
    public void laMafiaGanaCuandoIgualaEnNumeroTrasEliminaciones() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        Jugador c1 = ciudadano("C1");
        Jugador c2 = ciudadano("C2");
        jugadores.agregar(c1);
        jugadores.agregar(c2);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(c1);
        partida.resolverFaseActual();

        assertTrue(partida.resultado().isPresent());
        assertTrue(partida.resultado().get().esMismoBando(new BandoMafia()));
    }

    @Test
    public void elResumenAcumulaLasRondasJugadas() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        Jugador victima = ciudadano("Ana");
        jugadores.agregar(victima);
        jugadores.agregar(ciudadano("C2"));
        jugadores.agregar(ciudadano("C3"));

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();

        assertTrue(partida.resumen().contains("Ana"));
    }
}