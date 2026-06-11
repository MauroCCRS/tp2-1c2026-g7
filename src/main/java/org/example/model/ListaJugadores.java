package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class ListaJugadores {
    private final List<Jugador> jugadores = new ArrayList<>();

    public void agregar(Jugador jugador) {
        this.jugadores.add(jugador);
    }

    public void eliminar(Jugador jugador) {
        this.jugadores.remove(jugador);
    }

    public List<Jugador> obtenerListaCompleta() {
        return new ArrayList<>(this.jugadores);
    }

    public List<Jugador> obtenerVivos() {
        List<Jugador> vivos = new ArrayList<>();
        for (Jugador jugador : this.jugadores) {
            if (jugador.estaVivo()) {
                vivos.add(jugador);
            }
        }
        return vivos;
    }

    public List<Jugador> delBando(Bando bando) {
        List<Jugador> delBando = new ArrayList<>();
        for (Jugador jugador : this.jugadores) {
            if (jugador.bando().esMismoBando(bando)) {
                delBando.add(jugador);
            }
        }
        return delBando;
    }

    public List<Jugador> vivosDelBando(Bando bando) {
        List<Jugador> resultado = new ArrayList<>();
        for (Jugador jugador : delBando(bando)) {
            if (jugador.estaVivo()) {
                resultado.add(jugador);
            }
        }
        return resultado;
    }

    public List<Jugador> obtenerMafiosos() {
        return delBando(new BandoMafia());
    }

    public List<Jugador> obtenerCiudadanos() {
        return delBando(new BandoCiudadano());
    }
}