package org.example.model;

public class RegistroSinEliminacionDiurna extends RegistroRonda {

    public RegistroSinEliminacionDiurna(int numeroRonda) {
        super(numeroRonda);
    }

    @Override
    public String describir() {
        return "Ronda " + numeroRonda + " (Dia): nadie fue eliminado.";
    }
}