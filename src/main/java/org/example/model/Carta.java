package org.example.model;

public class Carta {
    private final Rol rol;
    private EstadoCarta estado;

    public Carta(Rol rol) {
        this.rol = rol;
        this.estado = new Oculta();
    }

    public void revelar() {
        this.estado = new Revelada();
    }

    public String descripcion() {
        return estado.describir(rol);
    }
}