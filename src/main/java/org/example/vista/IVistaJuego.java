package org.example.vista;

import java.awt.Component;
import java.util.Set;
import java.util.function.Consumer;
import java.awt.Graphics2D;

/**
 * Interfaz que define los métodos necesarios para la vista del juego.
 * Permite al controlador interactuar con la vista sin conocer su implementación concreta.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public interface IVistaJuego {
    /**
     * Obtiene el conjunto de teclas actualmente presionadas.
     * 
     * @return Conjunto de códigos de teclas presionadas
     */
    Set<Integer> getPressedKeys();
    
    /**
     * Establece el callback de renderizado que será llamado cuando la vista necesite renderizar.
     * 
     * @param callback Función que recibe un Graphics2D para renderizar el juego
     */
    void setRenderCallback(Consumer<Graphics2D> callback);
    
    /**
     * Solicita que la vista se repinte.
     */
    void repaint();
    
    /**
     * Solicita el foco para la vista.
     */
    void requestFocusInWindow();
    
    /**
     * Obtiene el componente como Component para poder agregarlo a contenedores Swing.
     * 
     * @return El componente de la vista
     */
    Component getComponent();
}

