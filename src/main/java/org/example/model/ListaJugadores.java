package org.example.model;
import java.util.ArrayList;
import java.util.List;


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
}
