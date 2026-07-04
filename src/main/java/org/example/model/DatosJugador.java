package org.example.model;

import java.util.Optional;

public record DatosJugador(
        String nombre,
        boolean vivo,
        String descripcionCarta,
        boolean yaVoto,
        Optional<String> rutaImagenVisible) {
}
