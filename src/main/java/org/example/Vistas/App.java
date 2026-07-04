package org.example.Vistas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.model.Configuracion;
import org.example.model.Jugador;
import org.example.model.Jugadores;
import org.example.model.MezcladorAleatorioRoles;
import org.example.model.MezcladorDeRoles;
import org.example.model.Partida;
import org.example.model.Partidas;
import org.example.model.RepartidorRoles;
import org.example.model.Rol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {
    private Stage stage;
    private Scene escenaPrincipal;
    private Jugadores jugadores;
    private Partida partida;
    private int duracionFaseSegundos = 60;
    private String claveTimer = "";
    private long inicioFaseMillis;
    private final Map<String, List<String>> mensajesPorFase = new HashMap<>();

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("Mafia - TP2");
        stage.setMinWidth(1100);
        stage.setMinHeight(720);
        stage.setMaximized(true);
        stage.setResizable(true);
        mostrarVistaInicio();
        stage.show();
        mantenerMaximizada();
    }

    public void mostrarVistaInicio() {
        cambiarEscena(new VistaInicio(this).crearEscena());
    }

    public void mostrarVistaIngresarNombres(int cantidadJugadores, int duracionFaseSegundos) {
        this.duracionFaseSegundos = duracionFaseSegundos;
        cambiarEscena(new VistaIngresarNombres(this, cantidadJugadores, duracionFaseSegundos).crearEscena());
    }

    public void crearPartida(List<String> nombres) {
        MezcladorDeRoles mezclador = new MezcladorAleatorioRoles();
        Configuracion configuracion = new Configuracion(mezclador);
        List<Rol> roles = configuracion.armarRoles(nombres.size());
        this.jugadores = new RepartidorRoles(mezclador).repartir(nombres, roles);
        this.partida = new Partidas().clasica(jugadores);
        this.mensajesPorFase.clear();
        this.claveTimer = "";
        mostrarVistaRepartoRoles();
    }

    public void mostrarVistaRepartoRoles() {
        cambiarEscena(new VistaRepartoRoles(this, jugadores).crearEscena());
    }

    public void mostrarVistaEstadoPartida() {
        asegurarTimerDeFase();
        cambiarEscena(new VistaEstadoPartida(this, partida).crearEscena());
    }

    public void reiniciar() {
        this.jugadores = null;
        this.partida = null;
        this.mensajesPorFase.clear();
        this.claveTimer = "";
        mostrarVistaInicio();
    }

    public int segundosRestantes() {
        asegurarTimerDeFase();
        long transcurridos = (System.currentTimeMillis() - inicioFaseMillis) / 1000;
        return Math.max(0, duracionFaseSegundos - (int) transcurridos);
    }

    public int duracionFaseSegundos() {
        return duracionFaseSegundos;
    }

    public void reiniciarTimerDeFase() {
        this.claveTimer = claveFaseActual();
        this.inicioFaseMillis = System.currentTimeMillis();
    }

    public void registrarMensaje(Jugador jugador, String texto) {
        String limpio = texto == null ? "" : texto.trim();
        if (limpio.isEmpty()) {
            return;
        }
        String mensaje = jugador.nombre() + ": " + limpio;
        mensajesPorFase.computeIfAbsent(claveFaseActual(), clave -> new ArrayList<>()).add(mensaje);
    }

    public List<String> mensajesActuales() {
        return new ArrayList<>(mensajesPorFase.getOrDefault(claveFaseActual(), List.of()));
    }

    private void cambiarEscena(Scene nuevaEscena) {
        Parent nuevoRoot = nuevaEscena.getRoot();
        if (escenaPrincipal == null) {
            escenaPrincipal = nuevaEscena;
            stage.setScene(escenaPrincipal);
        } else {
            nuevaEscena.setRoot(new Pane());
            escenaPrincipal.setRoot(nuevoRoot);
            for (String hoja : nuevaEscena.getStylesheets()) {
                if (!escenaPrincipal.getStylesheets().contains(hoja)) {
                    escenaPrincipal.getStylesheets().add(hoja);
                }
            }
        }
        mantenerMaximizada();
    }

    private void mantenerMaximizada() {
        if (stage == null) {
            return;
        }
        stage.setIconified(false);
        if (!stage.isMaximized()) {
            stage.setMaximized(true);
        }
        Platform.runLater(() -> {
            stage.setIconified(false);
            stage.setMaximized(true);
        });
    }

    private void asegurarTimerDeFase() {
        String claveActual = claveFaseActual();
        if (!claveActual.equals(claveTimer)) {
            reiniciarTimerDeFase();
        }
    }

    private String claveFaseActual() {
        if (partida == null) {
            return "sin-partida";
        }
        return partida.claveFaseActual();
    }

    public static String recurso(String ruta) {
        return App.class.getResource(ruta).toExternalForm();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
