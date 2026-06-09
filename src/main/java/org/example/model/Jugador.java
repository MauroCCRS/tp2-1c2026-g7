package org.example.model;

public class Jugador {

    private String nombre;
    private Estado estado = new Vivo();
    private Rol rol = new SinRol();

    public Estado devolverEstado(){
        return estado;
    }

    public void eliminar(){
        this.estado = new Eliminado();
    }

    public Rol devolverRol(Jugador jugadorQuePregunta){
        if (this == jugadorQuePregunta) {
            return rol;
        }
        return new RolOculto();
    }

    public void cambiarEstado(Estado estado){
        this.estado = estado;
    }

    public void cambiarRol(Rol rol){
        this.rol = rol;
    }

    public boolean tieneRolAsignado() {return rol.esRolAsignado(); }
}