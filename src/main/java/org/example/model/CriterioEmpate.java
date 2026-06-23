package org.example.model;

import java.util.List;

public interface CriterioEmpate {
    List<Jugador> desempatar(List<Jugador> empatados);
}