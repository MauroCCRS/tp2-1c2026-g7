package org.example.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VotacionMafia {

    private final CriterioConsenso criterioConsenso;
    private final Map<Jugador, VotoMafia> votos = new LinkedHashMap<>();

    public VotacionMafia(CriterioConsenso criterioConsenso) {
        this.criterioConsenso = criterioConsenso;
    }

    public void votar(Jugador votante, Jugador objetivo) {
        if (!votante.estaVivo()) {
            throw new VotacionInvalidaException("El votante debe estar vivo");
        }
        if (votos.containsKey(votante)) {
            throw new VotacionInvalidaException("Este jugador ya voto en esta fase");
        }
        if (!objetivo.estaVivo()) {
            throw new VotacionInvalidaException("La victima debe estar viva");
        }
        if (objetivo.esMafioso()) {
            throw new VotacionInvalidaException("La victima no puede ser mafioso");
        }
        VotoMafia voto = votante.crearVotoMafia(objetivo);
        this.votos.put(votante, voto);
    }

    public Optional<Jugador> victimaElegida() {
        if (votos.isEmpty()) {
            return Optional.empty();
        }

        Optional<VotoMafia> votoPrioritario = votos.values().stream()
                .filter(VotoMafia::esPrioritario)
                .findFirst();

        if (votoPrioritario.isPresent()) {
            return Optional.of(votoPrioritario.get().objetivo());
        }
        return criterioConsenso.evaluarConsenso(new ArrayList<>(votos.values()));
    }

    public Map<Jugador, Jugador> votosRegistrados() {
        Map<Jugador, Jugador> registrados = new LinkedHashMap<>();
        votos.forEach((votante, voto) -> registrados.put(votante, voto.objetivo()));
        return registrados;
    }

    public Map<Jugador, Long> conteoPorObjetivo() {
        Map<Jugador, Long> conteo = new LinkedHashMap<>();
        votos.values().forEach(voto -> conteo.put(voto.objetivo(), conteo.getOrDefault(voto.objetivo(), 0L) + 1));
        return conteo;
    }
}
