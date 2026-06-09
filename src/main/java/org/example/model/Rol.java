package org.example.model;

public class Rol {
    private Bando bando;

    public void ingresarBando(Bando bando){
        this.bando = bando;
    }
    public Bando devolverBando(){
        return bando;
    }


}
