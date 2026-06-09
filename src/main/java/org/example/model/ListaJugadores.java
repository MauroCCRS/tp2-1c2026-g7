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

    public List<Jugador> obtenerMafiosos(){
        List<Jugador> mafiosos = new ArrayList<>();

        for (Jugador jugador : this.jugadores) {
            Rol rol = jugador.devolverRol(jugador);

            if (rol.bando() instanceof BandoMafia) {
                mafiosos.add(jugador);
            }
        }

        return mafiosos;
    }

    public List<Jugador> obtenerCiudadanos(){
        List<Jugador> ciudadanos = new ArrayList<>();

        for (Jugador jugador : this.jugadores) {
            Rol rol = jugador.devolverRol(jugador);

            if (rol.bando() instanceof BandoCiudadano) {
                ciudadanos.add(jugador);
            }
        }

        return ciudadanos;
    }
}