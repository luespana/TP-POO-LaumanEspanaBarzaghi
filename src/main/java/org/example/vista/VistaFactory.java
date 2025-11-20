package org.example.vista;

import org.example.modelo.Ranking;

/**
 * Factory para crear instancias de las vistas concretas.
 * Este factory permite al controlador obtener vistas sin conocer las clases concretas.
 * 
 * <p>Este patrón permite desacoplar el controlador de las implementaciones concretas de las vistas,
 * respetando el principio de inversión de dependencias del patrón MVC.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class VistaFactory {
    
    /**
     * Crea una nueva instancia de la vista principal (ventana).
     * 
     * @param titulo Título de la ventana
     * @param ancho Ancho de la ventana
     * @param alto Alto de la ventana
     * @return Instancia de IVistaPrincipal
     */
    public static IVistaPrincipal crearVistaPrincipal(String titulo, int ancho, int alto) {
        return new VistaPrincipalImpl(titulo, ancho, alto);
    }
    
    /**
     * Crea una nueva instancia de la vista del menú.
     * 
     * @return Instancia de IVistaMenu
     */
    public static IVistaMenu crearVistaMenu() {
        return new VistaMenuImpl();
    }
    
    /**
     * Crea una nueva instancia de la vista del juego.
     * 
     * @param ancho Ancho del panel de juego
     * @param alto Alto del panel de juego
     * @return Instancia de IVistaJuego
     */
    public static IVistaJuego crearVistaJuego(int ancho, int alto) {
        return new VistaJuegoImpl(ancho, alto);
    }
    
    /**
     * Crea una nueva instancia de la vista de Game Over.
     * 
     * @param puntuacion Puntuación obtenida
     * @param nivelesSuperados Niveles superados
     * @return Instancia de IVistaGameOver
     */
    public static IVistaGameOver crearVistaGameOver(int puntuacion, int nivelesSuperados) {
        return new VistaGameOverImpl(puntuacion, nivelesSuperados);
    }
    
    /**
     * Crea una nueva instancia de la vista del ranking.
     * 
     * @param ranking Objeto Ranking con los datos a mostrar
     * @return Instancia de IVistaRanking
     */
    public static IVistaRanking crearVistaRanking(Ranking ranking) {
        return new VistaRankingImpl(ranking);
    }
}

