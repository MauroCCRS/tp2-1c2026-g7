package org.example.model;

public class Detective extends Rol {
    private Jugador objetivoAInvestigar;
    private Jugador ultimoInvestigado;
    private Bando resultadoInvestigacion;

    @Override
    public Bando bando() {
        return new BandoCiudadano();
    }

    @Override
    public void elegirInvestigar(Jugador objetivo) {
        if (objetivo == ultimoInvestigado) {
            throw new InvestigacionInvalidaException("No puede investigar al mismo jugador dos noches seguidas");
        }
        if (!objetivo.estaVivo()) {
            throw new InvestigacionInvalidaException("No se puede investigar a un jugador eliminado");
        }
        this.objetivoAInvestigar = objetivo;
    }

    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        this.resultadoInvestigacion = objetivoAInvestigar.resultadoAlSerInvestigado();
        this.ultimoInvestigado = objetivoAInvestigar;
    }

    public Bando resultadoInvestigacion() {
        return resultadoInvestigacion;
    }

    @Override
    public String nombre() {
        return "Detective";
    }
}