package org.example.Vistas;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.StringConverter;
import org.example.model.FaseNocturna;
import org.example.model.Jugador;
import org.example.model.Partida;

import java.util.List;
import java.util.Map;

public class VistaEstadoPartida {
    private final App app;
    private final Partida partida;
    private Label mensaje;
    private Timeline timer;

    public VistaEstadoPartida(App app, Partida partida) {
        this.app = app;
        this.partida = partida;
    }

    public Scene crearEscena() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add(esNoche() ? "screen-night" : "screen-day");
        root.setTop(crearHeader());
        root.setCenter(crearContenido());

        Scene scene = new Scene(root, 1280, 820);
        scene.getStylesheets().add(App.recurso("/mafia-ui.css"));
        return scene;
    }

    private HBox crearHeader() {
        Text titulo = new Text("Mesa de partida");
        titulo.getStyleClass().add("page-title");

        Label fase = new Label(partida.faseActual().nombre() + " - Ronda " + partida.faseActual().numeroRonda());
        fase.getStyleClass().add(esNoche() ? "phase-night" : "phase-day");

        Label tiempo = new Label();
        tiempo.getStyleClass().add("timer-label");
        iniciarTimer(tiempo);

        Button reiniciar = new Button("Nueva partida");
        reiniciar.getStyleClass().add("secondary-button");
        reiniciar.setOnAction(e -> {
            detenerTimer();
            SonidosJuego.click();
            app.reiniciar();
        });

        HBox header = new HBox(18, titulo, fase, tiempo, crearSeparador(), reiniciar);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(28, 34, 14, 34));
        return header;
    }

    private HBox crearContenido() {
        VBox estado = crearPanelEstado();
        VBox jugadores = crearPanelJugadores();
        VBox acciones = crearPanelAcciones();

        HBox contenido = new HBox(18, estado, jugadores, acciones);
        contenido.setPadding(new Insets(12, 34, 34, 34));
        HBox.setHgrow(jugadores, Priority.ALWAYS);
        return contenido;
    }

    private VBox crearPanelEstado() {
        VBox panel = panel("Estado");

        Label resultado = new Label(partida.anuncio().orElse("Partida en curso"));
        resultado.getStyleClass().add(partida.terminada() ? "winner-label" : "muted-label");

        mensaje = new Label(partida.terminada() ? "La partida termino." : "Registra acciones y resuelve la fase cuando este lista.");
        mensaje.getStyleClass().add("info-label");
        mensaje.setWrapText(true);

        VBox conteo = crearPanelConteo();
        VBox chat = crearChat();

        TextArea resumen = new TextArea(partida.resumen());
        resumen.setEditable(false);
        resumen.setWrapText(true);
        resumen.getStyleClass().add("history-area");
        resumen.setPrefHeight(210);

        Button resolver = new Button(partida.terminada() ? "Partida finalizada" : "Resolver fase");
        resolver.getStyleClass().add("primary-button");
        resolver.setMaxWidth(Double.MAX_VALUE);
        resolver.setDisable(partida.terminada());
        resolver.setOnAction(e -> ejecutarResolver());

        panel.getChildren().addAll(resultado, mensaje, conteo, chat, resumen, resolver);
        panel.setPrefWidth(360);
        return panel;
    }

    private VBox crearPanelConteo() {
        VBox caja = new VBox(8);
        caja.getStyleClass().add("count-box");

        Label titulo = new Label(esNoche() ? "Votos de mafia" : "Conteo diurno");
        titulo.getStyleClass().add("action-title");
        caja.getChildren().add(titulo);

        if (!esNoche() && partida.nominados().isEmpty()) {
            Label vacio = new Label("Todavia no hay nominados.");
            vacio.getStyleClass().add("muted-label");
            caja.getChildren().add(vacio);
        }

        if (partida.conteoVotos().isEmpty()) {
            Label sinVotos = new Label("Sin votos registrados.");
            sinVotos.getStyleClass().add("muted-label");
            caja.getChildren().add(sinVotos);
        } else {
            partida.conteoVotos().forEach((jugador, cantidad) -> {
                Label fila = new Label(jugador.nombre() + ": " + cantidad + " voto" + (cantidad == 1 ? "" : "s"));
                fila.getStyleClass().add("count-label");
                caja.getChildren().add(fila);
            });
        }

        if (!partida.votosRegistrados().isEmpty()) {
            Label subtitulo = new Label("Ya votaron");
            subtitulo.getStyleClass().add("field-label");
            caja.getChildren().add(subtitulo);
            partida.votosRegistrados().forEach((votante, objetivo) -> {
                Label voto = new Label(votante.nombre() + " -> " + objetivo.nombre());
                voto.getStyleClass().add("muted-label");
                caja.getChildren().add(voto);
            });
        }
        return caja;
    }

    private VBox crearChat() {
        VBox caja = new VBox(8);
        caja.getStyleClass().add("count-box");
        Label titulo = new Label(esNoche() ? "Chat nocturno" : "Chat del dia");
        titulo.getStyleClass().add("action-title");
        Label ayuda = new Label(esNoche() ? "Solo mafiosos pueden leer y escribir durante la noche." : "Todos los jugadores vivos pueden participar durante el dia.");
        ayuda.getStyleClass().add("muted-label");
        ayuda.setWrapText(true);

        TextArea mensajes = new TextArea(String.join("\n", app.mensajesActuales()));
        mensajes.setEditable(false);
        mensajes.setWrapText(true);
        mensajes.getStyleClass().add("chat-area");
        mensajes.setPrefHeight(95);

        ComboBox<Jugador> autor = comboJugadores(esNoche() ? mafiososVivos() : vivos());
        TextField texto = new TextField();
        texto.setPromptText("Mensaje");
        texto.getStyleClass().add("text-field");
        Button enviar = botonAccion("Enviar");
        enviar.setOnAction(e -> {
            if (autor.getValue() == null || texto.getText().trim().isEmpty()) {
                SonidosJuego.error();
                mensaje.setText("Elegi jugador y escribi un mensaje.");
                mensaje.getStyleClass().setAll("error-label");
                return;
            }
            app.registrarMensaje(autor.getValue(), texto.getText());
            SonidosJuego.ok();
            app.mostrarVistaEstadoPartida();
        });

        caja.getChildren().addAll(titulo, ayuda, mensajes, autor, texto, enviar);
        return caja;
    }

    private VBox crearPanelJugadores() {
        VBox panel = panel("Jugadores");
        GridPane grilla = new GridPane();
        grilla.setHgap(14);
        grilla.setVgap(14);

        List<Jugador> todos = partida.jugadores().todos();
        for (int i = 0; i < todos.size(); i++) {
            grilla.add(crearTarjetaJugador(todos.get(i)), i % 2, i / 2);
        }

        ScrollPane scroll = new ScrollPane(grilla);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("transparent-scroll");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        panel.getChildren().add(scroll);
        panel.setMinWidth(430);
        return panel;
    }

    private VBox crearTarjetaJugador(Jugador jugador) {
        VBox tarjeta = new VBox(8);
        tarjeta.getStyleClass().add(jugador.estaVivo() ? "player-card" : "player-card-dead");
        tarjeta.setPadding(new Insets(14));
        tarjeta.setPrefWidth(215);

        HBox fila = new HBox(10);
        fila.setAlignment(Pos.CENTER_LEFT);

        Label nombre = new Label(jugador.nombre());
        nombre.getStyleClass().add("player-name");
        if (debeMostrarIconoDeRol(jugador)) {
            ImageView icono = new ImageView(new Image(App.recurso(rutaImagenPara(rolDe(jugador)))));
            icono.setFitWidth(42);
            icono.setFitHeight(42);
            icono.setPreserveRatio(true);
            fila.getChildren().add(icono);
        }
        fila.getChildren().add(nombre);

        Label estado = new Label(jugador.estaVivo() ? estadoVotoDe(jugador) : "Eliminado - " + rolDe(jugador));
        estado.getStyleClass().add(jugador.estaVivo() ? "alive-label" : "dead-label");
        tarjeta.getChildren().addAll(fila, estado);
        return tarjeta;
    }

    private boolean debeMostrarIconoDeRol(Jugador jugador) {
        return (esNoche() && jugador.estaVivo() && esMafioso(jugador)) || partida.sheriffRevelado(jugador);
    }
    private String estadoVotoDe(Jugador jugador) {
        if (partida.votosRegistrados().containsKey(jugador)) {
            return "Ya voto";
        }
        return rolDe(jugador).equals("Carta oculta") ? "Vivo" : "Vivo";
    }

    private VBox crearPanelAcciones() {
        VBox panel = new VBox(12);
        panel.getStyleClass().add("actions-panel");
        panel.setPrefWidth(370);
        panel.setMaxWidth(390);

        Label titulo = new Label("Acciones");
        titulo.getStyleClass().add("actions-title");
        panel.getChildren().add(titulo);

        VBox lista = new VBox(12);
        lista.getStyleClass().add("actions-list");

        if (partida.terminada()) {
            Label finalizada = new Label("No hay mas acciones disponibles.");
            finalizada.getStyleClass().add("muted-label");
            lista.getChildren().add(finalizada);
        } else if (esNoche()) {
            lista.getChildren().addAll(crearAccionMafia(), crearAccionInvestigacion(), crearAccionProteccion());
        } else {
            lista.getChildren().addAll(crearAccionNominacion(), crearAccionVotoDiurno(), crearAccionSheriff());
        }

        ScrollPane scroll = new ScrollPane(lista);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("actions-scroll");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        panel.getChildren().add(scroll);
        return panel;
    }

    private VBox crearAccionMafia() {
        ComboBox<Jugador> votante = comboJugadores(mafiososVivosQueNoVotaron());
        ComboBox<Jugador> objetivo = comboJugadores(vivosNoMafiosos());
        Button boton = botonAccion("Registrar ataque");
        boton.setOnAction(e -> ejecutarConSeleccion("Elegi mafioso y victima.", true, () -> {
            partida.registrarVotoMafia(votante.getValue(), objetivo.getValue());
            return "Ataque registrado.";
        }, votante, objetivo));
        return accion("Mafia", "Solo mafiosos vivos pueden actuar de noche.", votante, objetivo, boton);
    }

    private VBox crearAccionInvestigacion() {
        ComboBox<Jugador> detective = comboJugadores(investigadoresVivos());
        ComboBox<Jugador> objetivo = comboJugadores(vivos());
        Button boton = botonAccion("Investigar");
        boton.setOnAction(e -> ejecutarConSeleccion("Elegi investigador y objetivo.", false, () -> {
            partida.elegirInvestigar(detective.getValue(), objetivo.getValue());
            return "Investigacion registrada.";
        }, detective, objetivo));
        return accion("Investigacion", "Solo Detective o Sheriff pueden investigar.", detective, objetivo, boton);
    }

    private VBox crearAccionProteccion() {
        ComboBox<Jugador> medico = comboJugadores(medicosVivos());
        ComboBox<Jugador> objetivo = comboJugadores(vivos());
        Button boton = botonAccion("Proteger");
        boton.setOnAction(e -> ejecutarConSeleccion("Elegi medico y objetivo.", false, () -> {
            partida.elegirProteger(medico.getValue(), objetivo.getValue());
            return "Proteccion registrada.";
        }, medico, objetivo));
        return accion("Medico", "Solo el medico puede proteger durante la noche.", medico, objetivo, boton);
    }

    private VBox crearAccionNominacion() {
        ComboBox<Jugador> nominado = comboJugadores(vivos());
        Button boton = botonAccion("Nominar");
        boton.setOnAction(e -> ejecutarConSeleccion("Elegi a quien nominar.", true, () -> {
            partida.nominar(nominado.getValue());
            return "Nominacion registrada.";
        }, nominado));
        return accion("Nominacion", "Agrega candidatos para la votacion diurna.", nominado, null, boton);
    }

    private VBox crearAccionVotoDiurno() {
        ComboBox<Jugador> votante = comboJugadores(vivosQueNoVotaron());
        ComboBox<Jugador> objetivo = comboJugadores(partida.nominados());
        Button boton = botonAccion("Votar");
        boton.setDisable(partida.nominados().isEmpty());
        boton.setOnAction(e -> ejecutarConSeleccion("Elegi votante y nominado.", true, () -> {
            partida.votar(votante.getValue(), objetivo.getValue());
            return "Voto registrado.";
        }, votante, objetivo));
        return accion("Votacion", "Solo votan jugadores vivos que aun no votaron.", votante, objetivo, boton);
    }

    private VBox crearAccionSheriff() {
        ComboBox<Jugador> sheriff = comboJugadores(sheriffsVivos());
        Button boton = botonAccion("Revelarse");
        boton.setOnAction(e -> ejecutarConSeleccion("Elegi al Sheriff.", true, () -> {
            partida.revelarSheriff(sheriff.getValue());
            return "Sheriff revelado.";
        }, sheriff));
        return accion("Sheriff", "Solo el Sheriff puede revelarse durante el dia.", sheriff, null, boton);
    }

    private VBox accion(String titulo, String ayuda, ComboBox<Jugador> primero, ComboBox<Jugador> segundo, Button boton) {
        VBox caja = new VBox(10);
        caja.getStyleClass().add("action-card");
        Label t = new Label(titulo);
        t.getStyleClass().add("action-title");
        Label a = new Label(ayuda);
        a.getStyleClass().add("muted-label");
        a.setWrapText(true);
        caja.getChildren().addAll(t, a, primero);
        if (segundo != null) {
            caja.getChildren().add(segundo);
        }
        caja.getChildren().add(boton);
        return caja;
    }

    private VBox panel(String titulo) {
        VBox panel = new VBox(14);
        panel.getStyleClass().add("panel");
        panel.setPadding(new Insets(18));
        Label label = new Label(titulo);
        label.getStyleClass().add("panel-title");
        panel.getChildren().add(label);
        return panel;
    }

    private ComboBox<Jugador> comboJugadores(List<Jugador> jugadores) {
        ComboBox<Jugador> combo = new ComboBox<>();
        combo.getItems().addAll(jugadores);
        combo.getStyleClass().add("combo");
        combo.setMaxWidth(Double.MAX_VALUE);
        combo.setPromptText(jugadores.isEmpty() ? "Sin opciones" : "Seleccionar jugador");
        combo.setConverter(new StringConverter<>() {
            @Override
            public String toString(Jugador jugador) {
                return jugador == null ? "" : jugador.nombre();
            }

            @Override
            public Jugador fromString(String string) {
                return null;
            }
        });
        return combo;
    }

    private Button botonAccion(String texto) {
        Button boton = new Button(texto);
        boton.getStyleClass().add("secondary-button");
        boton.setMaxWidth(Double.MAX_VALUE);
        return boton;
    }

    private List<Jugador> vivos() {
        return partida.jugadores().todos().stream().filter(Jugador::estaVivo).toList();
    }

    private List<Jugador> vivosQueNoVotaron() {
        Map<Jugador, Jugador> votos = partida.votosRegistrados();
        return vivos().stream().filter(jugador -> !votos.containsKey(jugador)).toList();
    }

    private List<Jugador> mafiososVivosQueNoVotaron() {
        Map<Jugador, Jugador> votos = partida.votosRegistrados();
        return mafiososVivos().stream().filter(jugador -> !votos.containsKey(jugador)).toList();
    }

    private List<Jugador> mafiososVivos() {
        return vivos().stream().filter(this::esMafioso).toList();
    }

    private List<Jugador> vivosNoMafiosos() {
        return vivos().stream().filter(jugador -> !esMafioso(jugador)).toList();
    }

    private List<Jugador> investigadoresVivos() {
        return vivos().stream().filter(jugador -> rolDe(jugador).equals("Detective") || rolDe(jugador).equals("Sheriff")).toList();
    }

    private List<Jugador> medicosVivos() {
        return vivos().stream().filter(jugador -> rolDe(jugador).equals("Medico") || rolDe(jugador).equals("Médico") || rolDe(jugador).equals("MÃ©dico")).toList();
    }

    private List<Jugador> sheriffsVivos() {
        return vivos().stream().filter(jugador -> rolDe(jugador).equals("Sheriff")).toList();
    }

    private boolean esMafioso(Jugador jugador) {
        String rol = rolDe(jugador);
        return rol.equals("Mafioso") || rol.equals("Padrino");
    }

    private boolean esNoche() {
        return partida.faseActual() instanceof FaseNocturna;
    }

    private String rolDe(Jugador jugador) {
        return jugador.cartaVistaPor(jugador).descripcion();
    }

    private String rutaImagenPara(String rol) {
        return switch (rol) {
            case "Ciudadano" -> "/ciudadano.png";
            case "Mafioso" -> "/mafioso.png";
            case "Detective" -> "/detective.png";
            case "Medico", "Médico", "MÃ©dico" -> "/medico.png";
            case "Padrino" -> "/padrino.png";
            case "Sheriff" -> "/sheriff.png";
            default -> "/ciudadano.png";
        };
    }

    private void iniciarTimer(Label tiempo) {
        actualizarTimer(tiempo);
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarTimer(tiempo)));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void actualizarTimer(Label tiempo) {
        int restantes = app.segundosRestantes();
        tiempo.setText("Tiempo: " + restantes + "s");
        if (restantes <= 0 && !partida.terminada()) {
            ejecutarResolver();
        }
    }

    private void detenerTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void ejecutarResolver() {
        detenerTimer();
        try {
            partida.resolverFaseActual();
            app.reiniciarTimerDeFase();
            SonidosJuego.resolver();
            app.mostrarVistaEstadoPartida();
        } catch (RuntimeException error) {
            SonidosJuego.error();
            mensaje.setText(error.getMessage());
            mensaje.getStyleClass().setAll("error-label");
        }
    }

    @SafeVarargs
    private final void ejecutarConSeleccion(String errorSeleccion, boolean refrescar, AccionSeleccion accion, ComboBox<Jugador>... combos) {
        SonidosJuego.click();
        for (ComboBox<Jugador> combo : combos) {
            if (combo.getValue() == null) {
                SonidosJuego.error();
                mensaje.setText(errorSeleccion);
                mensaje.getStyleClass().setAll("error-label");
                return;
            }
        }
        try {
            String texto = accion.ejecutar();
            SonidosJuego.ok();
            if (refrescar) {
                app.mostrarVistaEstadoPartida();
            } else {
                mensaje.setText(texto);
                mensaje.getStyleClass().setAll("info-label");
            }
        } catch (RuntimeException error) {
            SonidosJuego.error();
            mensaje.setText(error.getMessage());
            mensaje.getStyleClass().setAll("error-label");
        }
    }

    private HBox crearSeparador() {
        HBox separador = new HBox();
        HBox.setHgrow(separador, Priority.ALWAYS);
        return separador;
    }

    @FunctionalInterface
    private interface AccionSeleccion {
        String ejecutar();
    }
}

