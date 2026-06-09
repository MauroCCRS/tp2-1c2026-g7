package org.example;

import org.example.model.Jugador;
import org.example.model.Medico;
import org.junit.jupiter.api.Test;

public class TestMedico {
    @Test
    public void unJugadorAlSerProtegidoYElegidoPorMafiaSigueVivo() {
        Jugador medico = new Medico();
        Jugador victima = new Jugador();

        medico.proteger(victima);
        assertEquals(victima, medico.ultimaProteccion());
    };
}
