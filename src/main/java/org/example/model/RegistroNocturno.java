package org.example.model;

public class RegistroNocturno extends RegistroRonda {
    private final Jugador victima;

    public RegistroNocturno(int numeroRonda, Jugador victima) {
        super(numeroRonda);
        this.victima = victima;
    }

    @Override
    public String describir() {
        return "Ronda " + numeroRonda + " (Noche): " + victima.nombre() + " fue eliminado.";
    }
}