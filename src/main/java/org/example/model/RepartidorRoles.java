package org.example.model;

import java.util.List;

public class RepartidorRoles {

    private final MezcladorDeRoles mezclador;

    public RepartidorRoles(MezcladorDeRoles mezclador) {
        this.mezclador = mezclador;
    }

    public Jugadores repartir(List<String> nombres, List<Rol> roles) {
        if (nombres.size() != roles.size()) {
            throw new RepartoRolesInvalidoException("Debe haber exactamente un rol por jugador");
        }
        mezclador.mezclar(roles);
        Jugadores jugadores = new Jugadores();
        for (int i = 0; i < nombres.size(); i++) {
            jugadores.agregar(new Jugador(nombres.get(i), roles.get(i)));
        }
        return jugadores;
    }
}