package org.example;

import org.example.model.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestLogger {
    @BeforeEach
    public void inicializarLogger() {
        Logger.clear();
        Logger.setEnabled(true);
    }
    @Test
    public void registraUnEventoCorrectamente() {
        Logger logger = new Logger();
        String evento = "Comienza la partida";
        logger.log(evento);

        assertEquals(
                List.of(evento),
                logger.eventos()
        );
    }

    @Test
    public void registraVariosEventosCorrectamente() {
        Logger logger = new Logger();
        String evento = "Comienza la noche";
        String otroEvento = "Comienza la votacion";
        logger.log(evento);
        logger.log(otroEvento);
        assertEquals(
                List.of(
                        evento,
                        otroEvento
                ),
                logger.eventos()
        );
    }

    @Test
    public void noRegistraEventosCuandoEstaDeshabilitado() {
        Logger logger = new Logger();
        String evento = "Termino la votacion";
        logger.setEnabled(false);

        logger.log(evento);

        assertTrue(logger.eventos().isEmpty());
    }


}