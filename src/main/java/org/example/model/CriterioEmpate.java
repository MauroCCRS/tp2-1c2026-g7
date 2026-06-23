package org.example.model;

import java.util.List;
import java.util.Optional;

@FunctionalInterface
public interface CriterioEmpate {
    List<Jugador> desempatar(List<Jugador> empatados);

    default Optional<VotacionDiurna> generarBallotage(List<Jugador> empatados) {
        return Optional.empty();
    };
}