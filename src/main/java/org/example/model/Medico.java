package org.example.model;

public class Medico extends Rol{
    private Jugador ultimaProteccion;
    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    public void proteger(Jugador objetivo) {

    };
    public void actuarEnNoche(Jugador jugador, ResolucionNocturna resolucion){};

    public Jugador obtenerUltimaProteccion() {
        return ultimaProteccion;
    }
}
