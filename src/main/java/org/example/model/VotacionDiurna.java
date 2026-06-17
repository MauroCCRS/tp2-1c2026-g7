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
    private final CriterioEmpate criterio;
    private final Set<Jugador> nominados = new LinkedHashSet<>();
    private final Map<Jugador, Jugador> votos = new HashMap<>();

    public VotacionDiurna(CriterioEmpate criterio) {
        this.criterio = criterio;
    }

    public void nominar(Jugador jugador) {
        if (jugador.estaVivo()) {
            this.nominados.add(jugador);
        }
        if (!jugador.estaVivo()) {
            throw new NominadoInvalidoException("El nominado tiene que esta vivo.");
        }
    }

    public void votar(Jugador votante, Jugador objetivo) {
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

    public Optional<Jugador> resolver() {
        List<Jugador> ganadores = ganadoresPorMayoria();
        if (ganadores.size() > 1) {
            ganadores = criterio.desempatar(ganadores);
        }
        return ganadores.stream().findFirst();
    }
}