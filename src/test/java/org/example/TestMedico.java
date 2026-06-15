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
    public void noPuedeProtegerAlMismoJugador() {
        Jugador protegido = new Jugador("Ana", new Ciudadano());
        Medico medico = new Medico();
        medico.elegirProteger(protegido);

        assertThrows(ProtegidoInvalidoException.class, () -> medico.elegirProteger(protegido));
    }

    @Test
    public void noPuedeProtegerAUnJugadorNoVivo() {
        Jugador protegido = new Jugador("Ana", new Ciudadano());
        Medico medico = new Medico();
        protegido.eliminar();

        assertThrows(ProtegidoInvalidoException.class, () -> medico.elegirProteger(protegido));
    }
}