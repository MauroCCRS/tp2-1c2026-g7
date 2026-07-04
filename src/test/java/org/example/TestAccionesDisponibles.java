package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestAccionesDisponibles {

    @Test
    void unaFaseNocturnaEjecutaAccionesNocturnasYRechazaAccionesDiurnas() {
        AccionesNocturnasRegistradas acciones = new AccionesNocturnasRegistradas();
        AccionesDisponibles disponibles = new AccionesDisponiblesNocturnas(acciones);

        disponibles.ejecutarAccionNocturna(accionesNocturnas -> acciones.marcarRegistro(),
                () -> new IllegalStateException("nocturna"));

        assertTrue(acciones.registroEjecutado);
        assertThrows(IllegalStateException.class, () -> disponibles.ejecutarAccionDiurna(
                accionesDiurnas -> fail("No deberia ejecutar acciones diurnas en fase nocturna"),
                () -> new IllegalStateException("diurna")));
    }

    @Test
    void unaFaseDiurnaEjecutaAccionesDiurnasYRechazaAccionesNocturnas() {
        AccionesDiurnasRegistradas acciones = new AccionesDiurnasRegistradas();
        AccionesDisponibles disponibles = new AccionesDisponiblesDiurnas(acciones);

        disponibles.ejecutarAccionDiurna(accionesDiurnas -> acciones.marcarRegistro(),
                () -> new IllegalStateException("diurna"));

        assertTrue(acciones.registroEjecutado);
        assertThrows(IllegalStateException.class, () -> disponibles.ejecutarAccionNocturna(
                accionesNocturnas -> fail("No deberia ejecutar acciones nocturnas en fase diurna"),
                () -> new IllegalStateException("nocturna")));
    }

    private static class AccionesNocturnasRegistradas implements AccionesNocturnas {
        private boolean registroEjecutado;

        void marcarRegistro() {
            registroEjecutado = true;
        }

        @Override
        public void registrar(AccionVotoMafia accion) {
            marcarRegistro();
        }

        @Override
        public void registrar(AccionInvestigar accion) {
            marcarRegistro();
        }

        @Override
        public void registrar(AccionProteger accion) {
            marcarRegistro();
        }
    }

    private static class AccionesDiurnasRegistradas implements AccionesDiurnas {
        private boolean registroEjecutado;

        void marcarRegistro() {
            registroEjecutado = true;
        }

        @Override
        public void registrar(AccionNominar accion) {
            marcarRegistro();
        }

        @Override
        public void registrar(AccionVotar accion) {
            marcarRegistro();
        }

        @Override
        public void registrar(AccionRevelarSheriff accion, Partida partida) {
            marcarRegistro();
        }
    }
}
