package org.example.model;

public class AccionVotar implements AccionDePartida {
    private final Jugador votante;
    private final Jugador objetivo;

    public AccionVotar(Jugador votante, Jugador objetivo) {
        this.votante = votante;
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarEn(Fase fase, Partida partida) {
        fase.ejecutarAccionDiurna(
                acciones -> acciones.registrar(this),
                () -> new VotacionInvalidaException("Solo se puede votar durante la fase diurna"));
    }

    void registrarEn(VotacionDiurna votacion) {
        votacion.votar(votante, objetivo);
    }
}
