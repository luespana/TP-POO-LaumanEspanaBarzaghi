package org.example.vista;

import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Interfaz que define los métodos necesarios para la vista del ranking.
 * Permite al controlador interactuar con la vista sin conocer su implementación concreta.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public interface IVistaRanking {
    /**
     * Agrega un listener para el evento de volver al menú.
     * 
     * @param listener Listener que se ejecutará cuando se presione el botón de volver
     */
    void agregarListenerVolver(ActionListener listener);
    
    /**
     * Obtiene el componente como Component para poder agregarlo a contenedores Swing.
     * 
     * @return El componente de la vista
     */
    Component getComponent();
}

