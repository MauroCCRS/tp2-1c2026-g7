package org.example.Vistas;

import org.example.model.Jugador;
import org.example.model.Partida;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class VistaEstadoPartida {

    private App app;
    private Partida partida;

    public VistaEstadoPartida(App app, Partida partida) {
        this.app = app;
        this.partida = partida;
    }

    public Scene crearEscena() {
        boolean esNoche = partida.faseActual().nombre().equals("Nocturna");

        Color colorTexto = esNoche ? Color.WHITE : Color.BLACK;
        String fondo = esNoche
                ? "-fx-background-color: black;"
                : "-fx-background-color: white;";

        Text titulo = new Text("Estado de la partida");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 42));
        titulo.setFill(Color.web("#9b2f2f"));

        Text ronda = new Text(
                "Ronda actual: " + partida.faseActual().numeroRonda()
        );
        ronda.setFont(Font.font("System", FontWeight.BOLD, 26));
        ronda.setFill(colorTexto);

        Text fase = new Text(
                "Fase actual: " + partida.faseActual().nombre()
        );
        fase.setFont(Font.font("System", FontWeight.BOLD, 26));
        fase.setFill(colorTexto);

        Text subtitulo = new Text("Jugadores vivos:");
        subtitulo.setFont(Font.font("System", FontWeight.BOLD, 28));
        subtitulo.setFill(Color.web("#9b2f2f"));

        VBox listaJugadores = new VBox(10);
        listaJugadores.setAlignment(Pos.CENTER);

        for (Jugador jugador : partida.jugadores().todos()) {
            if (jugador.estaVivo()) {
                Text nombre = new Text("- " + jugador.nombre());
                nombre.setFont(Font.font("System", FontWeight.NORMAL, 22));
                nombre.setFill(colorTexto);
                listaJugadores.getChildren().add(nombre);
            }
        }

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle(fondo);

        layout.getChildren().addAll(
                titulo,
                ronda,
                fase,
                subtitulo,
                listaJugadores
        );

        return new Scene(layout, 800, 600);
    }
}