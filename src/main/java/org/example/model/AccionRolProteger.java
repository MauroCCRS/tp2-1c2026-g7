package org.example.model;

public class AccionRolProteger implements AccionDeRol {
    private final Jugador objetivo;

    public AccionRolProteger(Jugador objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarPara(Rol rol) {
        throw new ProteccionInvalidaException("Este rol no puede proteger");
    }

    @Override
    public void ejecutarPara(Medico medico) {
        medico.elegirProteger(objetivo);
    }
}
