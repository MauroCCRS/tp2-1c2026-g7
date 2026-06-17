package org.example.model;

public class VotacionMafia {


    private Jugador victimaElegida;



    public void votar(Jugador objetivo) {
        if (!objetivo.estaVivo()){
            throw new VotacionInvalidaException("La victima debe ser un jugador vivo");
        }
        if (objetivo.esMafioso()) {
            throw new VotacionInvalidaException("La victima no puede ser un mafioso");
        }
        this.victimaElegida = objetivo;
    }

    public Jugador victimaElegida() {
        return victimaElegida;
    }
}