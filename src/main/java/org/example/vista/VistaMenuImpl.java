package org.example.vista;

import org.example.app.MenuPanel;

import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Implementación concreta de IVistaMenu que envuelve MenuPanel.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
class VistaMenuImpl implements IVistaMenu {
    private final MenuPanel menuPanel;
    
    VistaMenuImpl() {
        this.menuPanel = new MenuPanel();
    }
    
    @Override
    public void agregarListenerNuevaPartida(ActionListener listener) {
        menuPanel.agregarListenerNuevaPartida(listener);
    }
    
    @Override
    public void agregarListenerVerRanking(ActionListener listener) {
        menuPanel.agregarListenerVerRanking(listener);
    }
    
    @Override
    public Component getComponent() {
        return menuPanel;
    }
}

