package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Jugadores {
    private final List<Jugador> jugadores = new ArrayList<>();

    public void agregar(Jugador jugador) {
        this.jugadores.add(jugador);
    }

    public int cantidadDeVivos() {
        return (int) jugadores.stream()
                .filter(Jugador::estaVivo)
                .count();
    }

    public int cantidadVivosDelBando(Bando bando) {
        return (int) jugadores.stream()
                .filter(Jugador::estaVivo)
                .filter(jugador -> jugador.perteneceA(bando))
                .count();
    }

    public void porCadaVivo(Consumer<Jugador> accion) {
        jugadores.stream()
                .filter(Jugador::estaVivo)
                .forEach(accion);
    }
}