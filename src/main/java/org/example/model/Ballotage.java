package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Ballotage implements CriterioEmpate {
    @Override
    public List<Jugador> desempatar(List<Jugador> empatados) {
        return new ArrayList<>();
    }
    @Override
    public Optional<VotacionDiurna> generarBallotage(List<Jugador> empatados) {
        return Optional.of(new VotacionDiurna(new SinEliminacion(), empatados));
    }
}
