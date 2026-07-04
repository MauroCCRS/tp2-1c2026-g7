package org.example.Vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.example.model.Jugador;
import org.example.model.Jugadores;

import java.util.List;

public class VistaRepartoRoles {
    private final App app;
    private final List<Jugador> jugadores;
    private int indiceActual = 0;
    private boolean rolVisible = false;
    private Text titulo;
    private Text subtitulo;
    private Text rol;
    private ImageView imagenRol;
    private Button accion;

    public VistaRepartoRoles(App app, Jugadores jugadores) {
        this.app = app;
        this.jugadores = jugadores.todos();
    }

    public Scene crearEscena() {
        StackPane pantalla = new StackPane();
        pantalla.getStyleClass().add("start-screen-root");

        Region fondo = new Region();
        fondo.getStyleClass().add("start-background");
        Region overlay = new Region();
        overlay.getStyleClass().add("start-overlay");

        BorderPane root = new BorderPane();
        root.getStyleClass().add("start-content-root");

        VBox tarjeta = new VBox(20);
        tarjeta.setAlignment(Pos.CENTER);
        tarjeta.setPadding(new Insets(34));
        tarjeta.getStyleClass().add("role-reveal-panel");
        tarjeta.setMaxWidth(560);

        titulo = new Text();
        titulo.getStyleClass().add("panel-page-title");
        subtitulo = new Text();
        subtitulo.getStyleClass().add("panel-copy");
        subtitulo.setWrappingWidth(470);
        rol = new Text();
        rol.getStyleClass().add("role-title");

        imagenRol = new ImageView();
        imagenRol.setFitWidth(240);
        imagenRol.setFitHeight(240);
        imagenRol.setPreserveRatio(true);
        imagenRol.setSmooth(true);

        accion = new Button();
        accion.getStyleClass().add("primary-button");
        accion.setOnAction(e -> manejarAccion());

        tarjeta.getChildren().addAll(titulo, subtitulo, imagenRol, rol, accion);

        StackPane centro = new StackPane(tarjeta);
        centro.setPadding(new Insets(36));
        root.setCenter(centro);

        HBox progreso = new HBox();
        progreso.setAlignment(Pos.CENTER);
        progreso.setPadding(new Insets(0, 0, 30, 0));
        Text paso = new Text("Reparto privado de roles");
        paso.getStyleClass().add("field-label-light");
        progreso.getChildren().add(paso);
        root.setBottom(progreso);

        prepararOculto();
        pantalla.getChildren().addAll(fondo, overlay, root);

        Scene scene = new Scene(pantalla, 1200, 760);
        scene.getStylesheets().add(App.recurso("/mafia-ui.css"));
        return scene;
    }

    private void manejarAccion() {
        SonidosJuego.click();
        if (!rolVisible) {
            mostrarRol();
            return;
        }
        indiceActual++;
        if (indiceActual >= jugadores.size()) {
            abrirTablero();
            return;
        }
        prepararOculto();
    }

    private void abrirTablero() {
        try {
            accion.setDisable(true);
            app.mostrarVistaEstadoPartida();
        } catch (RuntimeException error) {
            accion.setDisable(false);
            subtitulo.setText("No se pudo abrir el tablero: " + error.getMessage());
        }
    }

    private void prepararOculto() {
        Jugador jugador = jugadores.get(indiceActual);
        rolVisible = false;
        titulo.setText("Turno de " + jugador.nombre());
        subtitulo.setText("Pasa la pantalla a esta persona antes de revelar la carta.");
        rol.setText("");
        imagenRol.setImage(null);
        imagenRol.setVisible(false);
        accion.setText("Ver mi rol");
    }

    private void mostrarRol() {
        Jugador jugador = jugadores.get(indiceActual);
        String descripcion = jugador.cartaVistaPor(jugador).descripcion();
        rolVisible = true;
        titulo.setText(jugador.nombre());
        subtitulo.setText("Recuerda tu rol y oculta la pantalla antes de pasar al siguiente jugador.");
        rol.setText(descripcion);
        imagenRol.setImage(new Image(App.recurso(rutaImagenPara(descripcion))));
        imagenRol.setVisible(true);
        imagenRol.setOpacity(1);
        accion.setText(indiceActual == jugadores.size() - 1 ? "Ir al tablero" : "Ocultar y continuar");
    }

    private String rutaImagenPara(String rol) {
        return switch (rol) {
            case "Ciudadano" -> "/ciudadano.png";
            case "Mafioso" -> "/mafioso.png";
            case "Detective" -> "/detective.png";
            case "Medico", "MÃƒÆ’Ã‚Â©dico", "MÃƒÆ’Ã†â€™Ãƒâ€šÃ‚Â©dico" -> "/medico.png";
            case "Padrino" -> "/padrino.png";
            case "Sheriff" -> "/sheriff.png";
            default -> "/ciudadano.png";
        };
    }
}
