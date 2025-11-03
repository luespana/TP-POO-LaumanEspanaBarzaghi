package org.example.modelo;

/**
 * Representa una entrada individual en el ranking del juego.
 * 
 * <p>Cada entrada contiene:
 * <ul>
 *   <li>Nombre del jugador</li>
 *   <li>Cantidad de niveles superados</li>
 *   <li>Puntuación obtenida</li>
 * </ul>
 * 
 * <p>Implementa Comparable para permitir ordenamiento por puntuación descendente.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class EntradaRanking implements Comparable<EntradaRanking> {
    private String nombre;
    private int nivelesSuperados;
    private int puntuacion;

    /**
     * Constructor que crea una nueva entrada de ranking.
     * 
     * @param nombre Nombre del jugador
     * @param nivelesSuperados Cantidad de niveles superados
     * @param puntuacion Puntuación obtenida
     */
    public EntradaRanking(String nombre, int nivelesSuperados, int puntuacion) {
        this.nombre = nombre;
        this.nivelesSuperados = nivelesSuperados;
        this.puntuacion = puntuacion;
    }

    /**
     * Obtiene el nombre del jugador.
     * 
     * @return Nombre del jugador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene la cantidad de niveles superados.
     * 
     * @return Número de niveles superados
     */
    public int getNivelesSuperados() {
        return nivelesSuperados;
    }

    /**
     * Obtiene la puntuación obtenida.
     * 
     * @return Puntuación
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Compara esta entrada con otra para ordenamiento.
     * 
     * <p>Ordena por puntuación descendente (mayor puntuación primero).
     * 
     * @param otra Otra entrada de ranking a comparar
     * @return Valor negativo si esta entrada tiene mayor puntuación, positivo si menor, 0 si igual
     */
    @Override
    public int compareTo(EntradaRanking otra) {
        // Ordenar por puntuación descendente (mayor a menor)
        return Integer.compare(otra.puntuacion, this.puntuacion);
    }
}

