package org.example.model;

public class Vivo extends Estado {
    @Override
    public boolean estaVivo() {
        return true;
    }

    @Override
    public void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion) {
        jugador.ejecutarAccionNocturna(resolucion);
    }
}