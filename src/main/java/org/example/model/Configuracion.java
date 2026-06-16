package org.example.model;

import java.util.Collections;
import java.util.List;

public class Configuracion {

    private final MezcladorDeRoles mezclador;
    private final List<Tramo> tramos;

    public Configuracion(MezcladorDeRoles mezclador) {
        this.mezclador = mezclador;
        this.tramos = construirTramos();
    }

    public List<Rol> armarRoles(int cantidadJugadores) {
        return tramoPara(cantidadJugadores).armarRoles(cantidadJugadores, mezclador);
    }

    private Tramo tramoPara(int cantidadJugadores) {
        return tramos.stream()
                .filter(tramo -> tramo.contiene(cantidadJugadores))
                .findFirst()
                .orElseThrow(() -> new CantidadDeJugadoresInvalidaException(cantidadJugadores));
    }

    private List<Tramo> construirTramos() {
        Tramo chico = new Tramo(5, 6, 1,
                Collections.emptyList(),
                List.of(Detective::new, Medico::new), 1);
        Tramo mediano = new Tramo(7, 9, 2,
                List.of(Detective::new, Medico::new),
                Collections.emptyList(), 0);
        Tramo grande = new Tramo(10, 12, 2,
                List.of(Detective::new, Medico::new, Padrino::new, Sheriff::new),
                Collections.emptyList(), 0);
        return List.of(chico, mediano, grande);
    }
}