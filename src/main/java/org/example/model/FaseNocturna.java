package org.example.model;

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
        return resolucion.resolver()
                .<RegistroRonda>map(victima -> new RegistroNocturno(numeroRonda, victima))
                .orElseGet(() -> new RegistroNocheTranquila(numeroRonda));
    }

    @Override
    public Fase siguiente(Partida partida) {
        return partida.crearFaseDiurna(numeroRonda);
    }
}