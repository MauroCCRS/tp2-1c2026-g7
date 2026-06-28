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
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());
        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(caro, beto);
        votacion.votar(beto, ana);

        List<Jugador> ganadores = votacion.masVotados();
        assertEquals(1, ganadores.size());
        assertTrue(ganadores.contains(beto));
    }

    @Test
    public void elEmpateDevuelveATodosLosEmpatados() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());
        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(beto, ana);

        List<Jugador> ganadores = votacion.masVotados();
        assertEquals(2, ganadores.size());
        assertTrue(ganadores.contains(ana));
        assertTrue(ganadores.contains(beto));
    }

    @Test
    public void sinVotosNoHayGanadores() {
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        assertTrue(votacion.masVotados().isEmpty());
    }

    @Test
    public void resolverConGanadorClaroDevuelveEseJugador() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        votacion.nominar(ana);
        votacion.nominar(beto);
        votacion.votar(ana, beto);
        votacion.votar(caro, beto);
        votacion.votar(beto, ana);

        Optional<Jugador> eliminado = votacion.resolver();

        assertTrue(eliminado.isPresent());
        assertEquals(beto, eliminado.get());
    }

    @Test
    public void resolverConEmpateYCriterioSinEliminacionNoEliminaANadie() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());
        votacion.nominar(ana);
        votacion.nominar(beto);
        votacion.votar(ana, beto);
        votacion.votar(beto, ana);

        Optional<Jugador> eliminado = votacion.resolver();

        assertFalse(eliminado.isPresent());
    }

    @Test
    public void resolverSinVotosNoEliminaANadie() {
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        assertFalse(votacion.resolver().isPresent());
    }

    @Test
    public void resolverSoloConJugadoresVivos() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");

        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        ana.eliminar();

        assertThrows(VotacionInvalidaException.class, () -> {
            votacion.votar(ana, beto);
        });
    }

    @Test
    public void resolverSinObjetivoNominado() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");

        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        assertThrows(VotacionInvalidaException.class, () -> {
            votacion.votar(ana, beto);
        });
    }

    @Test
    public void resolverSoloConObjetivoNominado() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");

        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        votacion.nominar(ana);
        votacion.nominar(caro);
        votacion.votar(ana, caro);
        votacion.votar(beto, ana);
        votacion.votar(caro, ana);

        Optional<Jugador> resultado = votacion.resolver();

        assertTrue(resultado.isPresent());
        assertEquals(ana, resultado.get());
    }


}