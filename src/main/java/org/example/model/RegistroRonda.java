package org.example.model;

public abstract class RegistroRonda {
    protected final int numeroRonda;

    protected RegistroRonda(int numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public abstract String describir();
}