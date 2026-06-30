package org.example.model;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
public class Mayoria implements CriterioConsenso {
    @Override
    public Optional<Jugador> evaluarConsenso(List<VotoMafia> votos) {
        if (votos.isEmpty()) return Optional.empty();

        Map<Jugador, Long> conteo = votos.stream()
                .map(VotoMafia::objetivo)
                .collect(Collectors.groupingBy(objetivo -> objetivo, Collectors.counting()));

        long maximo = Collections.max(conteo.values());

        List<Jugador> masVotados = conteo.entrySet().stream()
                .filter(entrada -> entrada.getValue() == maximo)
                .map(Map.Entry::getKey)
                .toList();

        if (masVotados.size() == 1) {
            return Optional.of(masVotados.getFirst());
        }

        return Optional.empty();
    }
}
