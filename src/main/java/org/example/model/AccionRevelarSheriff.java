package org.example.model;

public class AccionRevelarSheriff implements AccionDePartida {
    private final Jugador sheriff;

    public AccionRevelarSheriff(Jugador sheriff) {
        this.sheriff = sheriff;
    }

    @Override
    public void ejecutarEn(Fase fase, Partida partida) {
        fase.ejecutarAccionDiurna(
                acciones -> acciones.registrar(this, partida),
                () -> new RevelacionInvalidaException("El Sheriff solo puede revelarse durante la fase diurna"));
    }

    void registrarEn(Partida partida) {
        sheriff.revelarse();
        partida.registrarSheriffRevelado(sheriff);
    }
}
