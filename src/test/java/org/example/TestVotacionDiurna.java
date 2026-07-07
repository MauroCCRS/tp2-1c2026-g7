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
    @Test
    public void unJugadorNoPuedeVotarDosVecesEnLaMismaVotacion() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        votacion.nominar(beto);
        votacion.nominar(caro);
        votacion.votar(ana, beto);

        assertThrows(VotacionInvalidaException.class, () -> votacion.votar(ana, caro));
        assertEquals(1L, votacion.conteoPorNominado().get(beto));
        assertEquals(0L, votacion.conteoPorNominado().get(caro));
    }

    @Test
    public void crearUnBallotageCuandoHayEmpate() {
        Jugador c1 = ciudadano("Ana");
        Jugador c2 = ciudadano("Beto");
        Jugador c3 = ciudadano("Caro");
        Jugador c4 = ciudadano("Dan");
        VotacionDiurna votacion = new VotacionDiurna(new Ballotage());

        votacion.nominar(c1);
        votacion.nominar(c2);

        votacion.votar(c1, c2);
        votacion.votar(c2, c1);
        votacion.votar(c3, c1);
        votacion.votar(c4, c2);

        Optional<VotacionDiurna> posibleBallotage = votacion.generarBallotage();

        assertTrue(posibleBallotage.isPresent());

        VotacionDiurna votacion2 = posibleBallotage.get();
        List<Jugador> nominadosBallotage = votacion2.obtenerNominados();

        assertEquals(2, nominadosBallotage.size());
        assertTrue(nominadosBallotage.contains(c1));
        assertTrue(nominadosBallotage.contains(c2));
    }

    @Test
    public void analizarResultadoEliminaAlJugadorCuandoHayUnGanadorDeterminado() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(beto, beto);
        votacion.votar(caro, ana);

        ResultadoVotacion resultado = votacion.analizarResultado();
        resultado.generarRegistro(1);

        assertFalse(beto.estaVivo());
        assertTrue(ana.estaVivo());
    }

    @Test
    public void analizarResultadoGeneraNuevaVotacionCuandoHayEmpateYElCriterioEsBallotage() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        VotacionDiurna votacion = new VotacionDiurna(new Ballotage());

        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(beto, ana);

        ResultadoVotacion resultado = votacion.analizarResultado();

        VotacionDiurna nuevaVotacion = resultado.obtenerSiguienteRonda()
                .orElseThrow(() -> new AssertionError("Debe generar una nueva ronda de ballotage."));

        List<Jugador> nominadosBallotage = nuevaVotacion.obtenerNominados();
        assertEquals(2, nominadosBallotage.size());
        assertTrue(nominadosBallotage.contains(ana));
        assertTrue(nominadosBallotage.contains(beto));
    }

    @Test
    public void analizarResultadoNoEliminaANadieCuandoHayEmpateClaro() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

        votacion.nominar(ana);
        votacion.nominar(beto);

        votacion.votar(ana, beto);
        votacion.votar(beto, ana);

        ResultadoVotacion resultado = votacion.analizarResultado();
        resultado.generarRegistro(1);

        assertTrue(ana.estaVivo());
        assertTrue(beto.estaVivo());

        assertTrue(resultado.obtenerSiguienteRonda().isEmpty());
    }
}

