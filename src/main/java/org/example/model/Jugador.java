package org.example.model;

public class Jugador {

    private final String nombre;
    private final Rol rol;
    private Estado estado;

    public Jugador(String nombre, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.estado = new Vivo();
    }

    public String nombre() {
        return nombre;
    }

    public boolean estaVivo() {
        return estado.estaVivo();
    }

    public void eliminar() {
        this.estado = new Eliminado();
    }

    public Bando bando() {
        return rol.bando();
    }

    public Bando resultadoAlSerInvestigado() {
        return rol.resultadoAlSerInvestigado();
    }

    public void actuarEnNoche(ResolucionNocturna resolucion) {
        estado.actuarEnNoche(this, resolucion);
    }

    void ejecutarAccionNocturna(ResolucionNocturna resolucion) {
        rol.actuarEnNoche(resolucion);
    }

    public Rol rolVistoPor(Jugador jugadorQuePregunta) {
        if (this == jugadorQuePregunta) {
            return rol;
        }
        return new RolOculto();
    }
}