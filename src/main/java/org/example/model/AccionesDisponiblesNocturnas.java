package org.example.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AccionesDisponiblesNocturnas implements AccionesDisponibles {
    private final AccionesNocturnas accionesNocturnas;

    public AccionesDisponiblesNocturnas(AccionesNocturnas accionesNocturnas) {
        this.accionesNocturnas = accionesNocturnas;
    }

    @Override
    public void ejecutarAccionNocturna(Consumer<AccionesNocturnas> accion, Supplier<RuntimeException> excepcion) {
        accion.accept(accionesNocturnas);
    }

    @Override
    public void ejecutarAccionDiurna(Consumer<AccionesDiurnas> accion, Supplier<RuntimeException> excepcion) {
        throw excepcion.get();
    }
}
