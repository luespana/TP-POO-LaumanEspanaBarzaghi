package org.example.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que gestiona el sistema de ranking del juego.
 * 
 * <p>Mantiene las 5 puntuaciones más altas registradas, ordenadas de mayor a menor.
 * Cada entrada contiene el nombre del jugador, niveles superados y puntuación obtenida.
 * 
 * <p>El ranking se ordena automáticamente después de cada inserción, manteniendo
 * solo las mejores puntuaciones.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class Ranking {
    /** Número máximo de entradas que se mantienen en el ranking */
    private static final int MAX_ENTRADAS = 5;
    private List<EntradaRanking> entradas;

    /**
     * Constructor que inicializa el ranking vacío.
     */
    public Ranking() {
        this.entradas = new ArrayList<>();
    }

    /**
     * Agrega una nueva entrada al ranking y mantiene solo las top 5.
     * 
     * <p>La entrada se inserta, se ordena el ranking por puntuación descendente,
     * y se eliminan las entradas que excedan el máximo permitido.
     * 
     * @param nombre Nombre del jugador
     * @param nivelesSuperados Cantidad de niveles superados en la partida
     * @param puntuacion Puntuación obtenida
     */
    public void agregarEntrada(String nombre, int nivelesSuperados, int puntuacion) {
        entradas.add(new EntradaRanking(nombre, nivelesSuperados, puntuacion));
        Collections.sort(entradas);
        
        // Mantener solo las top 5
        if (entradas.size() > MAX_ENTRADAS) {
            entradas = entradas.subList(0, MAX_ENTRADAS);
        }
    }

    /**
     * Verifica si una puntuación es suficiente para entrar en el ranking.
     * 
     * <p>Una puntuación es válida si:
     * <ul>
     *   <li>El ranking tiene menos de 5 entradas, o</li>
     *   <li>La puntuación es mayor que la puntuación más baja del ranking</li>
     * </ul>
     * 
     * @param puntuacion Puntuación a verificar
     * @return true si la puntuación entra en el top 5, false en caso contrario
     */
    public boolean esPuntuacionValida(int puntuacion) {
        // Verifica si la puntuación es suficiente para entrar en el ranking
        if (entradas.size() < MAX_ENTRADAS) {
            return true;
        }
        // Si hay 5 entradas, verifica si la puntuación es mayor que la más baja
        return puntuacion > entradas.get(entradas.size() - 1).getPuntuacion();
    }

    /**
     * Obtiene una copia de las entradas del ranking ordenadas de mayor a menor puntuación.
     * 
     * @return Lista inmutable de las entradas del ranking (top 5)
     */
    public List<EntradaRanking> getTop5() {
        return new ArrayList<>(entradas);
    }

    /**
     * Obtiene la cantidad actual de entradas en el ranking.
     * 
     * @return Número de entradas (0 a MAX_ENTRADAS)
     */
    public int getCantidadEntradas() {
        return entradas.size();
    }
}
