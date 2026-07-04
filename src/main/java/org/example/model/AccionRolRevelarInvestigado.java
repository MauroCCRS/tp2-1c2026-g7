package org.example.model;

public class AccionRolRevelarInvestigado implements AccionDeRol {
    private Jugador investigado;

    @Override
    public void ejecutarPara(Rol rol) {
        throw new RevelacionInvalidaException("Solo el Sheriff puede mostrar una investigacion");
    }

    @Override
    public void ejecutarPara(Sheriff sheriff) {
        this.investigado = sheriff.revelarJugadorInvestigado();
    }

    public Jugador investigado() {
        return investigado;
    }
}
