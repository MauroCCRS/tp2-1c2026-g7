package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    private static final List<String> eventos = new ArrayList<>();
    private static boolean enabled = true;

    public static void log(String evento) {
        if  (enabled) {
            eventos.add(evento);
        }
    }

    public static void show() {
        System.out.println("\n===");
        for (String evento : eventos) {
            System.out.println(evento);
        }
        System.out.println("===\n");

    }

    public static List<String> eventos() {
        return new ArrayList<>(eventos);
    }

    public static void setEnabled(boolean enabled) {
        Logger.enabled = enabled;
    }

    public static void clear() {
        eventos.clear();
    }

}

