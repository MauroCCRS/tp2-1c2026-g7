package org.example.model;

import java.util.List;
import java.util.Optional;

public class Ballotage implements CriterioEmpate {
    @Override
    public Optional<VotacionDiurna> resolverEmpate(List<Jugador> empatados) {
        // Retorna una nueva votación restringida a los empatados,
        // con un criterio estricto para evitar ballotages infinitos.
        return Optional.of(new VotacionDiurna(new SinEliminacion(), empatados));
    }
}
