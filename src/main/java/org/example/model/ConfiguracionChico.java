package org.example.model;


import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class ConfiguracionChico extends Configuracion{
    private final Random random = new Random();
    @Override
    public List<Rol> armarRoles() {
        int cantidadJugadores = random.nextInt(2) + 5; // 5 o 6
        List<Rol> rolesArmados = new ArrayList<>();
        agregarRolEspecial(rolesArmados);
        agregarMafiosos(rolesArmados, cantidadJugadores);
        agregarCiudadanos(rolesArmados, cantidadJugadores);

        Collections.shuffle(rolesArmados);

        return rolesArmados;
    };

    private void agregarRolEspecial(List<Rol> roles) {
        if (random.nextBoolean()) {
            roles.add(new Detective());
        } else {
            roles.add(new Medico());
        }
    }

    private void agregarMafiosos(List<Rol> roles, int cantidadJugadores) {
        int cantidadMafiosos = calcularCantidadMafiosos(cantidadJugadores);

        for (int i = 0; i < cantidadMafiosos; i++) {
            roles.add(new Mafioso());
        }
    }

    private int calcularCantidadMafiosos(int cantidadJugadores) {
        return cantidadJugadores == 5 ? 1 : 2;
    }

    private void agregarCiudadanos(List<Rol> roles, int cantidadJugadores) {
        while (roles.size() < cantidadJugadores) {
            roles.add(new Ciudadano());
        }
    }
}
