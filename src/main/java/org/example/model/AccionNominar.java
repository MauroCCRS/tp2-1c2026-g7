package org.example.model;

public class AccionNominar implements AccionDePartida {
    private final Jugador jugador;

    public AccionNominar(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public void ejecutarEn(AccionesDisponibles accionesDisponibles, Partida partida) {
        accionesDisponibles.ejecutarAccionDiurna(
                acciones -> acciones.registrar(this),
                () -> new NominacionInvalidaException("Solo se puede nominar durante la fase diurna"));
    }

    void registrarEn(VotacionDiurna votacion) {
        votacion.nominar(jugador);
    }
}

