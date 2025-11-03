package org.example.modelo;

public class JugadorEnJuego {
    private int puntaje;
    private int vidas;

    public JugadorEnJuego() {
        this.puntaje = 0;
        this.vidas = 3;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void agregarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    public int getVidas() {
        return vidas;
    }

    public void perderVida() {
        if (vidas > 0) vidas--;
    }

    public boolean sinVidas() { return vidas <= 0; }
}
