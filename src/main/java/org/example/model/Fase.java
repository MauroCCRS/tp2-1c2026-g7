package org.example.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public abstract class Fase {

    protected final int numeroRonda;

    protected Fase(int numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public abstract RegistroRonda resolver();

    public abstract Fase siguiente(Partida partida);

    void registrarVotoMafia(Jugador votante, Jugador objetivo) {
        throw new VotacionInvalidaException("La mafia solo puede votar durante la fase nocturna");
    }

    void nominar(Jugador jugador) {
        throw new NominacionInvalidaException("Solo se puede nominar durante la fase diurna");
    }

    void votar(Jugador votante, Jugador objetivo) {
        throw new VotacionInvalidaException("Solo se puede votar durante la fase diurna");
    }

    void elegirInvestigar(Jugador detective, Jugador objetivo) {
        throw new InvestigacionInvalidaException("Solo se puede investigar durante la fase nocturna");
    }

    void revelar(Jugador sheriff) {
        throw new RevelacionInvalidaException("El Sheriff solo puede revelarse durante la fase diurna");
    }

    void elegirProteger(Jugador medico, Jugador objetivo) {
        throw new ProteccionInvalidaException("Solo se puede proteger durante la fase nocturna");
    }

    public List<Jugador> nominados() {
        return Collections.emptyList();
    }

    public Map<Jugador, Jugador> votosRegistrados() {
        return Collections.emptyMap();
    }

    public Map<Jugador, Long> conteoVotos() {
        return Collections.emptyMap();
    }

    public int numeroRonda() {
        return numeroRonda;
    }

    public abstract String nombre();
}
