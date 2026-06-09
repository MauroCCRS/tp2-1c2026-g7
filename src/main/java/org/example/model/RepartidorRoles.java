package org.example.model;

import java.util.List;

public class RepartidorRoles {

    private MezcladorDeRoles mezclador;

    public RepartidorRoles(MezcladorDeRoles mezclador) {
        this.mezclador = mezclador;
    }

    public void repartir(ListaJugadores jugadores, List<Rol> roles) {
        List<Jugador> listaJugadores = jugadores.obtenerListaCompleta();

        if (listaJugadores.size() != roles.size()) {
            throw new IllegalArgumentException("Debe haber exactamente un rol por jugador");
        }

        mezclador.mezclar(roles);

        for (int i = 0; i < listaJugadores.size(); i++) {
            listaJugadores.get(i).cambiarRol(roles.get(i));
        }
    }
}
