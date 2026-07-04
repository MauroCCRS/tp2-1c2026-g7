package org.example.model;

public class AccionRolVotoMafia implements AccionDeRol {
    private final Jugador objetivo;
    private VotoMafia voto;

    public AccionRolVotoMafia(Jugador objetivo) {
        this.objetivo = objetivo;
    }

    @Override
    public void ejecutarPara(Rol rol) {
        throw new VotacionInvalidaException("Solo el mafioso puede votar");
    }

    @Override
    public void ejecutarPara(Mafioso mafioso) {
        this.voto = mafioso.crearVotoMafia(objetivo);
    }

    public VotoMafia voto() {
        return voto;
    }
}
