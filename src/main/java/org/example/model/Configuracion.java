package org.example.model;

import java.util.List;

public abstract class Configuracion {

    public abstract List<Rol> armarRoles();

    protected void agregarMafiosos(List<Rol> roles, int cantidad) {
        for (int i = 0; i < cantidad; i++) {
            roles.add(new Mafioso());
        }
    }

    protected void completarConCiudadanos(List<Rol> roles, int cantidadTotal) {
        while (roles.size() < cantidadTotal) {
            roles.add(new Ciudadano());
        }
    }
}