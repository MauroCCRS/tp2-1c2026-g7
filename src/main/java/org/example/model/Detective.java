package org.example.model;

public class Detective extends Investigador {


    @Override
    public void actuarEnNoche(ResolucionNocturna resolucion) {
        if (objetivoAInvestigar == null) {
            return;
        }
        this.resultadoInvestigacion = objetivoAInvestigar.resultadoAlSerInvestigado();
        this.objetivoAInvestigar = null;
    }

    @Override
    public String nombre() {
        return "Detective";
    }
}