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
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {
        });
        List<String> nombres = Arrays.asList("Ana", "Beto");
        List<Rol> roles = Arrays.asList(new Ciudadano(), new Mafioso());
        Jugadores jugadores = repartidor.repartir(nombres, roles);
        List<Jugador> capturados = new ArrayList<>();
        jugadores.porCadaVivo(capturados::add);
        Jugador ana = capturados.get(0);
        Jugador beto = capturados.get(1);

        assertEquals("Ciudadano", ana.cartaVistaPor(ana).descripcion());
        assertEquals("Mafioso", beto.cartaVistaPor(beto).descripcion());
    }

    @Test
    void noSePuedeRepartirSiLaCantidadDeRolesNoCoincide() {
        RepartidorRoles repartidor = new RepartidorRoles(roles -> {
        });

        List<String> nombres = Arrays.asList("Ana", "Beto");
        List<Rol> roles = List.of(new Ciudadano());

        assertThrows(RepartoRolesInvalidoException.class,
                () -> repartidor.repartir(nombres, roles));
    }
}