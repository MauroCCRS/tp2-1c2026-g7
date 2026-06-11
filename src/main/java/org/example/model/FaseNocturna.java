package org.example.model;

public class FaseNocturna extends Fase {

    private final ListaJugadores jugadores;
    private final VotacionMafia votacionMafia;
    private final ResolucionNocturna resolucion = new ResolucionNocturna();

    public FaseNocturna(int numeroRonda, ListaJugadores jugadores) {
        super(numeroRonda);
        this.jugadores = jugadores;
        this.votacionMafia = new VotacionMafia(jugadores);
    }

    public void votarVictima(Jugador objetivo) {
        this.votacionMafia.votar(objetivo);
    }

    @Override
    public RegistroRonda resolver() {
        resolucion.registrarAtaque(votacionMafia.victimaElegida());
        for (Jugador jugador : jugadores.obtenerVivos()) {
            jugador.actuarEnNoche(resolucion);
        }
        return resolucion.resolver()
                .<RegistroRonda>map(victima -> new RegistroNocturno(numeroRonda, victima))
                .orElseGet(() -> new RegistroNocheTranquila(numeroRonda));
    }

    @Override
    public Fase siguiente(Partida partida) {
        return partida.crearFaseDiurna(numeroRonda);
    }
}