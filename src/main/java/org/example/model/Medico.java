package org.example.model;

public class Medico extends Rol implements PuedeProteger, AccionNocturna {

    private Jugador objetivoAProteger;
    private Jugador ultimoProtegido;

    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    @Override
    public void elegirProteger(Jugador objetivo) {
        if (ultimoProtegido == objetivo) {
            throw new ProteccionInvalidaException("No se puede proteger al mismo jugador");
        }
        if (!objetivo.estaVivo()) {
            throw new ProteccionInvalidaException("El objetivo debe ser un jugador vivo");
        }
        this.objetivoAProteger = objetivo;
    }

    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        if (objetivoAProteger != null) {
            resolucion.registrarProteccion(objetivoAProteger);
            this.ultimoProtegido = objetivoAProteger;
        }
        this.objetivoAProteger = null;
    }

    @Override
    public String nombre() {
        return "Medico";
    }

    @Override
    public void ejecutar(AccionDeRol accion) {
        accion.ejecutarPara(this);
    }

}
