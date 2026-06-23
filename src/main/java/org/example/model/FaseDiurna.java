package org.example.model;

import java.util.Optional;

public class FaseDiurna extends Fase {

    private final VotacionDiurna votacion;
    private Optional<VotacionDiurna> ballotage = Optional.empty();

    public FaseDiurna(int numeroRonda, CriterioEmpate criterio) {
        this(numeroRonda, new VotacionDiurna(criterio));
    }

    public FaseDiurna(int numeroRonda, VotacionDiurna votacion) {
        super(numeroRonda);
        this.votacion = votacion;
    }

    @Override
    void nominar(Jugador jugador) {
        this.votacion.nominar(jugador);
    }

    @Override
    void votar(Jugador votante, Jugador objetivo) {
        this.votacion.votar(votante, objetivo);
    }

    @Override
    public RegistroRonda resolver() {
        Optional<Jugador> eliminado = votacion.resolver();
        if (eliminado.isPresent()) {
            eliminado.get().eliminar();
            return new RegistroDiurno(numeroRonda, eliminado.get());
        }
        this.ballotage = votacion.generarBallotage();
        return ballotage
                .<RegistroRonda>map(nueva -> new RegistroBallotage(numeroRonda, nueva.obtenerNominados()))
                .orElseGet(() -> new RegistroSinEliminacionDiurna(numeroRonda));
    }

    @Override
    void revelarSheriff(Jugador sheriff) {
        sheriff.revelarseComoSheriff();
    }

    @Override
    public Fase siguiente(Partida partida) {
        return ballotage
                .<Fase>map(nueva -> new FaseDiurna(numeroRonda, nueva))
                .orElseGet(() -> partida.crearFaseNocturna(numeroRonda + 1));
    }
}