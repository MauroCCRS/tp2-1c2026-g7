package org.example.model;

public abstract class Rol {
    private Bando bando;

    public Bando devolverBando() {
        return bando;
    };

    public void ingresarBando(Bando bando) {
        this.bando = bando;
    };
}
