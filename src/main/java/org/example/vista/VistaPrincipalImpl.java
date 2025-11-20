package org.example.vista;

import org.example.app.GameWindow;

import java.awt.Component;

/**
 * Implementación concreta de IVistaPrincipal que envuelve GameWindow.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
class VistaPrincipalImpl implements IVistaPrincipal {
    private final GameWindow window;
    
    VistaPrincipalImpl(String titulo, int ancho, int alto) {
        this.window = new GameWindow(titulo, null, ancho, alto);
    }
    
    @Override
    public void setContenido(Component component) {
        window.getContentPane().removeAll();
        window.getContentPane().add(component, java.awt.BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }
    
    @Override
    public void setVisible(boolean visible) {
        window.setVisible(visible);
    }
    
    @Override
    public Component getComponent() {
        return window;
    }
}

