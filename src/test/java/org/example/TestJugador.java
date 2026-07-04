package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestJugador {

    @Test
    void unJugadorPuedeSaberSuBando() {
        Jugador jugador = new Jugador("Ana", new Mafioso());
        assertTrue(jugador.perteneceA(new BandoMafia()));
        assertFalse(jugador.perteneceA(new BandoCiudadano()));
    }

    @Test
    void unJugadorRecienCreadoEstaVivo() {
        Jugador jugador = new Jugador("Ana", new Ciudadano());
        assertTrue(jugador.estaVivo());
    }

    @Test
    void unJugadorEliminadoDejaDeEstarVivo() {
        Jugador jugador = new Jugador("Ana", new Ciudadano());
        jugador.eliminar();
        assertFalse(jugador.estaVivo());
    }
    @Test
    void eliminarDosVecesAUnJugadorNoRompeElEstado() {
        Jugador jugador = new Jugador("Ana", new Ciudadano());

        jugador.eliminar();
        jugador.eliminar();

        assertFalse(jugador.estaVivo());
    }

    @Test
    void unJugadorVeSuPropiaCartaRevelada() {
        Jugador jugador = new Jugador("Ana", new Detective());

        assertEquals("Detective", jugador.cartaVistaPor(jugador).descripcion());
    }

    @Test
    void losMafiososSeReconocenEntreSi() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());
        Jugador padrino = new Jugador("Padrino", new Padrino());

        assertEquals("Padrino", padrino.cartaVistaPor(mafioso).descripcion());
    }

    @Test
    void unCiudadanoNoVeLaCartaAjenaSinRevelar() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador detective = new Jugador("Beto", new Detective());

        assertEquals("Carta oculta", detective.cartaVistaPor(ciudadano).descripcion());
    }

    @Test
    void unMafiosoVivoMuestraSuImagenDuranteLaNoche() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        assertTrue(mafioso.rutaImagenNocturnaVisible().isPresent());
    }

    @Test
    void unCiudadanoNoMuestraImagenDuranteLaNoche() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        assertTrue(ciudadano.rutaImagenNocturnaVisible().isEmpty());
    }

    @Test
    void unMafiosoEliminadoNoMuestraImagenDuranteLaNoche() {
        Jugador mafioso = new Jugador("Mafioso", new Mafioso());

        mafioso.eliminar();

        assertTrue(mafioso.rutaImagenNocturnaVisible().isEmpty());
    }

    @Test
    void soloUnRolMafiosoPuedeCrearVotosDeMafia() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());
        Jugador objetivo = new Jugador("Beto", new Ciudadano());

        assertThrows(VotacionInvalidaException.class, () -> ciudadano.crearVotoMafia(objetivo));
    }

    @Test
    void unRolSinInvestigacionNoTieneResultadoParaRevelar() {
        Jugador ciudadano = new Jugador("Ana", new Ciudadano());

        assertThrows(InvestigacionInvalidaException.class, ciudadano::resultadoInvestigacion);
    }
}
