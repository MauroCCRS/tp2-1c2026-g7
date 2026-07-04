package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Partida {

    private final Jugadores jugadores;
    private final RegistroPartida registro = new RegistroPartida();
    private final FabricaFases fabricaFases;
    private final EvaluadorGanador evaluadorGanador;
    private final AnunciadorGanador anunciadorGanador;
    private Jugador sheriffRevelado;
    private Fase faseActual;

    public Partida(Jugadores jugadores, FabricaFases fabricaFases, EvaluadorGanador evaluadorGanador,
            AnunciadorGanador anunciadorGanador) {
        this.jugadores = jugadores;
        this.fabricaFases = fabricaFases;
        this.evaluadorGanador = evaluadorGanador;
        this.anunciadorGanador = anunciadorGanador;
        this.faseActual = fabricaFases.crearFaseNocturna(1);
    }

    public Fase faseActual() {
        return faseActual;
    }

    public void registrarVotoMafia(Jugador votante, Jugador objetivo) {
        ejecutar(new AccionVotoMafia(votante, objetivo));
    }

    public void elegirInvestigar(Jugador detective, Jugador objetivo) {
        ejecutar(new AccionInvestigar(detective, objetivo));
    }

    public void revelarSheriff(Jugador sheriff) {
        ejecutar(new AccionRevelarSheriff(sheriff));
    }

    public void elegirProteger(Jugador medico, Jugador objetivo) {
        ejecutar(new AccionProteger(medico, objetivo));
    }

    public void nominar(Jugador jugador) {
        ejecutar(new AccionNominar(jugador));
    }

    public void votar(Jugador votante, Jugador objetivo) {
        ejecutar(new AccionVotar(votante, objetivo));
    }

    public void ejecutar(AccionDePartida accion) {
        faseActual.ejecutar(accion, this);
    }

    void registrarSheriffRevelado(Jugador sheriff) {
        this.sheriffRevelado = sheriff;
    }

    public void resolverFaseActual() {
        registro.agregarRegistro(faseActual.resolver());
        if (resultado().isEmpty()) {
            this.faseActual = faseActual.siguiente(fabricaFases);
        }
    }

    public boolean terminada() {
        return resultado().isPresent();
    }

    public boolean sheriffRevelado(Jugador jugador) {
        return sheriffRevelado == jugador;
    }

    public String estiloPantallaFase() {
        return faseActual.estiloPantalla();
    }

    public String estiloEtiquetaFase() {
        return faseActual.estiloEtiqueta();
    }

    public String tituloConteo() {
        return faseActual.tituloConteo();
    }

    public Optional<String> avisoConteo() {
        return faseActual.avisoConteo();
    }

    public Optional<String> rutaImagenVisiblePara(Jugador jugador) {
        if (sheriffRevelado == jugador) {
            return Optional.of(jugador.rutaImagenRol());
        }
        return faseActual.rutaImagenVisiblePara(jugador);
    }

    public String tituloChat() {
        return faseActual.tituloChat();
    }

    public String ayudaChat() {
        return faseActual.ayudaChat();
    }

    public List<Jugador> autoresChat() {
        return faseActual.autoresChat(jugadores);
    }

    public void agregarAccionesDeFase(AccionesPorFase acciones) {
        faseActual.agregarAcciones(acciones);
    }

    public Optional<Bando> resultado() {
        return evaluadorGanador.evaluar(jugadores);
    }

    public Optional<String> anuncio() {
        return anunciadorGanador.anunciar(resultado());
    }

    public String resumen() {
        return registro.generarResumen();
    }

    public String claveFaseActual() {
        return faseActual.clave();
    }

    public String descripcionFaseActual() {
        return faseActual.descripcion();
    }

    public List<DatosJugador> jugadoresEnMesa() {
        return jugadores.todos().stream()
                .map(jugador -> new DatosJugador(
                        jugador.nombre(),
                        jugador.estaVivo(),
                        jugador.cartaVistaPor(jugador).descripcion(),
                        votosRegistrados().containsKey(jugador),
                        rutaImagenVisiblePara(jugador)))
                .toList();
    }

    public List<Jugador> jugadoresVivos() {
        return jugadores.vivos();
    }

    public List<Jugador> mafiososVivos() {
        return jugadores.mafiososVivos();
    }

    public List<Jugador> mafiososVivosQueNoVotaron() {
        return sinVotoRegistrado(jugadores.mafiososVivos());
    }

    public List<Jugador> jugadoresVivosQueNoVotaron() {
        return sinVotoRegistrado(jugadores.vivos());
    }

    public List<Jugador> victimasDisponiblesParaMafia() {
        return jugadores.vivosNoMafiosos();
    }

    public List<Jugador> investigadoresDisponibles() {
        return jugadores.investigadoresVivos();
    }

    public List<Jugador> protectoresDisponibles() {
        return jugadores.protectoresVivos();
    }

    public List<Jugador> jugadoresQuePuedenRevelarse() {
        return jugadores.revelablesVivos();
    }

    private List<Jugador> sinVotoRegistrado(List<Jugador> candidatos) {
        Map<Jugador, Jugador> votos = votosRegistrados();
        return candidatos.stream()
                .filter(jugador -> !votos.containsKey(jugador))
                .toList();
    }

    public List<Jugador> nominados() {
        return faseActual.nominados();
    }

    public Map<Jugador, Jugador> votosRegistrados() {
        return faseActual.votosRegistrados();
    }

    public Map<Jugador, Long> conteoVotos() {
        return faseActual.conteoVotos();
    }
}
