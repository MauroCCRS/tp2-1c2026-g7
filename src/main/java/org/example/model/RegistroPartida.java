package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RegistroPartida {
    private final List<RegistroRonda> registros = new ArrayList<>();

    public void agregarRegistro(RegistroRonda registro) {
        this.registros.add(registro);
    }

    public String generarResumen() {
        return registros.stream()
                .map(RegistroRonda::describir)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}