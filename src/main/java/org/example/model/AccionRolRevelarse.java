package org.example.model;

public class AccionRolRevelarse implements AccionDeRol {
    @Override
    public void ejecutarPara(Rol rol) {
        throw new RevelacionInvalidaException("Solo el Sheriff puede revelarse");
    }

    @Override
    public void ejecutarPara(Sheriff sheriff) {
        sheriff.revelarse();
    }
}
