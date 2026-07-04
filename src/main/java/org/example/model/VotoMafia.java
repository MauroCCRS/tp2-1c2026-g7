package org.example.model;

import java.util.Optional;

public interface VotoMafia {
    Jugador objetivo();

    default VotoMafia elegirSobre(VotoMafia otroVoto) {
        return otroVoto;
    }

    default Optional<Jugador> victimaPrioritaria() {
        return Optional.empty();
    }
}
