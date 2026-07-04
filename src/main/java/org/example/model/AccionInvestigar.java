package org.example.model;

public class AccionInvestigar implements AccionDePartida {
    private final Jugador investigador;
    private final Jugador objetivo;

    public AccionInvestigar(Jugador investigador, Jugador objetivo) {
        this.investigador = investigador;
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarEn(AccionesDisponibles accionesDisponibles, Partida partida) {
        accionesDisponibles.ejecutarAccionNocturna(
                acciones -> acciones.registrar(this),
                () -> new InvestigacionInvalidaException("Solo se puede investigar durante la fase nocturna"));
    }

    void registrar() {
        investigador.elegirInvestigar(objetivo);
    }
}

