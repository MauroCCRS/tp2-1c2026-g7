package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestRepartidorDeRoles {

    @Test
    void alRepartirSeMezclanLosRoles() {
        MezcladorDeRoles mezclador = mock(MezcladorDeRoles.class);
        RepartidorRoles repartidor = new RepartidorRoles(mezclador);

        ListaJugadores jugadores = new ListaJugadores();
        jugadores.agregar(new Jugador());
        jugadores.agregar(new Jugador());

        List<Rol> roles = Arrays.asList(new Ciudadano(), new Mafioso());

        repartidor.repartir(jugadores, roles);

        verify(mezclador).mezclar(roles);
    }

    @Test
    void alRepartirCadaJugadorRecibeExactamenteUnRol() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        Jugador jugador1 = new Jugador();
        Jugador jugador2 = new Jugador();

        ListaJugadores jugadores = new ListaJugadores();
        jugadores.agregar(jugador1);
        jugadores.agregar(jugador2);

        Rol rol1 = new Ciudadano();
        Rol rol2 = new Mafioso();

        List<Rol> roles = Arrays.asList(rol1, rol2);

        repartidor.repartir(jugadores, roles);

        assertTrue(jugador1.tieneRolAsignado());
        assertTrue(jugador2.tieneRolAsignado());

        assertSame(rol1, jugador1.devolverRol(jugador1));
        assertSame(rol2, jugador2.devolverRol(jugador2));
    }

    @Test
    void noSePuedeRepartirSiLaCantidadDeRolesNoCoincideConLaCantidadDeJugadores() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        ListaJugadores jugadores = new ListaJugadores();
        jugadores.agregar(new Jugador());
        jugadores.agregar(new Jugador());

        List<Rol> roles = List.of(new Ciudadano());

        assertThrows(IllegalArgumentException.class,
                () -> repartidor.repartir(jugadores, roles));
    }
}