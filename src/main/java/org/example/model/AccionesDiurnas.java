package org.example.model;

public interface AccionesDiurnas {
    void registrar(AccionNominar accion);

    void registrar(AccionVotar accion);

    void registrar(AccionRevelarSheriff accion, Partida partida);
}
