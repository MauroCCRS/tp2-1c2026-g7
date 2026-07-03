package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FaseDiurna extends Fase {

    private final VotacionDiurna votacion;
    private Optional<VotacionDiurna> revotacion = Optional.empty();

    public FaseDiurna(int numeroRonda, CriterioEmpate criterio) {
        this(numeroRonda, new VotacionDiurna(criterio));
    }

    public FaseDiurna(int numeroRonda, VotacionDiurna votacion) {
        super(numeroRonda);
        this.votacion = votacion;
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
        Optional<Jugador> resultado = votacion.resolver();
        if (resultado.isPresent()) {
            Jugador eliminado = resultado.get();
            eliminado.eliminar();
            return new RegistroDiurno(numeroRonda, eliminado);
        }
        this.revotacion = votacion.generarBallotage();
        if (revotacion.isPresent()) {
            List<Jugador> nominadosARevotar = revotacion.get().obtenerNominados();
            return new RegistroBallotage(numeroRonda, nominadosARevotar);
        }
        return new RegistroSinEliminacionDiurna(numeroRonda);
    }

    @Override
    void revelar(Jugador sheriff) {
        sheriff.revelarse();
    }

    @Override
    public Fase siguiente(Partida partida) {
        return revotacion
                .<Fase>map(nueva -> new FaseDiurna(numeroRonda, nueva))
                .orElseGet(() -> partida.crearFaseNocturna(numeroRonda + 1));
    }

    @Override
    public List<Jugador> nominados() {
        return votacion.obtenerNominados();
    }

    @Override
    public Map<Jugador, Jugador> votosRegistrados() {
        return votacion.votosRegistrados();
    }

    @Override
    public Map<Jugador, Long> conteoVotos() {
        return votacion.conteoPorNominado();
    }

    @Override
    public String nombre() {
        return "Diurna";
    }
}
