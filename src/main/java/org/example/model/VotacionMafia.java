package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VotacionMafia {

    private final CriterioConsenso criterioConsenso;
    private final List<VotoMafia> votos = new ArrayList<>();

    public VotacionMafia(CriterioConsenso criterioConsenso) {
        this.criterioConsenso = criterioConsenso;
    }

    public void votar(Jugador votante, Jugador objetivo) {
        if (!objetivo.estaVivo()) {
            throw new VotacionInvalidaException("La victima debe estar viva");
        }
        if (objetivo.esMafioso()) {
            throw new VotacionInvalidaException("La victima no puede ser mafioso");
        }
        VotoMafia voto = votante.crearVotoMafia(objetivo);
        this.votos.add(voto);
    }

    public Optional<Jugador> victimaElegida() {
        if (votos.isEmpty()) {
            return Optional.empty();
        }

        Optional<VotoMafia> votoPrioritario = votos.stream()
                .filter(VotoMafia::esPrioritario)
                .findFirst();

        if (votoPrioritario.isPresent()) {
            return Optional.of(votoPrioritario.get().objetivo());
        }
        return criterioConsenso.evaluarConsenso(votos);
    }
}