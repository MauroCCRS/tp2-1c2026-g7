package org.example.model;

import java.util.Optional;

public class FaseNocturna extends Fase {

    private final Jugadores jugadores;
    private final VotacionMafia votacionMafia = new VotacionMafia();
    private final ResolucionNocturna resolucion = new ResolucionNocturna();

    public FaseNocturna(int numeroRonda, Jugadores jugadores) {
        super(numeroRonda);
        this.jugadores = jugadores;
    }

    @Override
    void registrarVotoMafia(Jugador objetivo) {
        this.votacionMafia.votar(objetivo);
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
        resolucion.registrarAtaque(votacionMafia.victimaElegida());
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

    // agrego para la  visualizacion del estado de la ronda actual.
    @Override
    public String nombre() {return "Nocturna";}
}