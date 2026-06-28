package org.example.Vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.example.model.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;

public class VistaIngresarNombres {

    private App app;
    private int cantidadJugadores;
    private List<TextField> camposNombre;

    public VistaIngresarNombres(App app, int cantidadJugadores) {
        this.app = app;
        this.cantidadJugadores = cantidadJugadores;
        this.camposNombre = new ArrayList<>();
    }

    public Scene crearEscena() {
        Text titulo = new Text("Ingresa los nombres de los jugadores: ");
        titulo.setFont(Font.font("System", FontWeight.BOLD, 40));
        titulo.setFill(Color.web("#9b2f2f"));

        VBox campos = new VBox(10);
        campos.setAlignment(Pos.CENTER);

        for (int i = 1; i <= cantidadJugadores; i++) {
            TextField campo = new TextField();
            campo.setPromptText("Nombre del jugador " + i);
            campo.setMaxWidth(300);
            camposNombre.add(campo);
            campos.getChildren().add(campo);
        }

        Label error = new Label("");
        error.setTextFill(Color.web("#ff6b6b"));

        Button botonContinuar = new Button("Continuar");
        botonContinuar.setCursor(Cursor.HAND);
        botonContinuar.setStyle(
                "-fx-background-color: #8b1e1e;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;"
        );

        botonContinuar.setOnAction(e -> {
            List<String> nombres = obtenerNombres();

            if (hayNombresVacios(nombres)) {
                error.setText("Todos los jugadores deben tener nombre.");
                return;
            }
//            MezcladorDeRoles mezclador = new MezcladorAleatorioRoles();
//            Configuracion configuracion = new Configuracion(mezclador);
//
//            List<Rol> roles = configuracion.armarRoles(nombres.size());
//
//            RepartidorRoles repartidor = new RepartidorRoles(mezclador);
//            Jugadores jugadores = repartidor.repartir(nombres, roles);

            app.crearPartida(nombres);

            /*
            System.out.println("Jugadores: " + nombres);
            app.mostrarVistaRepartoRoles(nombres);
             */
        });

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: black;");
        layout.getChildren().addAll(titulo, campos, error, botonContinuar);

        return new Scene(layout, 800, 600);
    }

    private List<String> obtenerNombres() {
        List<String> nombres = new ArrayList<>();

        for (TextField campo : camposNombre) {
            nombres.add(campo.getText().trim());
        }

        return nombres;
    }

    private boolean hayNombresVacios(List<String> nombres) {
        return nombres.stream().anyMatch(String::isEmpty);
    }
}