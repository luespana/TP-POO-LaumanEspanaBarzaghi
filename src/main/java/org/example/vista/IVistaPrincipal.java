package org.example.vista;

import java.awt.Component;

/**
 * Interfaz que define los métodos necesarios para la ventana principal de la aplicación.
 * Permite al controlador interactuar con la ventana sin conocer su implementación concreta.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public interface IVistaPrincipal {
    /**
     * Establece el componente que se mostrará en el contenido de la ventana.
     * 
     * @param component Componente a mostrar
     */
    void setContenido(Component component);
    
    /**
     * Hace visible la ventana.
     * 
     * @param visible true para hacer visible, false para ocultar
     */
    void setVisible(boolean visible);
    
    /**
     * Obtiene el componente de la ventana como Component.
     * 
     * @return El componente de la ventana
     */
    Component getComponent();
}

