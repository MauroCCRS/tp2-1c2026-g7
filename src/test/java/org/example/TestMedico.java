package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    public void elMedicoNoPuedeProtegerAlMismoJugadorDosNochesSeguidas() {
        Medico medico = new Medico();
        Jugador jugadorVictima = new Jugador("Jugador victima", new Ciudadano());
        ResolucionNocturna resolucion = new ResolucionNocturna();

        medico.elegirProteger(jugadorVictima);
        medico.actuarEnNoche(resolucion);


        assertThrows(ProteccionInvalidaException.class, () -> {
            medico.elegirProteger(jugadorVictima);
        });
    }

    @Test
    void elMedicoPuedeVolverAProtegerAUnJugadorSiAntesProtegioAOtro() {
        Medico medico = new Medico();
        Jugador ana = new Jugador("Ana", new Ciudadano());
        Jugador beto = new Jugador("Beto", new Ciudadano());

        medico.elegirProteger(ana);
        medico.actuarEnNoche(new ResolucionNocturna());

        medico.elegirProteger(beto);
        medico.actuarEnNoche(new ResolucionNocturna());

        assertDoesNotThrow(() -> medico.elegirProteger(ana));
    }

    @Test
    public void noPuedeProtegerAUnJugadorEliminado() {
        Jugador protegido = new Jugador("Ana", new Ciudadano());

        Medico medico = new Medico();

        protegido.eliminar();

        assertThrows(ProteccionInvalidaException.class, () -> medico.elegirProteger(protegido));
    }


}