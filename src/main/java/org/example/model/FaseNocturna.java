package org.example.model;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class FaseNocturna extends Fase implements AccionesNocturnas {

    private final Jugadores jugadores;
    private final VotacionMafia votacionMafia;
    private final ResolucionNocturna resolucion = new ResolucionNocturna();

    public FaseNocturna(int numeroRonda, Jugadores jugadores, CriterioConsenso criterioConsenso) {
        super(numeroRonda);
        this.jugadores = jugadores;
        this.votacionMafia = new VotacionMafia(criterioConsenso);
    }

    @Override
    public void ejecutar(AccionDePartida accion, Partida partida) {
        accion.ejecutarEn(this, partida);
    }

    @Override
    public void ejecutarAccionNocturna(Consumer<AccionesNocturnas> accion, Supplier<RuntimeException> excepcion) {
        accion.accept(this);
    }

    @Override
    public void registrar(AccionVotoMafia accion) {
        accion.registrarEn(votacionMafia);
    }

    @Override
    public void registrar(AccionInvestigar accion) {
        accion.registrar();
    }

    @Override
    public void registrar(AccionProteger accion) {
        accion.registrar();
    }

    @Override
    public RegistroRonda resolver() {
        votacionMafia.victimaElegida().ifPresent(resolucion::registrarAtaque);
        jugadores.porCadaVivo(jugador -> jugador.actuarEnNoche(resolucion));

        Optional<Jugador> victima = resolucion.resolver();
        if (victima.isPresent()) {
            victima.get().eliminar();
            return new RegistroNocturno(numeroRonda, victima.get());
        }
        return new RegistroNocheTranquila(numeroRonda);
    }

    @Override
    public Fase siguiente(Partida partida) {
        return partida.crearFaseDiurna(numeroRonda);
    }

    @Override
    public Map<Jugador, Jugador> votosRegistrados() {
        return votacionMafia.votosRegistrados();
    }

    @Override
    public Map<Jugador, Long> conteoVotos() {
        return votacionMafia.conteoPorObjetivo();
    }

    @Override
    public String nombre() {
        return "Nocturna";
    }

    @Override
    public String estiloPantalla() {
        return "screen-night";
    }

    @Override
    public String estiloEtiqueta() {
        return "phase-night";
    }

    @Override
    public String tituloConteo() {
        return "Votos de mafia";
    }

    @Override
    public java.util.Optional<String> avisoConteo() {
        return java.util.Optional.empty();
    }

    @Override
    public java.util.Optional<String> rutaImagenVisiblePara(Jugador jugador) {
        return jugador.rutaImagenNocturnaVisible();
    }

    @Override
    public String tituloChat() {
        return "Chat nocturno";
    }

    @Override
    public String ayudaChat() {
        return "Solo mafiosos pueden leer y escribir durante la noche.";
    }

    @Override
    public java.util.List<Jugador> autoresChat(Jugadores jugadores) {
        return jugadores.mafiososVivos();
    }

    @Override
    public void agregarAcciones(AccionesPorFase acciones) {
        acciones.agregarAccionesNocturnas();
    }
}
