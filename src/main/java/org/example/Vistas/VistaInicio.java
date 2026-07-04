package org.example.Vistas;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.text.Text;

public class VistaInicio {
    private final App app;

    public VistaInicio(App app) {
        this.app = app;
    }

    public Scene crearEscena() {
        StackPane pantalla = new StackPane();
        pantalla.getStyleClass().add("start-screen-root");

        Region fondo = new Region();
        fondo.getStyleClass().add("start-background");
        Region overlay = new Region();
        overlay.getStyleClass().add("start-overlay");
        CapaHumo humo = new CapaHumo();
        humo.widthProperty().bind(pantalla.widthProperty());
        humo.heightProperty().bind(pantalla.heightProperty());
        humo.setMouseTransparent(true);
        humo.iniciar();

        BorderPane root = new BorderPane();
        root.getStyleClass().add("start-content-root");

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
        pantalla.getChildren().addAll(fondo, overlay, humo, root);
        Scene scene = new Scene(pantalla, 1200, 760);
        scene.getStylesheets().add(App.recurso("/mafia-ui.css"));
        return scene;
    }

    private static final class CapaHumo extends Canvas {
        private final List<NubeHumo> nubes = new ArrayList<>();
        private final Random random = new Random(17);
        private long inicio;

        CapaHumo() {
            setOpacity(0.72);
            setEffect(new GaussianBlur(9));
        }

        void iniciar() {
            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    if (inicio == 0) {
                        inicio = now;
                    }
                    pintar((now - inicio) / 1_000_000_000.0);
                }
            };
            timer.start();
            getProperties().put("timerHumo", timer);
        }

        private void pintar(double segundos) {
            double ancho = getWidth();
            double alto = getHeight();
            if (ancho <= 0 || alto <= 0) {
                return;
            }
            prepararNubes(ancho, alto);

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, ancho, alto);
            for (NubeHumo nube : nubes) {
                double progreso = (segundos * nube.velocidad + nube.desfase) % 1.0;
                double x = nube.origenX + progreso * nube.recorridoX + Math.sin(segundos * nube.onda + nube.desfase * 8.0) * nube.vaiven;
                double y = nube.origenY - progreso * nube.recorridoY + Math.cos(segundos * nube.onda * 0.7 + nube.desfase * 5.0) * nube.vaiven * 0.45;
                double pulso = 0.76 + Math.sin(segundos * nube.pulso + nube.desfase * 10.0) * 0.18;
                double entrada = Math.sin(Math.PI * progreso);
                dibujarNube(gc, x, y, nube.ancho, nube.alto, nube.opacidad * pulso * entrada);
            }
        }

        private void prepararNubes(double ancho, double alto) {
            if (!nubes.isEmpty()) {
                return;
            }
            for (int i = 0; i < 42; i++) {
                double baseX = -ancho * 0.16 + random.nextDouble() * ancho * 0.68;
                double baseY = alto * (0.68 + random.nextDouble() * 0.26);
                double tamano = 180 + random.nextDouble() * 360;
                nubes.add(new NubeHumo(
                        baseX,
                        baseY,
                        ancho * (0.16 + random.nextDouble() * 0.34),
                        alto * (0.08 + random.nextDouble() * 0.20),
                        tamano,
                        tamano * (0.16 + random.nextDouble() * 0.16),
                        0.018 + random.nextDouble() * 0.038,
                        0.025 + random.nextDouble() * 0.050,
                        random.nextDouble(),
                        0.24 + random.nextDouble() * 0.42,
                        22 + random.nextDouble() * 54,
                        0.075 + random.nextDouble() * 0.10,
                        -10 + random.nextDouble() * 18));
            }
            for (int i = 0; i < 22; i++) {
                double baseX = ancho * (-0.10 + random.nextDouble() * 0.48);
                double baseY = alto * (0.50 + random.nextDouble() * 0.25);
                double tamano = 120 + random.nextDouble() * 260;
                nubes.add(new NubeHumo(
                        baseX,
                        baseY,
                        ancho * (0.10 + random.nextDouble() * 0.24),
                        alto * (0.12 + random.nextDouble() * 0.24),
                        tamano,
                        tamano * (0.14 + random.nextDouble() * 0.15),
                        0.014 + random.nextDouble() * 0.032,
                        0.030 + random.nextDouble() * 0.050,
                        random.nextDouble(),
                        0.30 + random.nextDouble() * 0.48,
                        14 + random.nextDouble() * 34,
                        0.035 + random.nextDouble() * 0.055,
                        -16 + random.nextDouble() * 28));
            }
        }

        private void dibujarNube(GraphicsContext gc, double x, double y, double ancho, double alto, double opacidad) {
            if (opacidad <= 0) {
                return;
            }
            gc.save();
            Rotate rotate = new Rotate(-6 + Math.sin(x * 0.01) * 4, x, y);
            gc.transform(rotate.getMxx(), rotate.getMyx(), rotate.getMxy(), rotate.getMyy(), rotate.getTx(), rotate.getTy());
            for (int capa = 0; capa < 14; capa++) {
                double proporcion = capa / 13.0;
                double w = ancho * (1.0 - proporcion * 0.46);
                double h = alto * (1.0 - proporcion * 0.34);
                double alpha = opacidad * Math.pow(1.0 - proporcion, 1.9) * 0.13;
                double offsetX = Math.sin(capa * 1.37 + x * 0.006) * ancho * 0.075;
                double offsetY = Math.cos(capa * 1.91 + y * 0.008) * alto * 0.62;
                gc.setFill(Color.rgb(142, 150, 170, limitar(alpha)));
                gc.fillOval(x - w / 2.0 + offsetX, y - h / 2.0 + offsetY, w, h);
            }
            for (int hebra = 0; hebra < 7; hebra++) {
                double alpha = opacidad * (0.018 + hebra * 0.004);
                double w = ancho * (0.42 + hebra * 0.08);
                double h = alto * (0.18 + hebra * 0.04);
                double offsetX = Math.sin(hebra * 2.4 + y * 0.004) * ancho * 0.18;
                double offsetY = -alto * 0.55 + hebra * alto * 0.22;
                gc.setFill(Color.rgb(95, 104, 128, limitar(alpha)));
                gc.fillOval(x - w / 2.0 + offsetX, y + offsetY, w, h);
            }
            gc.restore();
        }

        private double limitar(double valor) {
            return Math.max(0.0, Math.min(1.0, valor));
        }
    }

    private record NubeHumo(double origenX, double origenY, double recorridoX, double recorridoY, double ancho,
                            double alto, double velocidad, double pulso, double desfase, double onda,
                            double vaiven, double opacidad, double inclinacion) {
    }
}