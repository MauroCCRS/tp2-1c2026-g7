package org.example.Vistas;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import org.example.model.*;
import java.util.List;
import javafx.stage.Stage;
import org.example.model.Jugadores;


public class App extends Application {
    private Stage stage;
    private Jugadores jugadores;
    private Partida partida;

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        stage.setTitle("Mafia - TP2");
        stage.setMaximized(true);

        mostrarVistaInicio();

        stage.show();
    }
    public void mostrarVistaInicio() {
        VistaInicio vistaInicio = new VistaInicio(this);
        stage.setScene(vistaInicio.crearEscena());
    }

    public void mostrarVistaIngresarNombres(int cantidadJugadores) {
        VistaIngresarNombres vista = new VistaIngresarNombres(this, cantidadJugadores);
        stage.setScene(vista.crearEscena());
    }
    public void crearPartida(List<String> nombres) {

        MezcladorDeRoles mezclador = new MezcladorAleatorioRoles();
        Configuracion configuracion = new Configuracion(mezclador);
        List<Rol> roles = configuracion.armarRoles(nombres.size());
        RepartidorRoles repartidor = new RepartidorRoles(mezclador);
        Jugadores jugadores = repartidor.repartir(nombres, roles);

        this.partida = new Partida(jugadores);
        mostrarVistaRepartoRoles(jugadores);
    }
    public void mostrarVistaRepartoRoles(Jugadores jugadores) {
        VistaRepartoRoles vista = new VistaRepartoRoles(this, jugadores);
        stage.setScene(vista.crearEscena());
    }
    public void mostrarVistaEstadoPartida() {
        VistaEstadoPartida vista = new VistaEstadoPartida(this, partida);
        stage.setScene(vista.crearEscena());
    }

   /* @Override
    public void stop() {
        System.out.println("Se ha terminado el programa.");
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}
