package org.example.model;

import java.util.Optional;

public class VotoPadrino implements VotoMafia{
    private final Jugador objetivo;

    public VotoPadrino(Jugador objetivo) { this.objetivo = objetivo; }

    @Override public Jugador objetivo() { return objetivo; }

    @Override
    public VotoMafia elegirSobre(VotoMafia otroVoto) {
        return this;
    }

    @Override
    public Optional<Jugador> victimaPrioritaria() {
        return Optional.of(objetivo);
    }
}
