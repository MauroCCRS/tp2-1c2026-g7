package org.example.model;

public class RegistroNocheTranquila extends RegistroRonda {

    public RegistroNocheTranquila(int numeroRonda) {
        super(numeroRonda);
    }

    @Override
    public String describir() {
        return "Ronda " + numeroRonda + " (Noche): nadie fue eliminado.";
    }
}