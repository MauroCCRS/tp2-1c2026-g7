package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class SinEliminacion implements CriterioEmpate {
    @Override
    public List<Jugador> desempatar(List<Jugador> empatados) {
        return new ArrayList<>();
    }
}