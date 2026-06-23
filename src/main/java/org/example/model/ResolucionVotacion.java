package org.example.model;

public class ResolucionVotacion {
    private final VotacionDiurna votacion;

    public ResolucionVotacion(VotacionDiurna votacion) {
        this.votacion = votacion;
    }

    public RegistroRonda resolverEn(int numeroRonda) {
        return votacion.ganadorUnico()
                .<RegistroRonda>map(eliminado -> {
                    eliminado.eliminar();
                    return new RegistroDiurno(numeroRonda, eliminado);
                })
                .orElseGet(() -> new RegistroSinEliminacionDiurna(numeroRonda));
    }
}