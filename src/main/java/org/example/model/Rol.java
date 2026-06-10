package org.example.model;

public abstract class Rol {
    public abstract Bando bando();

    public Bando resultadoAlSerInvestigado() {
        return bando();
    }

    public void actuarEnNoche(ResolucionNocturna resolucion) { }

    public boolean esVisible() {
        return true;
    }
}