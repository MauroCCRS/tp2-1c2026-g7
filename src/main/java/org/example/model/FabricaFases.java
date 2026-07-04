package org.example.model;

public class FabricaFases {
    private final Jugadores jugadores;
    private final CriterioEmpate criterioEmpate;
    private final CriterioConsenso criterioConsenso;

    public FabricaFases(Jugadores jugadores, CriterioEmpate criterioEmpate, CriterioConsenso criterioConsenso) {
        this.jugadores = jugadores;
        this.criterioEmpate = criterioEmpate;
        this.criterioConsenso = criterioConsenso;
    }

    public FaseNocturna crearFaseNocturna(int numeroRonda) {
        return new FaseNocturna(numeroRonda, jugadores, criterioConsenso);
    }

    public FaseDiurna crearFaseDiurna(int numeroRonda) {
        return new FaseDiurna(numeroRonda, criterioEmpate);
    }
}
