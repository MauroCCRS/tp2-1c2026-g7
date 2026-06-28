package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SinVictima implements CriterioConsenso {
    @Override
    public Optional<Jugador> evaluarConsenso(List<VotoMafia> votos) {
        return Optional.empty();
    }
}
