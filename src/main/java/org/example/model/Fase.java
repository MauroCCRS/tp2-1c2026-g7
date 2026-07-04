package org.example.model;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public abstract class Fase {

    protected final int numeroRonda;

    protected Fase(int numeroRonda) {
        this.numeroRonda = numeroRonda;
    }

    public abstract RegistroRonda resolver();

    public abstract Fase siguiente(Partida partida);

    public abstract void ejecutar(AccionDePartida accion, Partida partida);

    public void ejecutarAccionNocturna(Consumer<AccionesNocturnas> accion, Supplier<RuntimeException> excepcion) {
        throw excepcion.get();
    }

    public void ejecutarAccionDiurna(Consumer<AccionesDiurnas> accion, Supplier<RuntimeException> excepcion) {
        throw excepcion.get();
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

    public String estiloPantalla() {
        return "screen-day";
    }

    public String estiloEtiqueta() {
        return "phase-day";
    }

    public String tituloConteo() {
        return "Conteo diurno";
    }

    public Optional<String> avisoConteo() {
        if (nominados().isEmpty()) {
            return Optional.of("Todavia no hay nominados.");
        }
        return Optional.empty();
    }

    public Optional<String> rutaImagenVisiblePara(Jugador jugador) {
        return Optional.empty();
    }

    public String tituloChat() {
        return "Chat del dia";
    }

    public String ayudaChat() {
        return "Todos los jugadores vivos pueden participar durante el dia.";
    }

    public List<Jugador> autoresChat(Jugadores jugadores) {
        return jugadores.vivos();
    }

    public void agregarAcciones(AccionesPorFase acciones) {
        acciones.agregarAccionesDiurnas();
    }

    public abstract String nombre();
}
