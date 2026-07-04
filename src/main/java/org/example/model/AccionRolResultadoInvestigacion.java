package org.example.model;

public class AccionRolResultadoInvestigacion implements AccionDeRol {
    private Bando resultado;

    @Override
    public void ejecutarPara(Rol rol) {
        throw new InvestigacionInvalidaException("Este rol no puede investigar");
    }

    @Override
    public void ejecutarPara(Investigador investigador) {
        this.resultado = investigador.resultadoInvestigacion();
    }

    public Bando resultado() {
        return resultado;
    }
}
