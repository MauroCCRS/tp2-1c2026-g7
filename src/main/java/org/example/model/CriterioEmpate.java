package org.example.model;

import java.util.List;
import java.util.Optional;

public interface CriterioEmpate {
    Optional<VotacionDiurna> resolverEmpate(List<Jugador> empatados);
}