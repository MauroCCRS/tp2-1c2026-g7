package org.example.Vistas;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VistaIngresarNombres {
    private static final List<String> NOMBRES_PREDETERMINADOS = List.of(
            "Bruno", "Valentina", "Mateo", "Camila", "Santiago",
            "Lucia", "Tomas", "Martina", "Nicolas", "Sofia",
            "Emilia", "Thiago", "Julieta", "Benicio", "Renata",
            "Joaquin", "Emma", "Dante", "Catalina", "Lautaro"
    );

    private final App app;
    private final int cantidadJugadores;
    private final int duracionFaseSegundos;
    private final List<TextField> camposNombre = new ArrayList<>();

    public VistaIngresarNombres(App app, int cantidadJugadores, int duracionFaseSegundos) {
        this.app = app;
        this.cantidadJugadores = cantidadJugadores;
        this.duracionFaseSegundos = duracionFaseSegundos;
    }

    public Scene crearEscena() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("screen-day");

        VBox header = new VBox(8);
        header.setPadding(new Insets(34, 44, 14, 44));
        Text titulo = new Text("Jugadores");
        titulo.getStyleClass().add("page-title");
        Text ayuda = new Text("Carga nombres unicos. Cada fase durara " + duracionFaseSegundos + " segundos.");
        ayuda.getStyleClass().add("muted-copy");
        header.getChildren().addAll(titulo, ayuda);

        GridPane grilla = new GridPane();
        grilla.setHgap(12);
        grilla.setVgap(14);
        grilla.setPadding(new Insets(24));
        grilla.getStyleClass().add("panel");

        List<String> nombresSugeridos = nombresSugeridos();
        for (int i = 1; i <= cantidadJugadores; i++) {
            Label etiqueta = new Label("Jugador " + i);
            etiqueta.getStyleClass().add("field-label");
            etiqueta.setMinWidth(86);
            etiqueta.setPrefWidth(86);
            TextField campo = new TextField(nombresSugeridos.get(i - 1));
            campo.setPromptText("Nombre");
            campo.getStyleClass().add("text-field");
            campo.setPrefWidth(280);
            camposNombre.add(campo);

            int fila = (i - 1) / 2;
            int columna = ((i - 1) % 2) * 2;
            grilla.add(etiqueta, columna, fila);
            grilla.add(campo, columna + 1, fila);
        }

        Label error = new Label();
        error.getStyleClass().add("error-label");

        Button volver = new Button("Volver");
        volver.getStyleClass().add("secondary-button");
        volver.setOnAction(e -> {
            SonidosJuego.click();
            app.mostrarVistaInicio();
        });

        Button continuar = new Button("Repartir roles");
        continuar.getStyleClass().add("primary-button");
        continuar.setOnAction(e -> {
            List<String> nombres = obtenerNombres();
            String validacion = validar(nombres);
            if (!validacion.isEmpty()) {
                SonidosJuego.error();
                error.setText(validacion);
                return;
            }
            SonidosJuego.ok();
            app.crearPartida(nombres);
        });

        HBox acciones = new HBox(12, volver, continuar);
        acciones.setAlignment(Pos.CENTER_RIGHT);

        VBox centro = new VBox(18, grilla, error, acciones);
        centro.setPadding(new Insets(12, 44, 44, 44));
        centro.setMaxWidth(880);
        centro.setAlignment(Pos.CENTER);

        ScrollPane scroll = new ScrollPane(centro);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("transparent-scroll");

        root.setTop(header);
        root.setCenter(scroll);
        Scene scene = new Scene(root, 1200, 760);
        scene.getStylesheets().add(App.recurso("/mafia-ui.css"));
        return scene;
    }

    private List<String> nombresSugeridos() {
        List<String> nombres = new ArrayList<>(NOMBRES_PREDETERMINADOS);
        Collections.shuffle(nombres);
        while (nombres.size() < cantidadJugadores) {
            nombres.add("Jugador " + (nombres.size() + 1));
        }
        return nombres;
    }

    private List<String> obtenerNombres() {
        return camposNombre.stream()
                .map(campo -> campo.getText().trim())
                .toList();
    }

    private String validar(List<String> nombres) {
        if (nombres.stream().anyMatch(String::isEmpty)) {
            return "Todos los jugadores deben tener nombre.";
        }
        Set<String> unicos = new HashSet<>();
        for (String nombre : nombres) {
            if (!unicos.add(nombre.toLowerCase())) {
                return "Los nombres no pueden repetirse.";
            }
        }
        return "";
    }
}
