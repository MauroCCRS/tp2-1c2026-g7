package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestPartidaPresentacion {

    private Jugador mafioso(String nombre) {
        return new Jugador(nombre, new Mafioso());
    }

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    private Partida partidaCon(Jugador... jugadores) {
        Jugadores mesa = new Jugadores();
        for (Jugador jugador : jugadores) {
            mesa.agregar(jugador);
        }
        return new Partidas().clasica(mesa);
    }

    @Test
    void describeLaFaseNocturnaParaLaVistaSinExponerElModeloInterno() {
        Jugador mafioso = mafioso("Mafioso");
        Jugador ciudadano = ciudadano("Ana");
        Partida partida = partidaCon(mafioso, ciudadano, ciudadano("Beto"));

        assertEquals("Nocturna-1", partida.claveFaseActual());
        assertEquals("Nocturna - Ronda 1", partida.descripcionFaseActual());
        assertEquals("screen-night", partida.estiloPantallaFase());
        assertEquals("phase-night", partida.estiloEtiquetaFase());
        assertEquals("Votos de mafia", partida.tituloConteo());
        assertTrue(partida.avisoConteo().isEmpty());
        assertEquals("Chat nocturno", partida.tituloChat());
        assertEquals(List.of(mafioso), partida.autoresChat());
        assertTrue(partida.ayudaChat().contains("mafiosos"));
        assertTrue(partida.rutaImagenVisiblePara(mafioso).isPresent());
        assertTrue(partida.rutaImagenVisiblePara(ciudadano).isEmpty());
    }

    @Test
    void describeLaFaseDiurnaLuegoDeResolverUnaNocheSinVictima() {
        Jugador mafioso = mafioso("Mafioso");
        Jugador ciudadano = ciudadano("Ana");
        Partida partida = partidaCon(mafioso, ciudadano, ciudadano("Beto"));

        partida.resolverFaseActual();

        assertEquals("Diurna-1", partida.claveFaseActual());
        assertEquals("Diurna - Ronda 1", partida.descripcionFaseActual());
        assertEquals("screen-day", partida.estiloPantallaFase());
        assertEquals("phase-day", partida.estiloEtiquetaFase());
        assertEquals("Conteo diurno", partida.tituloConteo());
        assertTrue(partida.avisoConteo().isPresent());
        assertEquals("Chat del dia", partida.tituloChat());
        assertEquals(List.of(mafioso, ciudadano), partida.autoresChat().subList(0, 2));
        assertTrue(partida.ayudaChat().contains("dia"));
    }

    @Test
    void agregaAccionesSegunLaFaseActual() {
        Partida partida = partidaCon(mafioso("Mafioso"), ciudadano("Ana"), ciudadano("Beto"));
        AccionesRegistradas acciones = new AccionesRegistradas();

        partida.agregarAccionesDeFase(acciones);
        partida.resolverFaseActual();
        partida.agregarAccionesDeFase(acciones);

        assertEquals(1, acciones.nocturnas);
        assertEquals(1, acciones.diurnas);
    }

    @Test
    void armaDatosDeJugadoresConCartaVisibleEstadoDeVidaVotoEImagen() {
        Jugador mafioso = mafioso("Mafioso");
        Jugador ciudadano = ciudadano("Ana");
        Partida partida = partidaCon(mafioso, ciudadano, ciudadano("Beto"));

        partida.registrarVotoMafia(mafioso, ciudadano);

        DatosJugador datosMafioso = datosDe(partida, "Mafioso");
        DatosJugador datosCiudadano = datosDe(partida, "Ana");

        assertEquals("Mafioso", datosMafioso.descripcionCarta());
        assertTrue(datosMafioso.vivo());
        assertTrue(datosMafioso.yaVoto());
        assertTrue(datosMafioso.rutaImagenVisible().isPresent());
        assertEquals("Ciudadano", datosCiudadano.descripcionCarta());
        assertFalse(datosCiudadano.yaVoto());
        assertTrue(datosCiudadano.rutaImagenVisible().isEmpty());
    }

    @Test
    void unSheriffReveladoMuestraSuImagenDuranteElDia() {
        Jugador mafioso = mafioso("Mafioso");
        Jugador sheriff = new Jugador("Sheriff", new Sheriff());
        Partida partida = partidaCon(mafioso, sheriff, ciudadano("Ana"), ciudadano("Beto"));

        partida.resolverFaseActual();
        partida.revelarSheriff(sheriff);

        assertTrue(partida.sheriffRevelado(sheriff));
        assertTrue(partida.rutaImagenVisiblePara(sheriff).isPresent());
        assertTrue(datosDe(partida, "Sheriff").rutaImagenVisible().isPresent());
    }

    private DatosJugador datosDe(Partida partida, String nombre) {
        return partida.jugadoresEnMesa().stream()
                .filter(datos -> datos.nombre().equals(nombre))
                .findFirst()
                .orElseThrow();
    }

    private static class AccionesRegistradas implements AccionesPorFase {
        private int nocturnas;
        private int diurnas;

        @Override
        public void agregarAccionesNocturnas() {
            nocturnas++;
        }

        @Override
        public void agregarAccionesDiurnas() {
            diurnas++;
        }
    }
}
