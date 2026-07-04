package org.example.model;

public interface AccionDeRol {
    void ejecutarPara(Rol rol);

    default void ejecutarPara(Investigador investigador) {
        ejecutarPara((Rol) investigador);
    }

    default void ejecutarPara(Detective detective) {
        ejecutarPara((Investigador) detective);
    }

    default void ejecutarPara(Medico medico) {
        ejecutarPara((Rol) medico);
    }

    default void ejecutarPara(Mafioso mafioso) {
        ejecutarPara((Rol) mafioso);
    }

    default void ejecutarPara(Padrino padrino) {
        ejecutarPara((Mafioso) padrino);
    }

    default void ejecutarPara(Sheriff sheriff) {
        ejecutarPara((Investigador) sheriff);
    }
}
