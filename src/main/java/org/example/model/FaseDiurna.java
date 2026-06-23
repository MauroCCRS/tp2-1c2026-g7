package org.example.model;

import java.util.List;
import java.util.Optional;

public class FaseDiurna extends Fase {

    private VotacionDiurna votacion;
    public FaseDiurna(int numeroRonda, CriterioEmpate criterio) {
        super(numeroRonda);
        this.votacion = new VotacionDiurna(criterio);
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
        List<Jugador> empatados = votacion.ganadoresPorMayoria();

        Optional<Jugador> eliminado = votacion.resolver();

        if (eliminado.isPresent()) {
            eliminado.get().eliminar();
            return new RegistroDiurno(numeroRonda, eliminado.get());
        }

        if (empatados.size() > 1) {
            Optional<VotacionDiurna> nuevaVotacion = votacion.generarBallotage();
            if (nuevaVotacion.isPresent()) {
                this.votacion = nuevaVotacion.get();
                return new RegistroBallotage(numeroRonda, votacion.obtenerNominados());
            }
        }
        return new RegistroSinEliminacionDiurna(numeroRonda);
    }

    void revelarSheriff(Jugador sheriff) {
        sheriff.revelarseComoSheriff();
    }

    @Override
    public Fase siguiente(Partida partida) {
        return partida.crearFaseNocturna(numeroRonda + 1);
    }
}