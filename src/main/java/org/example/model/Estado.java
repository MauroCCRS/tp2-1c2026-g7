package org.example.model;

public abstract class Estado {
    public abstract boolean estaVivo();
    public abstract void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion);
}