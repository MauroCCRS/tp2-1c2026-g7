package org.example.model;

public class Detective extends Investigador {


    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        this.resultadoInvestigacion = objetivoAInvestigar.resultadoAlSerInvestigado();
    }

    @Override
    public String nombre() {
        return "Detective";
    }
}