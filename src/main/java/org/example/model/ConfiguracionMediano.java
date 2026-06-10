package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfiguracionMediano extends Configuracion {
    private final Random random = new Random();

    @Override
    public List<Rol> armarRoles() {
        int cantidadJugadores = random.nextInt(3) + 7;
        List<Rol> roles = new ArrayList<>();
        roles.add(new Detective());
        roles.add(new Medico());
        agregarMafiosos(roles, cantidadJugadores == 8 ? 2 : 3);
        completarConCiudadanos(roles, cantidadJugadores);
        return roles;
    }
}