package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ResolucionNocturna {

    private Jugador atacado;
    private final List<Jugador> protegidos = new ArrayList<>();

    public void registrarAtaque(Jugador objetivo) {
        this.atacado = objetivo;
    }

    public void registrarProteccion(Jugador objetivo) {
        this.protegidos.add(objetivo);
    }

    public void resolver() {
        if (atacado == null) {
            return;
        }
        if (protegidos.contains(atacado)) {
            return;
        }
        atacado.eliminar();
    }
}