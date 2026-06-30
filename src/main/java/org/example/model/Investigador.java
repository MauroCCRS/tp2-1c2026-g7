package org.example.model;

public abstract class Investigador extends Rol{
    protected Jugador objetivoAInvestigar;
    protected Jugador ultimoInvestigado;
    protected Bando resultadoInvestigacion;

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
        this.ultimoInvestigado = objetivo;
    }

    public Bando resultadoInvestigacion() {
        return resultadoInvestigacion;
    }
}
