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

    public List<Jugador> vivos() {
        return jugadores.stream()
                .filter(Jugador::estaVivo)
                .toList();
    }

    public List<Jugador> mafiososVivos() {
        return vivos().stream()
                .filter(Jugador::esMafioso)
                .toList();
    }

    public List<Jugador> vivosNoMafiosos() {
        return vivos().stream()
                .filter(jugador -> !jugador.esMafioso())
                .toList();
    }

    public List<Jugador> investigadoresVivos() {
        List<Jugador> candidatos = new ArrayList<>();
        jugadores.forEach(jugador -> jugador.agregarSiPuedeInvestigar(candidatos));
        return candidatos;
    }

    public List<Jugador> protectoresVivos() {
        List<Jugador> candidatos = new ArrayList<>();
        jugadores.forEach(jugador -> jugador.agregarSiPuedeProteger(candidatos));
        return candidatos;
    }

    public List<Jugador> revelablesVivos() {
        List<Jugador> candidatos = new ArrayList<>();
        jugadores.forEach(jugador -> jugador.agregarSiPuedeRevelarse(candidatos));
        return candidatos;
    }

    public void porCadaVivo(Consumer<Jugador> accion) {
        jugadores.stream()
                .filter(Jugador::estaVivo)
                .forEach(accion);
    }

    //copia de lista de jugadores para usarlo en vista reparto de roles
    public List<Jugador> todos() {
        return new ArrayList<>(jugadores);
    }
}
