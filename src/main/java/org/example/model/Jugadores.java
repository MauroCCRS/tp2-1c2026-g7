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

    public List<Jugador> complicesMafiososDe(Jugador jugador) {
        if (!jugador.estaVivo() || !jugador.esMafioso()) {
            throw new JugadoresException("El jugador debe ser vivo y mafioso");
        }

        return jugadores.stream()
                .filter(Jugador::estaVivo)
                .filter(Jugador::esMafioso)
                .filter(complice -> complice != jugador)
                .toList();
    }

    public void porCadaVivo(Consumer<Jugador> accion) {
        jugadores.stream()
                .filter(Jugador::estaVivo)
                .forEach(accion);
    }
}