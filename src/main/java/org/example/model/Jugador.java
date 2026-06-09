package org.example.model;

public class Jugador {


    private String nombre;
    //private Rol rol;
    private Estado estado;
    private Rol rol = new SinRol();

    public Estado devolverEstado(){
        return estado;
    }

    public void eliminar(){}

    public Rol devolverRol(Jugador jugadorQuePregunta){
        if (this == jugadorQuePregunta) {
            return rol;
        }
        return new RolOculto(); //podriamos ponerlo asi ?
    }

    public void cambiarEstado(Estado estado){
        this.estado = estado;
    }

    public void cambiarRol(Rol rol){
        this.rol = rol;
    }

    public boolean tieneRolAsignado() {return rol.esRolAsignado(); }
}
