package org.example.model;

import java.util.function.Consumer;

public class Eliminado extends Estado {
    @Override
    public boolean estaVivo() {
        return false;
    }

    @Override
    public void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion) { }

    @Override
    public void siEstaVivo(Jugador jugador, Consumer<Jugador> accion) { }
}
