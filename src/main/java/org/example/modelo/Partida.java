package org.example.modelo;

public class Partida {
    private long inicioMillis;

    public void iniciarPartida() {
        inicioMillis = System.currentTimeMillis();
    }

    public long getDuracionMillis() {
        return System.currentTimeMillis() - inicioMillis;
    }
}
