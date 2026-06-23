package org.example.model;

import java.util.List;

public class Ballotage implements CriterioEmpate {
    @Override
    public Fase faseTrasEmpate(int numeroRonda, List<Jugador> empatados, Partida partida) {
        return partida.crearFaseBallotage(numeroRonda, empatados);
    }
}