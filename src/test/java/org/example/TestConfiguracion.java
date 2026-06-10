package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestConfiguracion {

    private long contarDelBando(List<Rol> roles, Bando bando) {
        return roles.stream().filter(rol -> rol.bando().esMismoBando(bando)).count();
    }

    @Test
    void configuracionChicoTieneBalanceDeBandosCorrecto() {
        List<Rol> roles = new ConfiguracionChico().armarRoles();
        long mafia = contarDelBando(roles, new BandoMafia());

        assertTrue(roles.size() == 5 || roles.size() == 6);
        assertTrue(mafia >= 1 && mafia <= 2);
        assertEquals(roles.size(), mafia + contarDelBando(roles, new BandoCiudadano()));
    }

    @Test
    void configuracionMedianoTieneBalanceDeBandosCorrecto() {
        List<Rol> roles = new ConfiguracionMediano().armarRoles();
        long mafia = contarDelBando(roles, new BandoMafia());

        assertTrue(roles.size() >= 7 && roles.size() <= 9);
        assertTrue(mafia >= 2 && mafia <= 3);
        assertEquals(roles.size(), mafia + contarDelBando(roles, new BandoCiudadano()));
    }

    @Test
    void configuracionGrandeTieneTresDeBandoMafia() {
        List<Rol> roles = new ConfiguracionGrande().armarRoles();
        long mafia = contarDelBando(roles, new BandoMafia());

        assertTrue(roles.size() >= 10 && roles.size() <= 12);
        assertEquals(3, mafia);
        assertEquals(roles.size(), mafia + contarDelBando(roles, new BandoCiudadano()));
    }
}