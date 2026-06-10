package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfiguracionChico extends Configuracion {
    private final Random random = new Random();

    @Override
    public List<Rol> armarRoles() {
        int cantidadJugadores = random.nextInt(2) + 5;
        List<Rol> roles = new ArrayList<>();
        agregarRolEspecial(roles);
        agregarMafiosos(roles, cantidadJugadores == 5 ? 1 : 2);
        completarConCiudadanos(roles, cantidadJugadores);
        return roles;
    }

    private void agregarRolEspecial(List<Rol> roles) {
        roles.add(random.nextBoolean() ? new Detective() : new Medico());
    }
}