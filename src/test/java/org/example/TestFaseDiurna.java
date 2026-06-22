package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestFaseDiurna {

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    @Test
    public void elMasVotadoEsEliminadoYQuedaRegistrado() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador descartable = ciudadano("Dummy");
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(descartable);
        jugadores.agregar(ana);
        jugadores.agregar(beto);
        jugadores.agregar(caro);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(descartable);
        partida.resolverFaseActual();

        partida.nominar(ana);
        partida.nominar(beto);
        partida.votar(ana, beto);
        partida.votar(caro, beto);
        partida.votar(beto, ana);
        partida.resolverFaseActual();

        assertFalse(beto.estaVivo());
        assertTrue(partida.resumen().contains("Beto"));
    }

    @Test
    public void enEmpateConSinEliminacionNoMuereNadie() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador descartable = ciudadano("Dummy");
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(descartable);
        jugadores.agregar(ana);
        jugadores.agregar(beto);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(descartable);
        partida.resolverFaseActual();

        partida.nominar(ana);
        partida.nominar(beto);
        partida.votar(ana, beto);
        partida.votar(beto, ana);
        partida.resolverFaseActual();

        assertTrue(ana.estaVivo());
        assertTrue(beto.estaVivo());
        assertTrue(partida.resumen().toLowerCase().contains("nadie"));
    }

    @Test
    public void elCriterioDeEmpateConfiguradoEliminaAUnoDeLosEmpatados() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador descartable = ciudadano("Dummy");
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(descartable);
        jugadores.agregar(ana);
        jugadores.agregar(beto);

        CriterioEmpate eliminaAlPrimero = empatados -> java.util.List.of(empatados.get(0));
        Partida partida = new Partida(jugadores, eliminaAlPrimero);
        partida.registrarVotoMafia(descartable);
        partida.resolverFaseActual();

        partida.nominar(ana);
        partida.nominar(beto);
        partida.votar(ana, beto);
        partida.votar(beto, ana);
        partida.resolverFaseActual();

        assertTrue(ana.estaVivo() ^ beto.estaVivo());
    }

    @Test
    public void sinVotosNoMuereNadie() {
        Jugador mafioso = new Jugador("M1", new Mafioso());
        Jugador descartable = ciudadano("Dummy");
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(descartable);
        jugadores.agregar(ana);
        jugadores.agregar(beto);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(descartable);
        partida.resolverFaseActual();

        partida.resolverFaseActual();

        assertTrue(partida.resumen().toLowerCase().contains("nadie"));
    }

    @Test
    public void elSheriffPuedeRevelarseDuranteLaFaseDiurna() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador victimaNocturna = new Jugador("Ana", new Ciudadano());
        Jugador sheriff = new Jugador("Sheriff", new Sheriff());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victimaNocturna);
        jugadores.agregar(sheriff);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victimaNocturna);
        partida.resolverFaseActual();

        partida.revelarSheriff(sheriff);

        assertTrue( sheriff.estaRevelado() );
    }


    @Test
    public void elSheriffNoPuedeRevelarseDuranteLaFaseNocturna() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador sheriff = new Jugador("Sheriff", new Sheriff());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);
        jugadores.agregar(sheriff);

        Partida partida = new Partida(jugadores);

        assertThrows(RevelacionInvalidaException.class, () ->
                partida.revelarSheriff(sheriff));
    }


    @Test
    public void unJugadorQueNoEsSheriffNoPuedeRevelarseComoSheriffDuranteLaFaseDiurna() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador victimaNocturna = new Jugador("Ana", new Ciudadano());
        Jugador ciudadano = new Jugador("Beto", new Ciudadano());

        Jugadores jugadores = new Jugadores();
        jugadores.agregar(mafioso);
        jugadores.agregar(victimaNocturna);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);
        partida.registrarVotoMafia(victimaNocturna);
        partida.resolverFaseActual();

        assertThrows(RevelacionInvalidaException.class, () ->
                partida.revelarSheriff(ciudadano));
    }
}