package org.example.model;

public class Detective extends Rol {
    private Jugador objetivoAInvestigar;
    private Bando resultadoInvestigacion;

    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    public void elegirInvestigar(Jugador objetivo) {
        this.objetivoAInvestigar = objetivo;
    }

    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        this.resultadoInvestigacion = objetivoAInvestigar.resultadoAlSerInvestigado();
    }

    public Bando resultadoInvestigacion() {
        return resultadoInvestigacion;
    }
}