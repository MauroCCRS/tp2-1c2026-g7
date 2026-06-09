package org.example.model;

public class BandoMafia implements Bando {
    @Override
    public boolean ganoSegun(ListaJugadores jugadores) {
        int mafiososVivos = jugadores.obtenerMafiosos().size();
        int ciudadanosVivos = jugadores.obtenerCiudadanos().size();

        return mafiososVivos >= ciudadanosVivos;
    }
}
