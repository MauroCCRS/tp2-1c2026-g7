package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestDetective {

    @Test
    public void elDetectivePuedeInvestigarAMafiosoYCiudadano() {
        Detective detective = new Detective();

        Jugador jugadorCiudadano = new Jugador("Ciudadano", new Ciudadano());
        Jugador jugadorMafioso = new Jugador("Mafioso", new Mafioso());

        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(jugadorCiudadano);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));

        detective.elegirInvestigar(jugadorMafioso);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoMafia()));
    }

    @Test
    public void elDetectiveRecibeCiudadanoAlInvestigarAlPadrino(){
        Detective detective = new Detective();

        Jugador padrino = new Jugador("Padrino", new Padrino());

        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(padrino);
        detective.actuarEnNoche(resolucion);

        assertTrue(detective.resultadoInvestigacion().esMismoBando(new BandoCiudadano()));
    }

    @Test
    public void elDetectiveNoPuedeInvestigarAlMismoJugadorDosNochesSeguidas(){
        Detective detective = new Detective();
        Jugador jugadorInvestigado = new Jugador("Jugador investigado", new Ciudadano());
        ResolucionNocturna resolucion = new ResolucionNocturna();

        detective.elegirInvestigar(jugadorInvestigado);
        detective.actuarEnNoche(resolucion);

        assertThrows(VictimaInvalidaException.class, () -> {
            detective.elegirInvestigar(jugadorInvestigado);
        });
    }

    @Test
    public void alInvestigarUnJugadorEliminadoDaError(){

        Detective detective = new Detective();
        ResolucionNocturna resolucion = new ResolucionNocturna();

        Jugador jugadorEliminadoMock = mock(Jugador.class);
        when(jugadorEliminadoMock.estaVivo()).thenReturn(false);

        detective.elegirInvestigar(jugadorEliminadoMock);
        detective.actuarEnNoche(resolucion);

        assertThrows(VictimaInvalidaException.class, () -> {
            detective.elegirInvestigar(jugadorEliminadoMock);
        });

    }

}
