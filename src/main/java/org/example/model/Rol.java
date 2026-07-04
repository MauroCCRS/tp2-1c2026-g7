package org.example.model;

public abstract class Rol {
    public abstract Bando bando();

    public abstract String nombre();

    public Bando resultadoAlSerInvestigado() {
        return bando();
    }

    public void actuarEnNoche(ResolucionNocturna resolucion) { }

    public void elegirInvestigar(Jugador objetivo) {
        throw new InvestigacionInvalidaException("Este rol no puede investigar");
    }

    public Jugador revelarJugadorInvestigado(){

        throw new RevelacionInvalidaException("Solo el Sheriff puede mostrar una investigacion");
    }

    public void revelarse() {
        throw new RevelacionInvalidaException("Solo el Sheriff puede revelarse");
    }

    public void elegirProteger(Jugador objetivo) {
        throw new ProteccionInvalidaException("Este rol no puede proteger");
    }

    public  VotoMafia crearVotoMafia(Jugador objetivo) {
        throw new VotacionInvalidaException("Solo el mafioso puede votar");
    }
    public Bando resultadoInvestigacion() {
        throw new InvestigacionInvalidaException("Este rol no puede investigar");
    }
}
