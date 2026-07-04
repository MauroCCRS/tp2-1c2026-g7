package org.example.model;

public class Detective extends Investigador implements AccionNocturna {


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

    @Override
    public void ejecutar(AccionDeRol accion) {
        accion.ejecutarPara(this);
    }
}
