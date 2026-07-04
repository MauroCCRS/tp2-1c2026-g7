package org.example.model;

import java.util.Optional;

public class AnunciadorGanador {
    public Optional<String> anunciar(Optional<Bando> resultado) {
        return resultado.map(bando -> "Ganador: " + bando.nombre());
    }
}
