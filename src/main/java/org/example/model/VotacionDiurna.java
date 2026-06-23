package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class VotacionDiurna {
    private final Set<Jugador> nominados = new LinkedHashSet<>();
    private final Map<Jugador, Jugador> votos = new HashMap<>();

    public void nominar(Jugador jugador) {
        if (!jugador.estaVivo()) {
            throw new NominacionInvalidaException("El nominado tiene que estar vivo.");
        }
        this.nominados.add(jugador);
    }

    public void votar(Jugador votante, Jugador objetivo) {
        if (!votante.estaVivo()) {
            throw new VotacionInvalidaException("El votante debe ser un jugador vivo");
        }
        if (!nominados.contains(objetivo)) {
            throw new VotacionInvalidaException("El objetivo debe ser nominado.");
        }
        this.votos.put(votante, objetivo);
    }

    public List<Jugador> ganadoresPorMayoria() {
        Map<Jugador, Long> conteo = votos.values().stream()
                .collect(Collectors.groupingBy(objetivo -> objetivo, Collectors.counting()));

        if (conteo.isEmpty()) {
            return new ArrayList<>();
        }

        long maximo = Collections.max(conteo.values());

        return conteo.entrySet().stream()
                .filter(entrada -> entrada.getValue() == maximo)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Optional<Jugador> ganadorUnico() {
        List<Jugador> ganadores = ganadoresPorMayoria();
        return ganadores.size() == 1 ? Optional.of(ganadores.get(0)) : Optional.empty();
    }

    public boolean hayEmpate() {
        return ganadoresPorMayoria().size() > 1;
    }

    public List<Jugador> empatados() {
        return ganadoresPorMayoria();
    }
}