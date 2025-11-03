package org.example.modelo;

/**
 * Representa una partida individual del juego.
 * 
 * <p>Rastrea el tiempo de inicio de la partida para poder calcular
 * la duración total cuando termine.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class Partida {
    private long inicioMillis;

    /**
     * Marca el inicio de la partida registrando el tiempo actual.
     */
    public void iniciarPartida() {
        inicioMillis = System.currentTimeMillis();
    }

    /**
     * Calcula la duración de la partida desde que se inició.
     * 
     * @return Duración en milisegundos desde el inicio
     */
    public long getDuracionMillis() {
        return System.currentTimeMillis() - inicioMillis;
    }
}
