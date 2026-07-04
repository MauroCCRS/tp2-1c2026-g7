package org.example.model;

import java.util.List;

public class AccionRolAgregarInvestigador implements AccionDeRol {
    private final Jugador jugador;
    private final List<Jugador> candidatos;

    public AccionRolAgregarInvestigador(Jugador jugador, List<Jugador> candidatos) {
        this.jugador = jugador;
        this.candidatos = candidatos;
    }

    @Override
    public void ejecutarPara(Rol rol) { }

    @Override
    public void ejecutarPara(Investigador investigador) {
        candidatos.add(jugador);
    }
}
