package org.example.model;

public class RegistroDiurno extends RegistroRonda {
    private final Jugador eliminado;

    public RegistroDiurno(int numeroRonda, Jugador eliminado) {
        super(numeroRonda);
        this.eliminado = eliminado;
    }

    @Override
    public String describir() {
        return "Ronda " + numeroRonda + " (Dia): " + eliminado.nombre()
                + " fue eliminado por votacion. Era " + eliminado.descripcionDeCarta() + ".";
    }
}