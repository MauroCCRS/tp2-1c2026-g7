package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TestMedico {

    @Test
    public void siElMedicoProtegeALaVictimaEstaSobreviveALaNoche() {
        Jugador victima = new Jugador("Ana", new Ciudadano());
        Medico medico = new Medico();
        medico.elegirProteger(victima);

        ResolucionNocturna resolucion = new ResolucionNocturna();
        resolucion.registrarAtaque(victima);
        medico.actuarEnNoche(resolucion);
        resolucion.resolver();

        assertTrue(victima.estaVivo());
    }

    @Test
    public void elMedicoNoPuedeProtegerAlMismoJugadorDosNochesSeguidas(){
        Medico medico = new Medico();
        Jugador jugadorVictima = new Jugador("Jugador victima", new Ciudadano());
        ResolucionNocturna resolucion = new ResolucionNocturna();

        medico.elegirProteger(jugadorVictima);
        medico.actuarEnNoche(resolucion);


        assertThrows(VictimaInvalidaException.class, () -> {
            medico.elegirProteger(jugadorVictima);
        });
    }
}