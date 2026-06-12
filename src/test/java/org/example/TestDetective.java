package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class TestDetective {

    @Test
    public void elDetectiveNoPuedeInvestigarAlMismoJugadorDosNochesSeguidas(){
        Detective detective = new Detective();
        Jugador jugadorInvestigado = mock(Jugador.class);
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
