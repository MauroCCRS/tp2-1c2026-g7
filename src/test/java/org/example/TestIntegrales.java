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
    private Logger logger = new Logger();

    @Test
    void seReparteUnMazoControladoSeJuegaLaNocheYLaVictimaNoProtegidaMuere() {
        logger.log("=== Noche sin proteccion ===");
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Mauro", "Agus", "Jose", "Ricardo"),
                Arrays.asList(new Mafioso(), new Ciudadano(), new Medico(), new Ciudadano())
        );
        logger.log("Roles repartidos.");
        List<Jugador> vivos = vivosDe(jugadores);
        Jugador mafioso = vivos.get(0);
        Jugador victima = vivos.get(1);
        Jugador medico = vivos.get(2);


        Partida partida = new Partida(jugadores);
        logger.log("La mafia vota a Agus.");
        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();

        assertTrue(mafioso.estaVivo());
        assertFalse(victima.estaVivo());
        assertTrue(medico.estaVivo());
        logger.log(partida.resumen());
        assertTrue(partida.resumen().contains("Agus fue eliminado"));
    }

    @Test
    void seReparteUnMazoControladoMedicoProtegeALaVictimaYLaNocheTerminaSinEliminados() {
        logger.log("=== Noche con proteccion medica ===");
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Medico rolMedico = new Medico();

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Mauro", "Agus", "Jose", "Ricardo"),
                Arrays.asList(new Mafioso(), new Ciudadano(), rolMedico, new Ciudadano())
        );
        logger.log("Roles repartidos.");
        List<Jugador> vivos = vivosDe(jugadores);
        Jugador victima = vivos.get(1);

        rolMedico.elegirProteger(victima);
        logger.log("La mafia ataca a Agus.");
        logger.log("El medico protege a Agus.");
        Partida partida = new Partida(jugadores);

        partida.registrarVotoMafia(victima);
        partida.resolverFaseActual();
        logger.log(partida.resumen());
        assertTrue(victima.estaVivo());
        assertTrue(partida.resumen().contains("nadie fue eliminado"));
    }

    @Test
    void seControlaElMezcladorConMockitoYLaPartidaSeJuegaConElOrdenForzado() {
        logger.log("=== Reparto con mezclador controlado ===");
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
        logger.log("Ana recibe el rol mafioso.");
        logger.log("Mauro recibe el rol ciudadano.");
        assertEquals(descripcionRevelada(rolMafioso), ana.cartaVistaPor(ana).descripcion());
        assertEquals(descripcionRevelada(rolCiudadano), mauro.cartaVistaPor(mauro).descripcion());
        verify(mezclador).mezclar(anyList());
    }

    @Test
    void jugadorNoPuedeVerRolAjenoLuegoDeUnRepartoYAntesDeJugarLaNoche() {

        logger.log("=== Cartas ocultas al inicio de la partida ===");

        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Jugadores jugadores = repartidor.repartir(
                Arrays.asList("Mauro", "Ana", "Jose"),
                Arrays.asList(new Mafioso(), new Ciudadano(), new Medico())
        );

        List<Jugador> vivos = vivosDe(jugadores);
        Jugador mauro = vivos.get(0);
        Jugador ana = vivos.get(1);

        logger.log("Roles repartidos.");
        assertNotEquals(
                ana.cartaVistaPor(ana).descripcion(),
                ana.cartaVistaPor(mauro).descripcion()
        );
        String vistaDeAna = ana.cartaVistaPor(ana).descripcion();
        String vistaDeMauro = ana.cartaVistaPor(mauro).descripcion();

        logger.log("Ana consulta su propia carta: " + vistaDeAna);
        logger.log("Mauro intenta consultar la carta de Ana: " + vistaDeMauro);
    }

    @Test
    void mafiaNoPuedeElegirComoVictimaAUnCompaneroMafiosoDentroDeLaPartida() {
        logger.log("=== Objetivo mafioso invalido ===");
        logger.setEnabled(false);
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
        logger.setEnabled(true);
        logger.log("La mafia intento atacar a un compañero mafioso.");
        logger.log(partida.resumen());
    }

    @Test
    void mafiaNoPuedeElegirComoVictimaAUnJugadorEliminado() {
        logger.log("=== Objetivo eliminado ===");
        logger.setEnabled(false);
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauri", new Mafioso());
        Jugador ciudadanoEliminado = new Jugador("Anai", new Ciudadano());
        ciudadanoEliminado.eliminar();

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadanoEliminado);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.registrarVotoMafia(ciudadanoEliminado));

        assertFalse(ciudadanoEliminado.estaVivo());
        logger.log("La mafia intento atacar a un jugador ya eliminado.");
        logger.log("Resultado : la accion fue rechazada.");
        logger.setEnabled(true);
        logger.log(partida.resumen());
    }

    @Test
    void seJuegaNocheYLuegoDiaConEliminacionPorVotacion() {
        logger.log("=== Ciclo completo: noche y dia ===");
        logger.setEnabled(false);
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
        logger.setEnabled(true);
        logger.log("Durante la noche fue eliminado Agus.");
        //FaseDiurna dia = (FaseDiurna) partida.faseActual();

        partida.nominar(Jose);
        partida.votar(mafioso, Jose);
        partida.votar(Ricardo, Jose);

        partida.resolverFaseActual();

        assertFalse(Agus.estaVivo());
        assertFalse(Jose.estaVivo());
        assertTrue(partida.resumen().contains("Agus fue eliminado"));
        assertTrue(partida.resumen().contains("Jose fue eliminado por votacion"));

        logger.log("Durante el dia Jose fue eliminado por votación.");
        logger.log(partida.resumen());
    }

    @Test
    void nocheSinVictimaElegidaNoEliminaANadieYQuedaRegistradaComoNocheTranquila() {
        logger.log("=== Noche tranquila ===");
        logger.setEnabled(false);
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
        logger.setEnabled(true);
        logger.log("La mafia no seleccionó ninguna víctima.");
        logger.log("Resultado: nadie fue eliminado.");
    }

    @Test
    void medicoEliminadoNoPuedeProtegerEnLaNocheSiguiente() {
        logger.log("=== Medico eliminado ===");
        logger.setEnabled(false);
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
        logger.setEnabled(true);
        logger.log("Jose fue eliminado durante la primera noche.");
        logger.log("Intento proteger a Ana en la noche siguiente.");
        logger.log("Resultado: la proteccion no tuvo efecto y Ana fue eliminada.");
    }
    @Test
    void enElDiaSiHayEmpateNoSeEliminaANadie() {
        logger.log("=== Empate en votacion ===");
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

        logger.log(partida.resumen());
    }
    @Test
    void partidaConDetectiveInvestigaDuranteLaNocheYLaMafiaEliminaAOtroJugador() {
        logger.log("=== Investigacion  nocturna ===");
        logger.setEnabled(false);
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
        logger.log("Dani investigo a May durante la noche.");
        logger.log("Ana fue eliminada por la mafia.");
        logger.log("Resultado: el detective identifico correctamente a un mafioso.");
        logger.setEnabled(true);
        logger.log(partida.resumen());
    }

    @Test
    void siNoHayUnRolPorCadaJugadorElRepartoEsInvalido() {
        logger.log("=== Reparto invalido ===");
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        assertThrows(RepartoRolesInvalidoException.class, () ->
                repartidor.repartir(Arrays.asList("oso", "manu"), List.of(new Ciudadano())));
        logger.log("Resultado: se lanza una excepcion de reparto invalido.");
    }
    @Test
    void noSePuedeNominarDuranteLaFaseNocturna() {
        logger.log("=== Accion invalida en fase  nocturna ===");
        logger.setEnabled(false);
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(NominacionInvalidaException.class, () -> partida.nominar(ciudadano));
        logger.log("Se intento nominar a un jugador durante la noche.");
        logger.setEnabled(true);
        logger.log("Resultado: la nominacion fue rechazada.");
    }
    @Test
    void noSePuedeVotarDuranteLaFaseNocturnaComoSiFueraDia() {
        logger.log("=== Votacion fuera de fase ===");
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadano);

        Partida partida = new Partida(jugadores);

        assertThrows(VotacionInvalidaException.class, () -> partida.votar(mafioso, ciudadano));
        logger.log("Se intento realizar una votacion diurna durante la noche.");
        logger.log("Resultado: la votación fue rechazada.");
    }
}
