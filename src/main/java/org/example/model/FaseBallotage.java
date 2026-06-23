package org.example.model;

import java.util.List;

public class FaseBallotage extends Fase {

    private final VotacionDiurna votacion = new VotacionDiurna();
    private final ResolucionVotacion resolucion = new ResolucionVotacion(votacion);

    public FaseBallotage(int numeroRonda, List<Jugador> empatados) {
        super(numeroRonda);
        empatados.forEach(votacion::nominar);
    }

    @Override
    void votar(Jugador votante, Jugador objetivo) {
        this.votacion.votar(votante, objetivo);
    }

    @Override
    public RegistroRonda resolver() {
        return resolucion.resolverEn(numeroRonda);
    }

    @Override
    public Fase siguiente(Partida partida) {
        return partida.crearFaseNocturna(numeroRonda + 1);
    }
}