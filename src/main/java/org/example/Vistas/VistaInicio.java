package org.example.Vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class VistaInicio {

    private App app;

    public VistaInicio(App app) {
        this.app = app;
    }

    public Scene crearEscena() {

        Text titulo = new Text("MAFIA");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 60));
        titulo.setFill(Color.web("#9b2f2f"));


        ImageView imagen = new ImageView(
                new Image(
                        VistaInicio.class.getResource("/mafia.png").toExternalForm()));
        imagen.setPreserveRatio(true);
        imagen.setFitWidth(280);

        Text texto = new Text("Elegí la cantidad de jugadores");
        texto.setFont(Font.font("System", FontWeight.BOLD, 28));
        texto.setFill(Color.WHITE);

        ComboBox<Integer> selectorJugadores = new ComboBox<>();

        for (int i = 5; i <= 12; i++) {
            selectorJugadores.getItems().add(i);
        }

        selectorJugadores.setValue(5);
        selectorJugadores.setPrefWidth(220);
        selectorJugadores.setPrefHeight(40);

        selectorJugadores.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-background-radius: 10;"
        );

        Button botonComenzar = new Button("Comenzar partida");

        botonComenzar.setPrefWidth(220);
        botonComenzar.setPrefHeight(50);
        botonComenzar.setCursor(Cursor.HAND);

        aplicarEstiloNormal(botonComenzar);

        botonComenzar.setOnMouseEntered(
                e -> aplicarEstiloHover(botonComenzar));

        botonComenzar.setOnMouseExited(
                e -> aplicarEstiloNormal(botonComenzar));

        botonComenzar.setOnAction(e -> {

            Integer cantidadJugadores =
                    selectorJugadores.getValue();

            System.out.println(
                    "Cantidad elegida: "
                            + cantidadJugadores);

            app.mostrarVistaIngresarNombres(cantidadJugadores);

        });

        VBox layout = new VBox(25);

        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(30));

        layout.setStyle(
                "-fx-background-color: black;"
        );

        layout.getChildren().addAll(
                titulo,
                imagen,
                texto,
                selectorJugadores,
                botonComenzar
        );

        return new Scene(layout, 800, 600);
    }

    private void aplicarEstiloNormal(Button boton) {

        boton.setStyle(
                "-fx-background-color: #8b1e1e;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: transparent;" +
                        "-fx-border-width: 2px;"
        );
    }

    private void aplicarEstiloHover(Button boton) {

        boton.setStyle(
                "-fx-background-color: #b83232;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 15;" +
                        "-fx-border-radius: 15;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2px;"
        );
    }
}