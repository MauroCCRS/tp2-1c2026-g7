package org.example.model;

public class Medico extends Rol {
    private Jugador objetivoAProteger;
    private Jugador ultimoProtegido;

    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    public void elegirProteger(Jugador objetivo) {
        if (objetivo == ultimoProtegido) {
            throw new VictimaInvalidaException("No puede proteger al mismo jugador dos noches seguidas");
        }
        this.objetivoAProteger = objetivo;
    }

    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {

        resolucion.registrarProteccion(objetivoAProteger);
        this.ultimoProtegido = objetivoAProteger;
    }
}