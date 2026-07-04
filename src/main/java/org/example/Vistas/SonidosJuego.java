package org.example.Vistas;

import javafx.scene.media.AudioClip;

public final class SonidosJuego {
    private static final AudioClip CLICK = cargar("/sounds/click.wav");
    private static final AudioClip OK = cargar("/sounds/success.wav");
    private static final AudioClip ERROR = cargar("/sounds/error.wav");
    private static final AudioClip RESOLVER = cargar("/sounds/resolve.wav");

    private SonidosJuego() { }

    public static void click() {
        reproducir(CLICK);
    }

    public static void ok() {
        reproducir(OK);
    }

    public static void error() {
        reproducir(ERROR);
    }

    public static void resolver() {
        reproducir(RESOLVER);
    }

    private static AudioClip cargar(String ruta) {
        try {
            return new AudioClip(App.recurso(ruta));
        } catch (RuntimeException error) {
            return null;
        }
    }

    private static void reproducir(AudioClip clip) {
        if (clip != null) {
            clip.play(0.45);
        }
    }
}
