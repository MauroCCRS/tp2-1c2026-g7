package org.example.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class AccionesDisponiblesDiurnas implements AccionesDisponibles {
    private final AccionesDiurnas accionesDiurnas;

    public AccionesDisponiblesDiurnas(AccionesDiurnas accionesDiurnas) {
        this.accionesDiurnas = accionesDiurnas;
    }

    @Override
    public void ejecutarAccionNocturna(Consumer<AccionesNocturnas> accion, Supplier<RuntimeException> excepcion) {
        throw excepcion.get();
    }

    @Override
    public void ejecutarAccionDiurna(Consumer<AccionesDiurnas> accion, Supplier<RuntimeException> excepcion) {
        accion.accept(accionesDiurnas);
    }
}
