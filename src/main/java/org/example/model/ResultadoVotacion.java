package org.example.model;

import java.util.Optional;

public interface ResultadoVotacion {
    RegistroRonda generarRegistro(int numeroRonda);
    Optional<VotacionDiurna> obtenerSiguienteRonda();
}
