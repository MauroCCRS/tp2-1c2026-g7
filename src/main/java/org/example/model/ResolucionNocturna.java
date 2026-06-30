package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResolucionNocturna {

    private Jugador atacado;
    private final List<Jugador> protegidos = new ArrayList<>();

    public void registrarAtaque(Jugador objetivo) {
        this.atacado = objetivo;
    }

    public void registrarProteccion(Jugador objetivo) {
        this.protegidos.add(objetivo);
    }

    public Optional<Jugador> resolver() {
        if (atacado == null) {
            return Optional.empty();
        }
        if (protegidos.contains(atacado)) {
            return Optional.empty();
        }
        return Optional.of(atacado);
    }

}