package org.example.model;

import java.util.List;
import java.util.Optional;

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
        rol.ejecutar(new AccionRolInvestigar(objetivo));
    }

    public void elegirProteger(Jugador objetivo) {
        rol.ejecutar(new AccionRolProteger(objetivo));
    }

    public void actuarEnNoche(ResolucionNocturna resolucion) {
        estado.actuarEnNoche(this, resolucion);
    }

    void ejecutarAccionNocturna(ResolucionNocturna resolucion) {
        rol.ejecutar(new AccionRolNocturna(resolucion));
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
        rol.ejecutar(new AccionRolRevelarse());
        this.carta.revelar();
    }

    public boolean perteneceA(Bando otroBando) {
        return bando().esMismoBando(otroBando);
    }

    public boolean esMafioso() {
        return bando().esMismoBando(new BandoMafia());
    }

    public String rutaImagenRol() {
        return rol.rutaImagen();
    }

    public Optional<String> rutaImagenNocturnaVisible() {
        List<String> rutas = new java.util.ArrayList<>();
        estado.siEstaVivo(this, jugador -> {
            if (jugador.esMafioso()) {
                rutas.add(jugador.rutaImagenRol());
            }
        });
        return rutas.stream().findFirst();
    }

    public void agregarSiPuedeInvestigar(List<Jugador> candidatos) {
        estado.siEstaVivo(this, jugador -> rol.ejecutar(new AccionRolAgregarInvestigador(jugador, candidatos)));
    }

    public void agregarSiPuedeProteger(List<Jugador> candidatos) {
        estado.siEstaVivo(this, jugador -> rol.ejecutar(new AccionRolAgregarProtector(jugador, candidatos)));
    }

    public void agregarSiPuedeRevelarse(List<Jugador> candidatos) {
        estado.siEstaVivo(this, jugador -> rol.ejecutar(new AccionRolAgregarRevelable(jugador, candidatos)));
    }

    public VotoMafia crearVotoMafia(Jugador objetivo) {
        AccionRolVotoMafia accion = new AccionRolVotoMafia(objetivo);
        rol.ejecutar(accion);
        return accion.voto();
    }

    public Jugador revelarJugador(){
        AccionRolRevelarInvestigado accion = new AccionRolRevelarInvestigado();
        rol.ejecutar(accion);
        return accion.investigado();
    }
    public Bando resultadoInvestigacion() {
        AccionRolResultadoInvestigacion accion = new AccionRolResultadoInvestigacion();
        rol.ejecutar(accion);
        return accion.resultado();
    }
}
