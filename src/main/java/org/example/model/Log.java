package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Log {
    private static final List<String> eventos = new ArrayList<>();

    public static void agregar(String clase, String accion, String detalle) {
        String evento = String.format("%s -> %s : %s", clase, accion, detalle);
        eventos.add(evento);
    }

    public static void  mostrar_eventos() {
        System.out.println("\n===");
        for (String evento : eventos) {
            System.out.println(evento);
        }
        System.out.println("===\n");

    }

    public static void limpiar() {
        eventos.clear();
    }
}

