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

    public void elegirProteger(Jugador objetivo) {
        throw new ProteccionInvalidaException("Este rol no puede proteger");
    }
}