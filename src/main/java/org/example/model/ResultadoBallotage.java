package org.example.model;

import java.util.Optional;

public class ResultadoBallotage implements ResultadoVotacion {
    private final VotacionDiurna nuevaVotacion;

    public ResultadoBallotage(VotacionDiurna nuevaVotacion) {
        this.nuevaVotacion = nuevaVotacion;
    }

    @Override
    public RegistroRonda generarRegistro(int numeroRonda) {
        return new RegistroBallotage(numeroRonda, nuevaVotacion.obtenerNominados());
    }

    @Override
    public Optional<VotacionDiurna> obtenerSiguienteRonda() {
        return Optional.of(nuevaVotacion);
    }
}