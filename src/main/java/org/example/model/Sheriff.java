package org.example.model;
public class Sheriff extends Investigador {

    @Override
    public String nombre() {
        return "Sheriff";
    }

    @Override
    public void revelarse() {
    }

    public Jugador revelarJugadorInvestigado(){
        return this.ultimoInvestigado;
    }


}