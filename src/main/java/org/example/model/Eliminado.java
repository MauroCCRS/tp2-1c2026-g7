package org.example.model;

public class Eliminado extends Estado {
    @Override
    public boolean estaVivo() {
        return false;
    }

    @Override
    public void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion) { }
}