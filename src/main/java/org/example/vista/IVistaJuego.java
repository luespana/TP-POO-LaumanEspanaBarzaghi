package org.example.vista;

import org.example.modelo.EstadoJuegoDTO;
import java.awt.Component;
import java.util.Set;

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
     * Establece el proveedor de estado del juego.
     * La vista llamará a este proveedor cuando necesite renderizar para obtener
     * los datos actuales del juego.
     * 
     * @param proveedorEstado Función que retorna el estado actual del juego
     */
    void setProveedorEstado(java.util.function.Supplier<EstadoJuegoDTO> proveedorEstado);
    
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

