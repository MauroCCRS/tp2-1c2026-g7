package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestConfiguracion {
    @Test
    void configuracionChicoTieneComposicionCorrecta() {
        List<Rol> roles = new ConfiguracionChico().armarRoles();
        long cantidadMafiosos =
                roles.stream()
                        .filter(rol -> rol.bando() instanceof BandoMafia)
                        .count();

        long cantidadEspeciales =
                roles.stream()
                        .filter(rol ->
                                rol instanceof Detective ||
                                        rol instanceof Medico)
                        .count();
        long cantidadCiudadanos =
                roles.stream()
                        .filter(rol ->
                                rol instanceof Ciudadano)
                        .count();
        assertEquals(1, cantidadEspeciales);
        assertTrue(cantidadMafiosos >= 1 && cantidadMafiosos <= 2);
        assertEquals(
                roles.size(),
                cantidadMafiosos + cantidadCiudadanos + cantidadEspeciales
        );
    }
    @Test
    void configuracionMedianoTieneComposicionCorrecta() {
        List<Rol> roles = new ConfiguracionMediano().armarRoles();
        long cantidadMafiosos =
                roles.stream()
                        .filter(rol -> rol.bando() instanceof BandoMafia)
                        .count();

        long cantidadEspeciales =
                roles.stream()
                        .filter(rol ->
                                rol instanceof Detective ||
                                        rol instanceof Medico)
                        .count();
        long cantidadCiudadanos =
                roles.stream()
                        .filter(rol ->
                                rol instanceof Ciudadano)
                        .count();
        assertEquals(2, cantidadEspeciales);
        assertTrue(cantidadMafiosos >= 2 && cantidadMafiosos <= 3);
        assertEquals(
                roles.size(),
                cantidadMafiosos + cantidadCiudadanos + cantidadEspeciales
        );
    }
    @Test
    void configuracionGrandeTieneComposicionCorrecta() {
        List<Rol> roles = new ConfiguracionGrande().armarRoles();
        long cantidadMafiosos =
                roles.stream()
                        .filter(rol -> rol.bando() instanceof BandoMafia)
                        .count();

        long cantidadEspeciales =
                roles.stream()
                        .filter(rol ->
                                rol instanceof Detective ||
                                        rol instanceof Medico ||
                                            rol instanceof Padrino ||
                                                rol instanceof Sheriff)
                        .count();
        long cantidadCiudadanos =
                roles.stream()
                        .filter(rol ->
                                rol instanceof Ciudadano)
                        .count();
        assertEquals(4, cantidadEspeciales);
        assertEquals(3, cantidadMafiosos);
        assertEquals(
                roles.size(),
                cantidadMafiosos + cantidadCiudadanos + cantidadEspeciales - 1
        );
    }

}
