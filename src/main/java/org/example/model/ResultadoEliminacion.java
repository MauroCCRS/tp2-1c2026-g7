package org.example.model;

import java.util.Optional;

public class ResultadoEliminacion implements ResultadoVotacion{
    private final Jugador eliminado;

    public ResultadoEliminacion(Jugador eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public RegistroRonda generarRegistro(int numeroRonda) {
        this.eliminado.eliminar();
        return new RegistroDiurno(numeroRonda, eliminado);
    }

    @Override
    public Optional<VotacionDiurna> obtenerSiguienteRonda() {
        return Optional.empty();
    }
}
