package org.example.model;

public class AccionProteger implements AccionDePartida {
    private final Jugador protector;
    private final Jugador objetivo;

    public AccionProteger(Jugador protector, Jugador objetivo) {
        this.protector = protector;
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarEn(AccionesDisponibles accionesDisponibles, Partida partida) {
        accionesDisponibles.ejecutarAccionNocturna(
                acciones -> acciones.registrar(this),
                () -> new ProteccionInvalidaException("Solo se puede proteger durante la fase nocturna"));
    }

    void registrar() {
        protector.elegirProteger(objetivo);
    }
}

