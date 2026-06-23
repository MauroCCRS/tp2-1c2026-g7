package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SinEliminacion implements CriterioEmpate {
    @Override
    public Optional<VotacionDiurna> resolverEmpate(List<Jugador> empatados) {
        return Optional.empty();
    }
}