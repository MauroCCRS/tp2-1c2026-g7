package org.example.model;

import java.util.Map;
import java.util.Optional;

public class FaseNocturna extends Fase {

    private final Jugadores jugadores;
    private final VotacionMafia votacionMafia;
    private final ResolucionNocturna resolucion = new ResolucionNocturna();

    public FaseNocturna(int numeroRonda, Jugadores jugadores, CriterioConsenso criterioConsenso) {
        super(numeroRonda);
        this.jugadores = jugadores;
        this.votacionMafia = new VotacionMafia(criterioConsenso);
    }

    @Override
    void registrarVotoMafia(Jugador votante, Jugador objetivo) {
        this.votacionMafia.votar(votante, objetivo);
    }

    @Override
    void elegirInvestigar(Jugador detective, Jugador objetivo) {
        detective.elegirInvestigar(objetivo);
    }

    @Override
    void elegirProteger(Jugador medico, Jugador objetivo) {
        medico.elegirProteger(objetivo);
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
}
