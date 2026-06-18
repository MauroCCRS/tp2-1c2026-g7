package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class TestIntegrales{

    private List<Jugador> vivosDe(Jugadores jugadores) {
        List<Jugador> vivos = new ArrayList<>();
        jugadores.porCadaVivo(vivos::add);
        return vivos;
    }

    private String descripcionRevelada(Rol rol) {
        Carta carta = new Carta(rol);
        carta.revelar();
        return carta.descripcion();
    }

    @Test
    void seReparteUnMazoControladoSeJuegaLaNocheYLaVictimaNoProtegidaMuere() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Mauro", "Agus", "Jose", "Ricardo"),
                Arrays.asList(new Mafioso(), new Ciudadano(), new Medico(), new Ciudadano())
        );

        List<Jugador> vivos = vivosDe(jugadores);
        Jugador mafioso = vivos.get(0);
        Jugador victima = vivos.get(1);
        Jugador medico = vivos.get(2);

        Partida partida = new Partida(jugadores);

        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();

        assertTrue(mafioso.estaVivo());
        assertFalse(victima.estaVivo());
        assertTrue(medico.estaVivo());
        assertTrue(partida.resumen().contains("Agus fue eliminado"));
    }

    @Test
    void seReparteUnMazoControladoMedicoProtegeALaVictimaYLaNocheTerminaSinEliminados() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Medico rolMedico = new Medico();

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Mauro", "Agus", "Jose", "Ricardo"),
                Arrays.asList(new Mafioso(), new Ciudadano(), rolMedico, new Ciudadano())
        );

        List<Jugador> vivos = vivosDe(jugadores);
        Jugador victima = vivos.get(1);

        rolMedico.elegirProteger(victima);

        Partida partida = new Partida(jugadores);

        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();

        assertTrue(victima.estaVivo());
        assertTrue(partida.resumen().contains("nadie fue eliminado"));
    }

    @Test
    void seControlaElMezcladorConMockitoYLaPartidaSeJuegaConElOrdenForzado() {
        MezcladorDeRoles mezclador = mock(MezcladorDeRoles.class);

        doAnswer(invocacion -> {
            List<Rol> roles = invocacion.getArgument(0);
            Collections.swap(roles, 0, 1);
            return null;
        }).when(mezclador).mezclar(anyList());

        RepartidorRoles repartidor = new RepartidorRoles(mezclador);

        Rol rolCiudadano = new Ciudadano();
        Rol rolMafioso = new Mafioso();

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Ana", "Mauro"),
                new ArrayList<>(Arrays.asList(rolCiudadano, rolMafioso))
        );

        List<Jugador> vivos = vivosDe(jugadores);
        Jugador ana = vivos.get(0);
        Jugador mauro = vivos.get(1);

        assertEquals(descripcionRevelada(rolMafioso), ana.cartaVistaPor(ana).descripcion());
        assertEquals(descripcionRevelada(rolCiudadano), mauro.cartaVistaPor(mauro).descripcion());
        verify(mezclador).mezclar(anyList());
    }

    @Test
    void jugadorNoPuedeVerRolAjenoLuegoDeUnRepartoYAntesDeJugarLaNoche() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Mauro", "Ana", "Jose"),
                Arrays.asList(new Mafioso(), new Ciudadano(), new Medico())
        );

        List<Jugador> vivos = vivosDe(jugadores);
        Jugador mauro = vivos.get(0);
        Jugador ana = vivos.get(1);

        assertNotEquals(
                ana.cartaVistaPor(ana).descripcion(),
                ana.cartaVistaPor(mauro).descripcion()
        );
    }

    @Test
    void mafiaNoPuedeElegirComoVictimaAUnCompaneroMafiosoDentroDeLaPartida() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso1 = new Jugador("Mar", new Mafioso());
        Jugador mafioso2 = new Jugador("Berto", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso1);
        jugadores.agregar(mafioso2);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.registrarVotoMafia(mafioso2));

        assertTrue(mafioso1.estaVivo());
        assertTrue(mafioso2.estaVivo());
        assertTrue(ciudadano.estaVivo());
    }

    @Test
    void mafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauri", new Mafioso());
        Jugador ciudadanoEliminado = new Jugador("Anai", new Ciudadano());
        ciudadanoEliminado.eliminar();

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadanoEliminado);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.registrarVotoMafia(ciudadanoEliminado));

        assertFalse(ciudadanoEliminado.estaVivo());
    }

    @Test
    void seJuegaNocheYLuegoDiaConEliminacionPorVotacion() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador Agus = new Jugador("Agus", new Ciudadano());
        Jugador Jose = new Jugador("Jose", new Ciudadano());
        Jugador Ricardo = new Jugador("Ricardo", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(Agus);
        jugadores.agregar(Jose);
        jugadores.agregar(Ricardo);

        Partida partida = new Partida(jugadores);

        partida.registrarVotoMafia(Agus);
        partida.resolverFaseActual();

        //FaseDiurna dia = (FaseDiurna) partida.faseActual();

        partida.nominar(Jose);
        partida.votar(mafioso, Jose);
        partida.votar(Ricardo, Jose);

        partida.resolverFaseActual();

        assertFalse(Agus.estaVivo());
        assertFalse(Jose.estaVivo());
        assertTrue(partida.resumen().contains("Agus fue eliminado"));
        assertTrue(partida.resumen().contains("Jose fue eliminado por votacion"));
    }

    @Test
    void nocheSinVictimaElegidaNoEliminaANadieYQuedaRegistradaComoNocheTranquila() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador medico = new Jugador("Jose", new Medico());

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);
        jugadores.agregar(medico);

        Partida partida = new Partida(jugadores);

        partida.resolverFaseActual();

        assertTrue(mafioso.estaVivo());
        assertTrue(ciudadano.estaVivo());
        assertTrue(medico.estaVivo());
        assertTrue(partida.resumen().contains("nadie fue eliminado"));
    }

    @Test
    void medicoEliminadoNoPuedeProtegerEnLaNocheSiguiente() {
        Medico rolMedico = new Medico();

        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador medico = new Jugador("Jose", rolMedico);
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador cami = new Jugador("Cami", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(medico);
        jugadores.agregar(ana);
        jugadores.agregar(cami);

        Partida partida = new Partida(jugadores);

        partida.registrarVotoMafia(medico);
        partida.resolverFaseActual();

        partida.resolverFaseActual();

        rolMedico.elegirProteger(ana);

        partida.registrarVotoMafia(ana);
        partida.resolverFaseActual();

        assertFalse(medico.estaVivo());
        assertFalse(ana.estaVivo());
    }
    @Test
    void enElDiaSiHayEmpateNoSeEliminaANadie() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador rich = new Jugador("Rich", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(ana);
        jugadores.agregar(rich);

        Partida partida = new Partida(jugadores);

        partida.resolverFaseActual();

        partida.nominar(ana);
        partida.nominar(rich);
        partida.votar(ana, rich);
        partida.votar(rich, ana);
        partida.resolverFaseActual();

        assertTrue(ana.estaVivo());
        assertTrue(rich.estaVivo());
        assertTrue(partida.resumen().contains("Ronda 1 (Dia): nadie fue eliminado"));
    }
    @Test
    void partidaConDetectiveInvestigaDuranteLaNocheYLaMafiaEliminaAOtroJugador() {
        Detective rolDetective = new Detective();
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("May", new Mafioso());
        Jugador detective = new Jugador("Dani", rolDetective);
        Jugador victima = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(detective);
        jugadores.agregar(victima);

        Partida partida = new Partida(jugadores);

        partida.elegirInvestigar(detective, mafioso);
        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();

        assertFalse(victima.estaVivo());
        assertTrue(rolDetective.resultadoInvestigacion().esMismoBando(new BandoMafia()));
    }

    @Test
    void siNoHayUnRolPorCadaJugadorElRepartoEsInvalido() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        assertThrows(RepartoRolesInvalidoException.class, () ->
                repartidor.repartir(Arrays.asList("oso", "manu"), List.of(new Ciudadano())));
    }
    @Test
    void noSePuedeNominarDuranteLaFaseNocturna() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(NominacionInvalidaException.class, () -> partida.nominar(ciudadano));
    }
    @Test
    void noSePuedeVotarDuranteLaFaseNocturnaComoSiFueraDia() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.votar(mafioso, ciudadano));
    }
}
