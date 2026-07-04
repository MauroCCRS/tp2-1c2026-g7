package org.example.model;

public class Sheriff extends Investigador implements PuedeRevelarse {

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

    @Override
    public void ejecutar(AccionDeRol accion) {
        accion.ejecutarPara(this);
    }

}
