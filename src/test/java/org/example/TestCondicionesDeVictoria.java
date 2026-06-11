package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestCondicionesDeVictoria {

    private Jugador mafioso(String nombre) {
        return new Jugador(nombre, new Mafioso());
    }

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    @Test
    public void laMafiaGanaCuandoIgualaEnNumeroALosCiudadanos() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        jugadores.agregar(ciudadano("C1"));

        boolean gano = new BandoMafia().ganoSegun(jugadores);

        assertTrue(gano);
    }

    @Test
    public void laMafiaNoGanaSiHayMasCiudadanosVivos() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        jugadores.agregar(ciudadano("C1"));
        jugadores.agregar(ciudadano("C2"));

        boolean gano = new BandoMafia().ganoSegun(jugadores);

        assertFalse(gano);
    }

    @Test
    public void laMafiaGanaAlSuperarEnNumeroTrasUnaEliminacion() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        jugadores.agregar(mafioso("M2"));
        Jugador ciudadano1 = ciudadano("C1");
        jugadores.agregar(ciudadano1);
        jugadores.agregar(ciudadano("C2"));
        ciudadano1.eliminar();

        boolean gano = new BandoMafia().ganoSegun(jugadores);

        assertTrue(gano);
    }

    @Test
    public void losCiudadanosGananCuandoNoQuedanMafiososVivos() {
        Jugadores jugadores = new Jugadores();
        Jugador mafioso = mafioso("M1");
        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano("C1"));
        mafioso.eliminar();

        boolean gano = new BandoCiudadano().ganoSegun(jugadores);

        assertTrue(gano);
    }

    @Test
    public void losCiudadanosNoGananSiQuedaUnMafiosoVivo() {
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso("M1"));
        jugadores.agregar(ciudadano("C1"));

        boolean gano = new BandoCiudadano().ganoSegun(jugadores);

        assertFalse(gano);
    }
}
