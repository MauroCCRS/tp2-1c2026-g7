package org.example.model;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListaJugadores {
    private List<Jugador> jugadores;

    public ListaJugadores() {
        this.jugadores = new ArrayList<>();
    }
    public List<Jugador> obtenerListaCompleta() {
        return this.jugadores;
    }
    public void agregar(Jugador jugador) {
        this.jugadores.add(jugador);
    }
    public void eliminar(Jugador jugador) {
        this.jugadores.remove(jugador);
    }

    public List<Jugador> obtenerMafiosos() {
        return jugadores.stream()
                .filter(jugador ->
                        jugador.devolverRol().bando() instanceof BandoMafia)
                .collect(Collectors.toList());
    }

    public List<Jugador> obtenerCiudadanos() {
        return jugadores.stream()
                .filter(jugador ->
                        jugador.devolverRol().bando() instanceof BandoCiudadano)
                .collect(Collectors.toList());
    }
}
