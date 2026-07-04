package org.example.model;

import java.util.List;
import java.util.Optional;

public class EvaluadorGanador {
    private final List<Bando> bandos;

    public EvaluadorGanador(List<Bando> bandos) {
        this.bandos = bandos;
    }

    public Optional<Bando> evaluar(Jugadores jugadores) {
        return bandos.stream()
                .filter(bando -> bando.ganoSegun(jugadores))
                .findFirst();
    }
}
