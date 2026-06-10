package org.example.model;

public class Medico extends Rol {
    private Jugador objetivoAProteger;

    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    public void elegirProteger(Jugador objetivo) {
        this.objetivoAProteger = objetivo;
    }

    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        resolucion.registrarProteccion(objetivoAProteger);
    }
}