package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@Tag("integracion")
class TestIntegrales {

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
    @Tag("reparto")
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
    @Tag("carta")
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
    @Tag("votacion")
    @Tag("mafia")
    void mafiaNoPuedeElegirComoVictimaAUnCompaneroMafiosoDentroDeLaPartida() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso1 = new Jugador("Mar", new Mafioso());
        Jugador mafioso2 = new Jugador("Berto", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso1);
        jugadores.agregar(mafioso2);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.registrarVotoMafia(mafioso1, mafioso2));

        assertTrue(mafioso1.estaVivo());
        assertTrue(mafioso2.estaVivo());
        assertTrue(ciudadano.estaVivo());
    }

    @Test
    @Tag("votacion")
    @Tag("mafia")
    void mafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauri", new Mafioso());
        Jugador ciudadanoEliminado = new Jugador("Anai", new Ciudadano());
        ciudadanoEliminado.eliminar();

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadanoEliminado);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.registrarVotoMafia(mafioso, ciudadanoEliminado));

        assertFalse(ciudadanoEliminado.estaVivo());
    }

    @Test
    @Tag("fases")
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
    @Tag("detective")
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
        partida.registrarVotoMafia(mafioso, victima);
        partida.resolverFaseActual();

        assertFalse(victima.estaVivo());
        assertTrue(rolDetective.resultadoInvestigacion().esMismoBando(new BandoMafia()));
    }

    @Test
    @Tag("reparto")
    void siNoHayUnRolPorCadaJugadorElRepartoEsInvalido() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        assertThrows(RepartoRolesInvalidoException.class, () ->
                repartidor.repartir(Arrays.asList("oso", "manu"), List.of(new Ciudadano())));
    }

    @Test
    @Tag("fases")
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
    @Tag("fases")
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