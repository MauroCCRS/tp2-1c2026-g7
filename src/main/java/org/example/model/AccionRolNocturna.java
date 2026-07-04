package org.example.model;

public class AccionRolNocturna implements AccionDeRol {
    private final ResolucionNocturna resolucion;

    public AccionRolNocturna(ResolucionNocturna resolucion) {
        this.resolucion = resolucion;
    }

    @Override
    public void ejecutarPara(Rol rol) { }

    @Override
    public void ejecutarPara(Detective detective) {
        detective.actuarEnNoche(resolucion);
    }

    @Override
    public void ejecutarPara(Medico medico) {
        medico.actuarEnNoche(resolucion);
    }
}
