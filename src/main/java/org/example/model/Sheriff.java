package org.example.model;
public class Sheriff extends Detective {


    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

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