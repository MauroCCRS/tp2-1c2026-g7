package org.example.model;
public abstract class Rol {
    public abstract Bando bando();
    public abstract String nombre();
    public Bando resultadoAlSerInvestigado() {
        return bando();
    }
    public void actuarEnNoche(ResolucionNocturna resolucion) { }
}