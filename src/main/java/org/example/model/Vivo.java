package org.example.model;

import java.util.function.Consumer;

public class Vivo extends Estado {
    @Override
    public boolean estaVivo() {
        return true;
    }

    @Override
    public void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion) {
        jugador.ejecutarAccionNocturna(resolucion);
    }

    @Override
    public void siEstaVivo(Jugador jugador, Consumer<Jugador> accion) {
        accion.accept(jugador);
    }
}
