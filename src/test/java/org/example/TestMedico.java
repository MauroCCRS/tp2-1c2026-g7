package org.example;

import org.example.model.Jugador;
import org.example.model.Medico;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestMedico {
    @Test
    public void alProtegerAUnJugadorRegistraEsaProteccionComoLaUltima() {
        Medico medico = new Medico();
        Jugador objetivo = new Jugador();

        medico.proteger(objetivo);

        assertEquals(objetivo, medico.obtenerUltimaProteccion());
    }
}