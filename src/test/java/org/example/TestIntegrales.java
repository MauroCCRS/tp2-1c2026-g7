package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class TestIntegrales {

    private List<Jugador> vivosDe(Jugadores jugadores) {
        List<Jugador> vivos = new ArrayList<>();
        jugadores.porCadaVivo(vivos::add);
        return vivos;
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

        ((FaseNocturna) partida.faseActual()).votarVictima(victima);
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

        ((FaseNocturna) partida.faseActual()).votarVictima(victima);
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

        assertSame(rolMafioso, ana.rolVistoPor(ana));
        assertSame(rolCiudadano, mauro.rolVistoPor(mauro));
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

        assertTrue(mauro.rolVistoPor(mauro).esVisible());
        assertFalse(mauro.rolVistoPor(ana).esVisible());
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

        assertThrows(VictimaInvalidaException.class, () ->
                ((FaseNocturna) partida.faseActual()).votarVictima(mafioso2)
        );

        assertTrue(mafioso1.estaVivo());
        assertTrue(mafioso2.estaVivo());
        assertTrue(ciudadano.estaVivo());
    }

    @Test
    void mafiaNoPuedeElegirComoVictimaAUnJugadorYaEliminadoDentroDeLaPartida() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauri", new Mafioso());
        Jugador ciudadanoEliminado = new Jugador("Anai", new Ciudadano());
        ciudadanoEliminado.eliminar();

        jugadores.agregar(mafioso);
        jugadores.agregar(ciudadanoEliminado);

        Partida partida = new Partida(jugadores);

        assertThrows(VictimaInvalidaException.class, () ->
                ((FaseNocturna) partida.faseActual()).votarVictima(ciudadanoEliminado)
        );

        assertFalse(ciudadanoEliminado.estaVivo());
    }

    @Test
    void luegoDeResolverLaNocheSePuedeJugarElDiaYEliminarPorVotacion() {
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

        ((FaseNocturna) partida.faseActual()).votarVictima(Agus);
        partida.resolverFaseActual();

        FaseDiurna dia = (FaseDiurna) partida.faseActual();

        dia.nominar(Jose);
        dia.votar(mafioso, Jose);
        dia.votar(Ricardo, Jose);

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

        ((FaseNocturna) partida.faseActual()).votarVictima(medico);
        partida.resolverFaseActual();

        FaseDiurna dia = (FaseDiurna) partida.faseActual();
        partida.resolverFaseActual();

        rolMedico.elegirProteger(ana);

        ((FaseNocturna) partida.faseActual()).votarVictima(ana);
        partida.resolverFaseActual();

        assertFalse(medico.estaVivo());
        assertFalse(ana.estaVivo());
    }

    @Test
    void dosNochesYUnDiaQuedanAcumuladosEnElResumenDeLaPartida() {
        Jugadores jugadores = new Jugadores();

        Jugador mafioso = new Jugador("Mauro", new Mafioso());
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador beto = new Jugador("Beto", new Ciudadano());
        Jugador cami = new Jugador("Cami", new Ciudadano());
        Jugador dani = new Jugador("Dani", new Ciudadano());

        jugadores.agregar(mafioso);
        jugadores.agregar(ana);
        jugadores.agregar(beto);
        jugadores.agregar(cami);
        jugadores.agregar(dani);

        Partida partida = new Partida(jugadores);

        ((FaseNocturna) partida.faseActual()).votarVictima(ana);
        partida.resolverFaseActual();

        FaseDiurna dia = (FaseDiurna) partida.faseActual();
        dia.votar(mafioso, beto);
        dia.votar(cami, beto);
        partida.resolverFaseActual();

        ((FaseNocturna) partida.faseActual()).votarVictima(cami);
        partida.resolverFaseActual();

        String resumen = partida.resumen();

        assertTrue(resumen.contains("Ronda 1 (Noche)"));
        assertTrue(resumen.contains("Ronda 1 (Dia)"));
        assertTrue(resumen.contains("Ronda 2 (Noche)"));
        assertTrue(resumen.contains("Ana"));
        assertTrue(resumen.contains("Beto"));
        assertTrue(resumen.contains("Cami"));
    }
}

//package org.example;
//
//import org.example.model.*;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class TestIntegrales {
//
//
//    @Test
//    void laMafiaAtacaAUnCiudadanoNoProtegidoYElCiudadanoQuedaEliminado() {
//        Jugadores jugadores = new Jugadores();
//
//        Jugador mafioso = new Jugador("Mauro", new Mafioso());
//        Jugador victima = new Jugador("Agus", new Ciudadano());
//        Jugador medico = new Jugador("Jose", new Medico());
//
//        jugadores.agregar(mafioso);
//        jugadores.agregar(victima);
//        jugadores.agregar(medico);
//
//        FaseNocturna noche = new FaseNocturna(1, jugadores);
//
//        noche.votarVictima(victima);
//        noche.resolver();
//
//        assertFalse(victima.estaVivo());
//        assertTrue(mafioso.estaVivo());
//        assertTrue(medico.estaVivo());
//    }
//
//    @Test
//    void laMafiaAtacaAUnCiudadanoProtegidoPorElMedicoYElCiudadanoSigueVivo() {
//        Jugadores jugadores = new Jugadores();
//
//        Jugador mafioso = new Jugador("Mauro", new Mafioso());
//        Jugador victima = new Jugador("Ricardo", new Ciudadano());
//
//        Medico rolMedico = new Medico();
//        Jugador medico = new Jugador("Mar", rolMedico);
//
//        jugadores.agregar(mafioso);
//        jugadores.agregar(victima);
//        jugadores.agregar(medico);
//
//        rolMedico.elegirProteger(victima);
//
//        FaseNocturna noche = new FaseNocturna(1, jugadores);
//
//        noche.votarVictima(victima);
//        noche.resolver();
//
//        assertTrue(victima.estaVivo());
//    }
//
//    @Test
//    void unJugadorSoloPuedeVerSuPropioRolDuranteLaPartida() {
//        Rol rolAna = new Ciudadano();
//
//        Jugador ana = new Jugador("Ana", rolAna);
//        Jugador beto = new Jugador("Beto", new Mafioso());
//
//        assertSame(rolAna, ana.rolVistoPor(ana));
//        assertFalse(ana.rolVistoPor(beto).esVisible());
//    }
//
//    @Test
//    void elRepartidorMezclaLosRolesYAsignaUnRolACadaJugador() {
//        MezcladorDeRoles mezcladorSinCambios = roles -> { };
//        RepartidorRoles repartidor = new RepartidorRoles(mezcladorSinCambios);
//
//        Jugadores jugadores = repartidor.repartir(
//                java.util.List.of("Ana", "Beto", "Cami"),
//                java.util.List.of(new Mafioso(), new Ciudadano(), new Medico())
//        );
//
//        assertEquals(3, jugadores.cantidadDeVivos());
//    }
//}