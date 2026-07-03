package org.example.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Partida {

    private final Jugadores jugadores;
    private final RegistroPartida registro = new RegistroPartida();
    private final List<Bando> bandos = Arrays.asList(new BandoMafia(), new BandoCiudadano());
    private final CriterioEmpate criterioEmpate;
    private final CriterioConsenso criterioConsenso;
    private Jugador sheriffRevelado;
    private Fase faseActual;

    public Partida(Jugadores jugadores) {
        this(jugadores, new SinEliminacion(), new Mayoria());
    }

    public Partida(Jugadores jugadores, CriterioEmpate criterioEmpate, CriterioConsenso criterioConsenso) {
        this.jugadores = jugadores;
        this.criterioEmpate = criterioEmpate;
        this.criterioConsenso = criterioConsenso;
        this.faseActual = crearFaseNocturna(1);
    }

    public Fase faseActual() {
        return faseActual;
    }

    public void registrarVotoMafia(Jugador votante, Jugador objetivo) {
        faseActual.registrarVotoMafia(votante, objetivo);
    }

    public void elegirInvestigar(Jugador detective, Jugador objetivo) {
        faseActual.elegirInvestigar(detective, objetivo);
    }

    public void revelarSheriff(Jugador sheriff) {
        faseActual.revelar(sheriff);
        this.sheriffRevelado = sheriff;
    }

    public void elegirProteger(Jugador medico, Jugador objetivo) {
        faseActual.elegirProteger(medico, objetivo);
    }

    public void nominar(Jugador jugador) {
        faseActual.nominar(jugador);
    }

    public void votar(Jugador votante, Jugador objetivo) {
        faseActual.votar(votante, objetivo);
    }

    public void resolverFaseActual() {
        registro.agregarRegistro(faseActual.resolver());
        if (resultado().isEmpty()) {
            this.faseActual = faseActual.siguiente(this);
        }
    }

    public boolean terminada() {
        return resultado().isPresent();
    }

    public boolean sheriffRevelado(Jugador jugador) {
        return sheriffRevelado == jugador;
    }

    public FaseNocturna crearFaseNocturna(int numeroRonda) {
        return new FaseNocturna(numeroRonda, jugadores, criterioConsenso);
    }

    public FaseDiurna crearFaseDiurna(int numeroRonda) {
        return new FaseDiurna(numeroRonda, criterioEmpate);
    }

    public Optional<Bando> resultado() {
        return bandos.stream()
                .filter(bando -> bando.ganoSegun(jugadores))
                .findFirst();
    }

    public Optional<String> anuncio() {
        return resultado().map(bando -> "Ganador: " + bando.nombre());
    }

    public String resumen() {
        return registro.generarResumen();
    }

    public Jugadores jugadores() {
        return jugadores;
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
