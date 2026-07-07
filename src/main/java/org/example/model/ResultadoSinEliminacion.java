package org.example.model;

import java.util.Optional;

public class ResultadoSinEliminacion implements ResultadoVotacion {
    @Override
    public RegistroRonda generarRegistro(int numeroRonda) {
        return new RegistroSinEliminacionDiurna(numeroRonda);
    }

    @Override
    public Optional<VotacionDiurna> obtenerSiguienteRonda() {
        return Optional.empty();
    }
}