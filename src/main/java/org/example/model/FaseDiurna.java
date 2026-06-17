package org.example.model;

public class FaseDiurna extends Fase {

    private final VotacionDiurna votacion = new VotacionDiurna(new SinEliminacion());

    public FaseDiurna(int numeroRonda) {
        super(numeroRonda);
    }

    @Override
    void nominar(Jugador jugador) {
        this.votacion.nominar(jugador);
    }

    @Override
    void votar(Jugador votante, Jugador objetivo) {
        this.votacion.votar(votante, objetivo);
    }

    @Override
    public RegistroRonda resolver() {
        return votacion.resolver()
                .<RegistroRonda>map(eliminado -> {
                    eliminado.eliminar();
                    return new RegistroDiurno(numeroRonda, eliminado);
                })
                .orElseGet(() -> new RegistroSinEliminacionDiurna(numeroRonda));
    }

    @Override
    public Fase siguiente(Partida partida) {
        return partida.crearFaseNocturna(numeroRonda + 1);
    }
}