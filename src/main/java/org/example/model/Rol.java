package org.example.model;

public abstract class Rol {
    public abstract Bando bando();

    public abstract String nombre();

    public String rutaImagen() {
        return "/" + nombre().toLowerCase() + ".png";
    }

    public Bando resultadoAlSerInvestigado() {
        return bando();
    }

    public void ejecutar(AccionDeRol accion) {
        accion.ejecutarPara(this);
    }
}
