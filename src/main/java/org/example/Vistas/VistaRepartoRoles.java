package org.example.Vistas;

import org.example.model.Jugador;
import org.example.model.Jugadores;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class VistaRepartoRoles {

    private App app;
    private List<Jugador> jugadores;
    private int indiceActual = 0;
    private ImageView imagenRol;

    private Text mensaje;
    private Text rolMostrado;
    private Button boton;

    public VistaRepartoRoles(App app, Jugadores jugadores) {
        this.app = app;
        this.jugadores = jugadores.todos();
    }

    public Scene crearEscena() {
        mensaje = new Text();
        mensaje.setFont(Font.font("System", FontWeight.BOLD, 34));
        mensaje.setFill(Color.WHITE);

        rolMostrado = new Text("");
        rolMostrado.setFont(Font.font("System", FontWeight.BOLD, 42));
        rolMostrado.setFill(Color.web("#9b2f2f"));

        imagenRol = new ImageView();
        imagenRol.setPreserveRatio(true);
        imagenRol.setFitWidth(180);
        imagenRol.setVisible(false);

        boton = new Button();
        boton.setCursor(Cursor.HAND);
        boton.setStyle(
                "-fx-background-color: #8b1e1e;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 18px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 12;"
        );

        boton.setOnAction(e -> manejarBoton());

        VBox layout = new VBox(30);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(30));
        layout.setStyle("-fx-background-color: black;");
        layout.getChildren().addAll(mensaje, rolMostrado,imagenRol, boton);

        prepararPantallaOculta();

        return new Scene(layout, 800, 600);
    }

    private void prepararPantallaOculta() {
        Jugador jugador = jugadores.get(indiceActual);

        mensaje.setText("Pasale la compu  a " + jugador.nombre());
        rolMostrado.setText("");
        imagenRol.setVisible(false);
        imagenRol.setImage(null);
        boton.setText("Ver  mi  rol");
    }

    private void mostrarRol() {
        Jugador jugador = jugadores.get(indiceActual);
        String descripcionRol = jugador.cartaVistaPor(jugador).descripcion();

        mensaje.setText(jugador.nombre() + ", Tu  rol  es:");
        rolMostrado.setText(
                jugador.cartaVistaPor(jugador).descripcion()
        );

        rolMostrado.setText(descripcionRol);
        imagenRol.setImage(new Image(
                VistaRepartoRoles.class
                        .getResource(rutaImagenPara(descripcionRol))
                        .toExternalForm()
        ));
        imagenRol.setVisible(true);
        boton.setText("Ocultar  y  Continuar");
    }
    private String rutaImagenPara(String rol) {
        return switch (rol) {
            case "Ciudadano" -> "/ciudadano.png";
            case "Mafioso" -> "/mafioso.png";
            case "Detective" -> "/detective.png";
            case "Medico", "Médico" -> "/medico.png";
            case "Padrino" -> "/padrino.png";
            case "Sheriff" -> "/sheriff.png";
            default -> "/ciudadano.png";
        };
    }

    private void manejarBoton() {
        if (rolMostrado.getText().isEmpty()) {
            mostrarRol();
            return;
        }

        indiceActual++;

        if (indiceActual < jugadores.size()) {
            prepararPantallaOculta();
        } else {
            mensaje.setText("Todos los roles fueron repartidos");
            rolMostrado.setText("");
            imagenRol.setVisible(false);
            imagenRol.setImage(null);
            boton.setText("Continuar");
            boton.setOnAction(e -> app.mostrarVistaEstadoPartida());
        }
    }
}