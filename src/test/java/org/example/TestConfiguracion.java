package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class TestConfiguracion {

    private final MezcladorDeRoles sinMezclar = roles -> {};

    private long contarDelBando(List<Rol> roles, Bando bando) {
        return roles.stream().filter(rol -> rol.bando().esMismoBando(bando)).count();
    }

    private long contarEspecie(List<Rol> roles, String nombre) {
        return roles.stream().filter(rol -> rol.nombre().equals(nombre)).count();
    }

    @Test
    void partidaChicaDeCincoTieneUnMafiosoUnEspecialYTresCiudadanos() {
        List<Rol> roles = new Configuracion(sinMezclar).armarRoles(5);

        assertEquals(5, roles.size());
        assertEquals(1, contarDelBando(roles, new BandoMafia()));
        assertEquals(3, contarEspecie(roles, "Ciudadano"));
    }

    @Test
    void partidaChicaDeSeisTieneUnMafiosoUnEspecialYCuatroCiudadanos() {
        List<Rol> roles = new Configuracion(sinMezclar).armarRoles(6);

        assertEquals(6, roles.size());
        assertEquals(1, contarDelBando(roles, new BandoMafia()));
        assertEquals(1, contarEspecie(roles, "Detective"));
        assertEquals(4, contarEspecie(roles, "Ciudadano"));
    }

    @Test
    void partidaMedianaDeOchoTieneDosMafiososDetectiveYMedico() {
        List<Rol> roles = new Configuracion(sinMezclar).armarRoles(8);

        assertEquals(8, roles.size());
        assertEquals(2, contarDelBando(roles, new BandoMafia()));
        assertEquals(1, contarEspecie(roles, "Detective"));
        assertEquals(1, contarEspecie(roles, "Medico"));
    }

    @Test
    void partidaGrandeDeDoceTieneTodosLosRolesEspecialesIncluidoPadrino() {
        List<Rol> roles = new Configuracion(sinMezclar).armarRoles(12);

        assertEquals(12, roles.size());
        assertEquals(3, contarDelBando(roles, new BandoMafia()));
        assertEquals(1, contarEspecie(roles, "Detective"));
        assertEquals(1, contarEspecie(roles, "Medico"));
        assertEquals(1, contarEspecie(roles, "Sheriff"));
        assertEquals(1, contarEspecie(roles, "Padrino"));
    }

    @Test
    void elRolEspecialDePartidaChicaDependeDelMezclador() {
        MezcladorDeRoles ponerMedicoPrimero = roles ->
                roles.sort((a, b) -> a.nombre().equals("Medico") ? -1 : 1);
        List<Rol> roles = new Configuracion(ponerMedicoPrimero).armarRoles(6);

        assertEquals(1, contarEspecie(roles, "Medico"));
        assertEquals(0, contarEspecie(roles, "Detective"));
    }

    @Test
    void unaCantidadFueraDeRangoEsRechazada() {
        assertThrows(CantidadDeJugadoresInvalidaException.class,
                () -> new Configuracion(sinMezclar).armarRoles(4));
    }
}