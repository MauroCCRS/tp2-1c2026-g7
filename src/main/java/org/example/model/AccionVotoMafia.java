package org.example.model;

public class AccionVotoMafia implements AccionDePartida {
    private final Jugador votante;
    private final Jugador objetivo;

    public AccionVotoMafia(Jugador votante, Jugador objetivo) {
        this.votante = votante;
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarEn(Fase fase, Partida partida) {
        fase.ejecutarAccionNocturna(
                acciones -> acciones.registrar(this),
                () -> new VotacionInvalidaException("La mafia solo puede votar durante la fase nocturna"));
    }

    void registrarEn(VotacionMafia votacionMafia) {
        votacionMafia.votar(votante, objetivo);
    }
}
