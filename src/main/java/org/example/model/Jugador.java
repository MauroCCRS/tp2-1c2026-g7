package org.example.model;
public class Jugador {
    private final String nombre;
    private final Rol rol;
    private final Carta carta;
    private Estado estado;

    public Jugador(String nombre, Rol rol) {
        this.nombre = nombre;
        this.rol = rol;
        this.carta = new Carta(rol);
        this.estado = new Vivo();
    }

    public String nombre() {
        return nombre;
    }

    public String descripcionDeCarta() {
        return carta.descripcion();
    }

    public boolean estaVivo() {
        return estado.estaVivo();
    }

    public void eliminar() {
        this.estado = new Eliminado();
        this.carta.revelar();
    }

    public Bando bando() {
        return rol.bando();
    }

    public Bando resultadoAlSerInvestigado() {
        return rol.resultadoAlSerInvestigado();
    }

    public void elegirInvestigar(Jugador objetivo) {
        rol.elegirInvestigar(objetivo);
    }

    public void elegirProteger(Jugador objetivo) {
        rol.elegirProteger(objetivo);
    }

    public void actuarEnNoche(ResolucionNocturna resolucion) {
        estado.actuarEnNoche(this, resolucion);
    }

    void ejecutarAccionNocturna(ResolucionNocturna resolucion) {
        rol.actuarEnNoche(resolucion);
    }

    public Carta cartaVistaPor(Jugador jugadorQuePregunta) {
        if (this == jugadorQuePregunta || this.ambosSonMafiosos(jugadorQuePregunta)){
            Carta propia = new Carta(rol);
            propia.revelar();
            return propia;
        }
        return carta;
    }

    private boolean ambosSonMafiosos(Jugador otroJugador) {
        return this.esMafioso() && otroJugador.esMafioso();
    }

    public void revelarse(){
            rol.revelarse();
            this.carta.revelar();
    }

    public boolean perteneceA(Bando otroBando) {
        return bando().esMismoBando(otroBando);
    }

    public boolean esMafioso() {
        return bando().esMismoBando(new BandoMafia());
    }
    public VotoMafia crearVotoMafia(Jugador objetivo) {
        return this.rol.crearVotoMafia(objetivo);
    }

    public Jugador revelarJugador(){
        return rol.revelarJugadorInvestigado();
    }
    public Bando resultadoInvestigacion() {
        return rol.resultadoInvestigacion();
    }
}
