package org.example.model;

import java.util.List;

public interface CriterioEmpate {
    Fase faseTrasEmpate(int numeroRonda, List<Jugador> empatados, Partida partida);
}