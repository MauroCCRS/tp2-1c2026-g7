package org.example.model;

public class Medico extends Rol {
    private Jugador objetivoAProteger;
    private Jugador objetivoProtegido;
    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    public void elegirProteger(Jugador objetivo) {
        if (objetivoAProteger == objetivo) {
            throw new ProtegidoInvalidoException("No se puede proteger al mismo jugador");
        }
        if (!objetivo.estaVivo()) {
            throw new ProtegidoInvalidoException("El objetivo debe ser un jugador vivo");
        }
        this.objetivoProtegido = this.objetivoAProteger;
        this.objetivoAProteger = objetivo;
    }

    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        resolucion.registrarProteccion(objetivoAProteger);
    }
    
    @Override
    public String nombre() {
        return "Medico";
    }
}