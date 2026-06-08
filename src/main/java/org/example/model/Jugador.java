package org.example.model;

public class Jugador {


    private String nombre;
    private Rol rol;
    private Estado estado;


    public Estado devolverEstado(){
        return estado;
    }

    public void eliminar(){}

    public Rol devolverRol(){
        return rol;
    }

    public void cambiarEstado(Estado estado){
        this.estado = estado;
    }

    public void cambiarRol(Rol rol){
        this.rol = rol;
    }
}
