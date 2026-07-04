package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TestRolesMafiaEspeciales {

    @Test
    void elVotoDelPadrinoEsPrioritarioYConservaSuObjetivo() {
        Jugador objetivo = new Jugador("Ana", new Ciudadano());
        VotoPadrino voto = new VotoPadrino(objetivo);

        assertSame(objetivo, voto.objetivo());
        assertEquals(Optional.of(objetivo), voto.victimaPrioritaria());
    }

    @Test
    void elPadrinoCreaUnVotoPrioritarioDeMafia() {
        Jugador objetivo = new Jugador("Ana", new Ciudadano());
        VotoMafia voto = new Padrino().crearVotoMafia(objetivo);

        assertSame(objetivo, voto.objetivo());
        assertEquals(Optional.of(objetivo), voto.victimaPrioritaria());
    }

    @Test
    void elPadrinoSeVeComoCiudadanoAlSerInvestigado() {
        Bando resultado = new Padrino().resultadoAlSerInvestigado();

        assertTrue(resultado.esMismoBando(new BandoCiudadano()));
        assertFalse(resultado.esMismoBando(new BandoMafia()));
    }

    @Test
    void elVotoDelPadrinoDefineLaVictimaAunqueOtroMafiosoVoteDistinto() {
        Jugador padrino = new Jugador("Don", new Padrino());
        Jugador mafioso = new Jugador("Caro", new Mafioso());
        Jugador objetivoPadrino = new Jugador("Ana", new Ciudadano());
        Jugador objetivoMafioso = new Jugador("Beto", new Ciudadano());
        VotacionMafia votacion = new VotacionMafia(new SinVictima());

        votacion.votar(mafioso, objetivoMafioso);
        votacion.votar(padrino, objetivoPadrino);

        assertEquals(Optional.of(objetivoPadrino), votacion.victimaElegida());
    }

    @Test
    void sinVictimaNuncaDevuelveObjetivoAunqueHayaVotos() {
        Jugador objetivo = new Jugador("Ana", new Ciudadano());
        List<VotoMafia> votos = List.of(new VotoNormal(objetivo));

        assertTrue(new SinVictima().evaluarConsenso(votos).isEmpty());
    }

    @Test
    void elMezcladorAleatorioConservaLosMismosRolesYLaCantidad() {
        Rol ciudadano = new Ciudadano();
        Rol detective = new Detective();
        Rol medico = new Medico();
        Rol mafioso = new Mafioso();
        List<Rol> roles = new ArrayList<>(List.of(ciudadano, detective, medico, mafioso));

        new MezcladorAleatorioRoles().mezclar(roles);

        assertEquals(4, roles.size());
        assertTrue(roles.contains(ciudadano));
        assertTrue(roles.contains(detective));
        assertTrue(roles.contains(medico));
        assertTrue(roles.contains(mafioso));
    }
    @Test
    void elPadrinoIntegradoAlRepartoSeInvestigaComoCiudadanoDuranteLaPartida() {
        List<String> nombres = List.of("M1", "M2", "Dani", "Mica", "Don", "Sara", "Ana", "Beto", "Caro", "Luz");
        List<Rol> roles = new Configuracion(rolesOrdenados -> { }).armarRoles(nombres.size());
        Jugadores jugadores = new RepartidorRoles(rolesOrdenados -> { }).repartir(nombres, roles);
        Jugador detective = jugadorConRol(jugadores, "Detective");
        Jugador padrino = jugadorConRol(jugadores, "Padrino");
        Partida partida = new Partidas().clasica(jugadores);

        assertTrue(padrino.perteneceA(new BandoMafia()));

        partida.elegirInvestigar(detective, padrino);
        partida.resolverFaseActual();

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));
        assertFalse(detective.resultadoInvestigacion().esMismoBando(new BandoMafia()));
    }

    private Jugador jugadorConRol(Jugadores jugadores, String rol) {
        return jugadores.todos().stream()
                .filter(jugador -> jugador.cartaVistaPor(jugador).descripcion().equals(rol))
                .findFirst()
                .orElseThrow(() -> new AssertionError("No se repartio el rol " + rol));
    }
}
