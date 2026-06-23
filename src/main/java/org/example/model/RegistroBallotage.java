package org.example.model;
import java.util.List;

public class RegistroBallotage extends RegistroRonda {
    private final List<Jugador> empatados;

    public RegistroBallotage(int numeroRonda, List<Jugador> empatados) {
        super(numeroRonda);
        this.empatados = empatados;
    }

    @Override
    public String describir() {
        return "Empate en la ronda " + numeroRonda + ". Se inicia el ballotage entre: " +
                empatados.stream().map(Jugador::nombre).toList();
    }
}
