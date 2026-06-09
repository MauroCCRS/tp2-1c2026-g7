package org.example.model;

public class VotacionMafia {

    private final ListaJugadores jugadores;
    private Jugador victimaElegida;

    public VotacionMafia(ListaJugadores jugadores) {
        this.jugadores = jugadores;
    }

    public void votar(Jugador objetivo) {
        if (!jugadores.obtenerVivos().contains(objetivo)) {
            throw new VictimaInvalidaException("La victima debe ser un jugador vivo");
        }
        if (jugadores.obtenerMafiosos().contains(objetivo)) {
            throw new VictimaInvalidaException("La victima no puede ser un mafioso");
        }
        this.victimaElegida = objetivo;
    }

    public Jugador victimaElegida() {
        return victimaElegida;
    }
}