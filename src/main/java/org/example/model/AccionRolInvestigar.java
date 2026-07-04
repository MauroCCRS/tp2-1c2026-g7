package org.example.model;

public class AccionRolInvestigar implements AccionDeRol {
    private final Jugador objetivo;

    public AccionRolInvestigar(Jugador objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarPara(Rol rol) {
        throw new InvestigacionInvalidaException("Este rol no puede investigar");
    }

    @Override
    public void ejecutarPara(Investigador investigador) {
        investigador.elegirInvestigar(objetivo);
    }
}
