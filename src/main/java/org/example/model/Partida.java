package org.example.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Partida {

    private final Jugadores jugadores;
    private final RegistroPartida registro = new RegistroPartida();
    private final List<Bando> bandos = Arrays.asList(new BandoMafia(), new BandoCiudadano());
    private final CriterioEmpate criterioEmpate;
    private Fase faseActual;

    public Partida(Jugadores jugadores) {
        this(jugadores, new SinEliminacion());
    }

    public Partida(Jugadores jugadores, CriterioEmpate criterioEmpate) {
        this.jugadores = jugadores;
        this.criterioEmpate = criterioEmpate;
        this.faseActual = new FaseNocturna(1, jugadores);
    }

    public Fase faseActual() {
        return faseActual;
    }

    public void registrarVotoMafia(Jugador objetivo) {
        faseActual.registrarVotoMafia(objetivo);
    }

    public void elegirInvestigar(Jugador detective, Jugador objetivo) {
        faseActual.elegirInvestigar(detective, objetivo);
    }

    public void revelarSheriff(Jugador sheriff) {
        faseActual.revelarSheriff(sheriff);
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

    public FaseNocturna crearFaseNocturna(int numeroRonda) {
        return new FaseNocturna(numeroRonda, jugadores);
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
}