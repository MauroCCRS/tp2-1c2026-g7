package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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

        List<Jugador> ganadores = votacion.ganadoresPorMayoria();

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

        List<Jugador> ganadores = votacion.ganadoresPorMayoria();

        assertEquals(2, ganadores.size());
        assertTrue(ganadores.contains(ana));
        assertTrue(ganadores.contains(beto));
    }

    @Test
    public void sinVotosNoHayGanadores() {
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        assertTrue(votacion.ganadoresPorMayoria().isEmpty());
    }

    @Test
    public void resolverConGanadorClaroDevuelveEseJugador() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

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
}