package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestRepartidorDeRoles {

    @Test
    void alRepartirSeMezclanLosRoles() {
        MezcladorDeRoles mezclador = mock(MezcladorDeRoles.class);
        RepartidorRoles repartidor = new RepartidorRoles(mezclador);

        List<String> nombres = Arrays.asList("Ana", "Beto");
        List<Rol> roles = Arrays.asList(new Ciudadano(), new Mafioso());

        repartidor.repartir(nombres, roles);

        verify(mezclador).mezclar(roles);
    }

    @Test
    void alRepartirCadaJugadorRecibeSuRolEnOrden() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        List<String> nombres = Arrays.asList("Ana", "Beto");
        Rol rolAna = new Ciudadano();
        Rol rolBeto = new Mafioso();
        List<Rol> roles = Arrays.asList(rolAna, rolBeto);

        Jugadores jugadores = repartidor.repartir(nombres, roles);

        List<Jugador> capturados = new ArrayList<>();
        jugadores.porCadaVivo(capturados::add);

        Jugador ana = capturados.get(0);
        Jugador beto = capturados.get(1);
        assertSame(rolAna, ana.rolVistoPor(ana));
        assertSame(rolBeto, beto.rolVistoPor(beto));
    }

    @Test
    void noSePuedeRepartirSiLaCantidadDeRolesNoCoincide() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {});

        List<String> nombres = Arrays.asList("Ana", "Beto");
        List<Rol> roles = List.of(new Ciudadano());

        assertThrows(IllegalArgumentException.class,
                () -> repartidor.repartir(nombres, roles));
    }
}