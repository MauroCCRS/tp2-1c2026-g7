package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class VotacionDiurna {
    private final CriterioEmpate criterio;
    private final Set<Jugador> nominados = new LinkedHashSet<>();
    private final Map<Jugador, Jugador> votos = new LinkedHashMap<>();

    public VotacionDiurna(CriterioEmpate criterio) {
        this.criterio = criterio;
    }

    public VotacionDiurna(CriterioEmpate criterio, List<Jugador> empatados) {
        this.criterio = criterio;
        this.nominados.addAll(empatados);
    }

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
        if (votos.containsKey(votante)) {
            throw new VotacionInvalidaException("Este jugador ya voto en esta fase");
        }
        if (!nominados.contains(objetivo)) {
            throw new VotacionInvalidaException("El objetivo debe ser nominado.");
        }
        this.votos.put(votante, objetivo);
    }

    public List<Jugador> masVotados() {
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

    public List<Jugador> obtenerNominados() {
        return new ArrayList<>(nominados);
    }

    public Map<Jugador, Jugador> votosRegistrados() {
        return new LinkedHashMap<>(votos);
    }

    public Map<Jugador, Long> conteoPorNominado() {
        Map<Jugador, Long> conteo = new LinkedHashMap<>();
        nominados.forEach(nominado -> conteo.put(nominado, 0L));
        votos.values().forEach(objetivo -> conteo.put(objetivo, conteo.getOrDefault(objetivo, 0L) + 1));
        return conteo;
    }

    public Optional<Jugador> resolver() {
        List<Jugador> masVotados = masVotados();
        if (masVotados.size() > 1) {
            masVotados = criterio.desempatar(masVotados);
        }
        return masVotados.stream().findFirst();
    }

    public Optional<VotacionDiurna> generarBallotage() {
        List<Jugador> masVotados = masVotados();
        if (masVotados.size() > 1) {
            return criterio.generarBallotage(masVotados);
        }
        return Optional.empty();
    }
}
