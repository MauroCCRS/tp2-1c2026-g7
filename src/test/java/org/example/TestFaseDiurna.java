package org.example;

import org.example.model.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestFaseDiurna {

    private Jugador ciudadano(String nombre) {
        return new Jugador(nombre, new Ciudadano());
    }

    @Test
    public void elMasVotadoEsEliminadoYQuedaRegistrado() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");
        Jugador caro = ciudadano("Caro");

        FaseDiurna fase = new FaseDiurna(1);
        fase.nominar(ana);
        fase.nominar(beto);
        fase.votar(ana, beto);
        fase.votar(caro, beto);
        fase.votar(beto, ana);

        RegistroRonda registro = fase.resolver();

        assertFalse(beto.estaVivo());
        assertTrue(registro.describir().contains("Beto"));
    }

    @Test
    public void enEmpateConSinEliminacionNoMuereNadie() {
        Jugador ana = ciudadano("Ana");
        Jugador beto = ciudadano("Beto");

        FaseDiurna fase = new FaseDiurna(1);
        fase.nominar(ana);
        fase.nominar(beto);
        fase.votar(ana, beto);
        fase.votar(beto, ana);

        RegistroRonda registro = fase.resolver();

        assertTrue(ana.estaVivo());
        assertTrue(beto.estaVivo());
        assertTrue(registro.describir().toLowerCase().contains("nadie"));
    }

    @Test
    public void sinVotosNoMuereNadie() {
        FaseDiurna fase = new FaseDiurna(1);

        RegistroRonda registro = fase.resolver();

        assertTrue(registro.describir().toLowerCase().contains("nadie"));
    }
}