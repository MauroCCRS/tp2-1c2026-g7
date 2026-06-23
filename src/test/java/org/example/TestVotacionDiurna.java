package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class TestVotacionDiurna {

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    @Test
    public void elMasVotadoEsElUnicoGanadorPorMayoria() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        VotacionDiurna votacion = new VotacionDiurna();
        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(caro, beto);
        votacion.votar(beto, ana);

        List<Jugador> ganadores = votacion.ganadoresPorMayoria();

        assertEquals(1, ganadores.size());
        assertTrue(ganadores.contains(beto));
    }

    @Test
    public void elEmpateDevuelveATodosLosEmpatados() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        VotacionDiurna votacion = new VotacionDiurna();
        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(beto, ana);

        List<Jugador> ganadores = votacion.ganadoresPorMayoria();

        assertEquals(2, ganadores.size());
        assertTrue(ganadores.contains(ana));
        assertTrue(ganadores.contains(beto));
    }

    @Test
    public void sinVotosNoHayGanadores() {
        VotacionDiurna votacion = new VotacionDiurna();

        assertTrue(votacion.ganadoresPorMayoria().isEmpty());
    }

    @Test
    public void ganadorUnicoConGanadorClaroDevuelveEseJugador() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        VotacionDiurna votacion = new VotacionDiurna();

        votacion.nominar(ana);
        votacion.nominar(beto);
        votacion.votar(ana, beto);
        votacion.votar(caro, beto);
        votacion.votar(beto, ana);

        Optional<Jugador> ganador = votacion.ganadorUnico();

        assertTrue(ganador.isPresent());
        assertEquals(beto, ganador.get());
    }

    @Test
    public void conEmpateNoHayGanadorUnicoYSeDetectaElEmpate() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        VotacionDiurna votacion = new VotacionDiurna();
        votacion.nominar(ana);
        votacion.nominar(beto);
        votacion.votar(ana, beto);
        votacion.votar(beto, ana);

        assertFalse(votacion.ganadorUnico().isPresent());
        assertTrue(votacion.hayEmpate());
        assertEquals(2, votacion.empatados().size());
    }

    @Test
    public void sinVotosNoHayGanadorUnico() {
        VotacionDiurna votacion = new VotacionDiurna();

        assertFalse(votacion.ganadorUnico().isPresent());
    }

    @Test
    public void noSePuedeVotarConVotanteEliminado() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");

        VotacionDiurna votacion = new VotacionDiurna();

        ana.eliminar();

        assertThrows(VotacionInvalidaException.class, () -> {
            votacion.votar(ana, beto);
        });
    }

    @Test
    public void noSePuedeVotarAUnObjetivoNoNominado() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");

        VotacionDiurna votacion = new VotacionDiurna();

        assertThrows(VotacionInvalidaException.class, () -> {
            votacion.votar(ana, beto);
        });
    }

    @Test
    public void soloCuentanLosVotosAObjetivosNominados() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");

        VotacionDiurna votacion = new VotacionDiurna();

        votacion.nominar(ana);
        votacion.nominar(caro);
        votacion.votar(ana, caro);
        votacion.votar(beto, ana);
        votacion.votar(caro, ana);

        Optional<Jugador> ganador = votacion.ganadorUnico();

        assertTrue(ganador.isPresent());
        assertEquals(ana, ganador.get());
    }
}