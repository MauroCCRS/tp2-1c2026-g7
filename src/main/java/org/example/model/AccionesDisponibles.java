package org.example.model;

import java.util.function.Consumer;
import java.util.function.Supplier;

public interface AccionesDisponibles {
    void ejecutarAccionNocturna(Consumer<AccionesNocturnas> accion, Supplier<RuntimeException> excepcion);

    void ejecutarAccionDiurna(Consumer<AccionesDiurnas> accion, Supplier<RuntimeException> excepcion);
}
