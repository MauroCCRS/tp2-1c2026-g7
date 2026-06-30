package org.example.model;

public class VotoNormal implements VotoMafia{
    private final Jugador objetivo;

    public VotoNormal(Jugador objetivo) { this.objetivo = objetivo; }

    @Override public Jugador objetivo() { return objetivo; }
    @Override public boolean esPrioritario() { return false; }
}
