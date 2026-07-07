package org.example.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FaseDiurna extends Fase implements AccionesDiurnas {

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
    protected AccionesDisponibles accionesDisponibles() {
        return new AccionesDisponiblesDiurnas(this);
    }

    @Override
    public void registrar(AccionNominar accion) {
        accion.registrarEn(votacion);
    }

    @Override
    public void registrar(AccionVotar accion) {
        accion.registrarEn(votacion);
    }

    @Override
    public RegistroRonda resolver() {
        ResultadoVotacion resultado = votacion.analizarResultado();

        this.revotacion = resultado.obtenerSiguienteRonda();

        return resultado.generarRegistro(numeroRonda);
    }

    @Override
    public void registrar(AccionRevelarSheriff accion, Partida partida) {
        accion.registrarEn(partida);
    }

    @Override
    public Fase siguiente(FabricaFases fabricaFases) {
        return revotacion
                .<Fase>map(nueva -> new FaseDiurna(numeroRonda, nueva))
                .orElseGet(() -> fabricaFases.crearFaseNocturna(numeroRonda + 1));
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

