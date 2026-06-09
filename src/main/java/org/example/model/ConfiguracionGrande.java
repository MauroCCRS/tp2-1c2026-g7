package org.example.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ConfiguracionGrande extends Configuracion{
    private final Random random = new Random();
    @Override
    public List<Rol> armarRoles() {
        int cantidadJugadores = random.nextInt(3) + 10; // 10 o 11 o 12
        List<Rol> rolesArmados = new ArrayList<>();
        agregarRolesEspeciales(rolesArmados);
        agregarMafiosos(rolesArmados, 2);
        agregarCiudadanos(rolesArmados, cantidadJugadores);

        Collections.shuffle(rolesArmados);

        return rolesArmados;
    };

    private void agregarRolesEspeciales(List<Rol> roles) {
        roles.add(new Detective());
        roles.add(new Medico());
        roles.add(new Padrino());
        roles.add(new Sheriff());

    }

    private void agregarMafiosos(List<Rol> roles, int cantidadMafiosos) {
        for (int i = 0; i < cantidadMafiosos; i++) {
            roles.add(new Mafioso());
        }
    }

    private void agregarCiudadanos(List<Rol> roles, int cantidadJugadores) {
        while (roles.size() < cantidadJugadores) {
            roles.add(new Ciudadano());
        }
    }
}
