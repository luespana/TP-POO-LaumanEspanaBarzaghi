package org.example.vista;

import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Interfaz que define los métodos necesarios para la vista del menú principal.
 * Permite al controlador interactuar con la vista sin conocer su implementación concreta.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public interface IVistaMenu {
    /**
     * Agrega un listener para el evento de comenzar nueva partida.
     * 
     * @param listener Listener que se ejecutará cuando se presione el botón de nueva partida
     */
    void agregarListenerNuevaPartida(ActionListener listener);
    
    /**
     * Agrega un listener para el evento de ver ranking.
     * 
     * @param listener Listener que se ejecutará cuando se presione el botón de ver ranking
     */
    void agregarListenerVerRanking(ActionListener listener);
    
    /**
     * Obtiene el componente como Component para poder agregarlo a contenedores Swing.
     * 
     * @return El componente de la vista
     */
    Component getComponent();
}

