package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Tramo {

    private final int minimo;
    private final int maximo;
    private final int cantidadMafiosos;
    private final List<Supplier<Rol>> especialesFijos;
    private final List<Supplier<Rol>> especialesCandidatos;
    private final int cantidadEspecialesAElegir;

    public Tramo(int minimo, int maximo, int cantidadMafiosos,
                 List<Supplier<Rol>> especialesFijos,
                 List<Supplier<Rol>> especialesCandidatos,
                 int cantidadEspecialesAElegir) {
        this.minimo = minimo;
        this.maximo = maximo;
        this.cantidadMafiosos = cantidadMafiosos;
        this.especialesFijos = especialesFijos;
        this.especialesCandidatos = especialesCandidatos;
        this.cantidadEspecialesAElegir = cantidadEspecialesAElegir;
    }

    public boolean contiene(int cantidadJugadores) {
        return cantidadJugadores >= minimo && cantidadJugadores <= maximo;
    }

    public List<Rol> armarRoles(int cantidadJugadores, MezcladorDeRoles mezclador) {
        List<Rol> roles = new ArrayList<>();
        agregarMafiosos(roles);
        agregarEspecialesFijos(roles);
        agregarEspecialesElegidos(roles, mezclador);
        completarConCiudadanos(roles, cantidadJugadores);
        return roles;
    }

    private void agregarMafiosos(List<Rol> roles) {
        for (int i = 0; i < cantidadMafiosos; i++) {
            roles.add(new Mafioso());
        }
    }

    private void agregarEspecialesFijos(List<Rol> roles) {
        especialesFijos.forEach(creador -> roles.add(creador.get()));
    }

    private void agregarEspecialesElegidos(List<Rol> roles, MezcladorDeRoles mezclador) {
        List<Rol> candidatos = new ArrayList<>();
        especialesCandidatos.forEach(creador -> candidatos.add(creador.get()));
        mezclador.mezclar(candidatos);
        candidatos.stream().limit(cantidadEspecialesAElegir).forEach(roles::add);
    }

    private void completarConCiudadanos(List<Rol> roles, int cantidadJugadores) {
        while (roles.size() < cantidadJugadores) {
            roles.add(new Ciudadano());
        }
    }
}