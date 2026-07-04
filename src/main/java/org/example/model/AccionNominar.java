package org.example.model;

public class AccionNominar implements AccionDePartida {
    private final Jugador jugador;

    public AccionNominar(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public void ejecutarEn(Fase fase, Partida partida) {
        fase.ejecutarAccionDiurna(
                acciones -> acciones.registrar(this),
                () -> new NominacionInvalidaException("Solo se puede nominar durante la fase diurna"));
    }

    void registrarEn(VotacionDiurna votacion) {
        votacion.nominar(jugador);
    }
}
