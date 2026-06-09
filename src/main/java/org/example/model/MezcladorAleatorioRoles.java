package org.example.model;

import java.util.Collections;
import java.util.List;

public class MezcladorAleatorioRoles implements MezcladorDeRoles {

    @Override
    public void mezclar(List<Rol> roles) {
        Collections.shuffle(roles);
    }
}
