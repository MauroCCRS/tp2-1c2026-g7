package org.example.model;

import java.util.function.Consumer;

public abstract class Estado {
    public abstract boolean estaVivo();
    public abstract void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion);
    public abstract void siEstaVivo(Jugador jugador, Consumer<Jugador> accion);
}
