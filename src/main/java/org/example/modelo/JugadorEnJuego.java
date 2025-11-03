package org.example.modelo;

/**
 * Representa el estado del jugador durante una partida activa.
 * 
 * <p>Gestiona:
 * <ul>
 *   <li>Puntuación acumulada durante la partida</li>
 *   <li>Número de vidas restantes</li>
 * </ul>
 * 
 * <p>Se inicializa con 3 vidas y puntuación 0 al comenzar una nueva partida.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class JugadorEnJuego {
    private int puntaje;
    private int vidas;

    /**
     * Constructor que inicializa el jugador con 3 vidas y puntuación 0.
     */
    public JugadorEnJuego() {
        this.puntaje = 0;
        this.vidas = 3;
    }

    /**
     * Obtiene la puntuación actual del jugador.
     * 
     * @return Puntuación acumulada
     */
    public int getPuntaje() {
        return puntaje;
    }

    /**
     * Agrega puntos a la puntuación del jugador.
     * 
     * @param puntos Cantidad de puntos a agregar (normalmente 10 por enemigo eliminado)
     */
    public void agregarPuntos(int puntos) {
        this.puntaje += puntos;
    }

    /**
     * Obtiene el número de vidas restantes.
     * 
     * @return Vidas restantes (0 a 3)
     */
    public int getVidas() {
        return vidas;
    }

    /**
     * Reduce en 1 el número de vidas del jugador.
     * 
     * <p>No permite que las vidas sean negativas.
     */
    public void perderVida() {
        if (vidas > 0) vidas--;
    }

    /**
     * Verifica si el jugador se ha quedado sin vidas.
     * 
     * @return true si las vidas son 0 o menos, false en caso contrario
     */
    public boolean sinVidas() { return vidas <= 0; }
}
