package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CriterioConsenso {
    Optional<Jugador> evaluarConsenso(List<VotoMafia> votos);
}
