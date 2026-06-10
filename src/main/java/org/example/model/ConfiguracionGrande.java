package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfiguracionGrande extends Configuracion {
    private final Random random = new Random();

    @Override
    public List<Rol> armarRoles() {
        int cantidadJugadores = random.nextInt(3) + 10;
        List<Rol> roles = new ArrayList<>();
        roles.add(new Detective());
        roles.add(new Medico());
        roles.add(new Padrino());
        roles.add(new Sheriff());
        agregarMafiosos(roles, 2);
        completarConCiudadanos(roles, cantidadJugadores);
        return roles;
    }
}