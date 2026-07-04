package org.example.model;

import java.util.Arrays;
import java.util.List;

public class Partidas {

    public Partida clasica(Jugadores jugadores) {
        return conReglas(jugadores,
                new SinEliminacion(),
                new Mayoria(),
                Arrays.asList(new BandoMafia(), new BandoCiudadano()));
    }

    public Partida conReglas(Jugadores jugadores, CriterioEmpate criterioEmpate,
                             CriterioConsenso criterioConsenso) {
        return conReglas(jugadores,
                criterioEmpate,
                criterioConsenso,
                Arrays.asList(new BandoMafia(), new BandoCiudadano()));
    }

    public Partida conReglas(Jugadores jugadores, CriterioEmpate criterioEmpate,
                             CriterioConsenso criterioConsenso, List<Bando> bandos) {
        return new Partida(jugadores,
                new FabricaFases(jugadores, criterioEmpate, criterioConsenso),
                new EvaluadorGanador(bandos),
                new AnunciadorGanador());
    }
}
