package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestRegistros {

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    @Test
    public void elRegistroNocturnoNarraALaVictima() {
        RegistroNocturno registro = new RegistroNocturno(1, ciudadano("Ana"));

        assertTrue(registro.describir().contains("Ana"));
    }

    @Test
    public void elRegistroDiurnoNarraAlEliminado() {
        RegistroDiurno registro = new RegistroDiurno(2, ciudadano("Beto"));

        assertTrue(registro.describir().contains("Beto"));
    }

    @Test
    public void elResumenIncluyeTodosLosRegistros() {
        RegistroPartida registro = new RegistroPartida();
        registro.agregarRegistro(new RegistroNocturno(1, ciudadano("Ana")));
        registro.agregarRegistro(new RegistroDiurno(2, ciudadano("Beto")));

        String resumen = registro.generarResumen();

        assertTrue(resumen.contains("Ana"));
        assertTrue(resumen.contains("Beto"));
    }
}