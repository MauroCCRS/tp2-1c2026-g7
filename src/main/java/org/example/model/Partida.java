package org.example.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Partida {

    private final ListaJugadores jugadores;
    private final RegistroPartida registro = new RegistroPartida();
    private final List<Bando> bandos = Arrays.asList(new BandoMafia(), new BandoCiudadano());
    private Fase faseActual;

    public Partida(ListaJugadores jugadores) {
        this.jugadores = jugadores;
        this.faseActual = new FaseNocturna(1, jugadores);
    }

    public Fase faseActual() {
        return faseActual;
    }

    public void resolverFaseActual() {
        registro.agregarRegistro(faseActual.resolver());
        this.faseActual = faseActual.siguiente(this);
    }

    public FaseNocturna crearFaseNocturna(int numeroRonda) {
        return new FaseNocturna(numeroRonda, jugadores);
    }

    public FaseDiurna crearFaseDiurna(int numeroRonda) {
        return new FaseDiurna(numeroRonda);
    }

    public Optional<Bando> resultado() {
        return bandos.stream()
                .filter(bando -> bando.ganoSegun(jugadores))
                .findFirst();
    }

    public String resumen() {
        return registro.generarResumen();
    }
}