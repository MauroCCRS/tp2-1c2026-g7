package org.example.model;

public abstract class Fase {

    protected final int numeroRonda;

    protected Fase(int numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public abstract RegistroRonda resolver();

    public abstract Fase siguiente(Partida partida);

    public void registrarVotoMafia(Jugador objetivo) {
    }
}