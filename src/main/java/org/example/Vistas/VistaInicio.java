package org.example.Vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class VistaInicio {
    private final App app;

    public VistaInicio(App app) {
        this.app = app;
    }

    public Scene crearEscena() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("screen-day");

        VBox contenido = new VBox(18);
        contenido.setAlignment(Pos.CENTER_LEFT);
        contenido.setPadding(new Insets(56, 72, 56, 72));
        contenido.setMaxWidth(720);

        Text titulo = new Text("MAFIA");
        titulo.getStyleClass().add("hero-title");

        Text bajada = new Text("Organiza la partida, reparte roles en privado y guia cada fase desde una mesa clara para todos.");
        bajada.getStyleClass().add("hero-copy");
        bajada.setWrappingWidth(620);

        Text jugadoresLabel = new Text("Cantidad de jugadores");
        jugadoresLabel.getStyleClass().add("field-label-light");

        ComboBox<Integer> selectorJugadores = new ComboBox<>();
        for (int i = 5; i <= 12; i++) {
            selectorJugadores.getItems().add(i);
        }
        selectorJugadores.setValue(5);
        selectorJugadores.getStyleClass().add("combo");
        selectorJugadores.setPrefWidth(210);

        Text timingLabel = new Text("Tiempo por fase");
        timingLabel.getStyleClass().add("field-label-light");

        ComboBox<Integer> selectorTiempo = new ComboBox<>();
        selectorTiempo.getItems().addAll(30, 60, 90, 120, 180);
        selectorTiempo.setValue(60);
        selectorTiempo.getStyleClass().add("combo");
        selectorTiempo.setPrefWidth(210);

        Button comenzar = new Button("Comenzar partida");
        comenzar.getStyleClass().add("primary-button");
        comenzar.setOnAction(e -> {
            SonidosJuego.click();
            app.mostrarVistaIngresarNombres(selectorJugadores.getValue(), selectorTiempo.getValue());
        });

        HBox selectores = new HBox(14, new VBox(8, jugadoresLabel, selectorJugadores), new VBox(8, timingLabel, selectorTiempo));
        selectores.setAlignment(Pos.CENTER_LEFT);

        contenido.getChildren().addAll(titulo, bajada, selectores, comenzar);

        HBox hero = new HBox(contenido);
        hero.setAlignment(Pos.CENTER_LEFT);
        hero.setPadding(new Insets(38));

        root.setCenter(hero);
        Scene scene = new Scene(root, 1200, 760);
        scene.getStylesheets().add(App.recurso("/mafia-ui.css"));
        return scene;
    }
}
