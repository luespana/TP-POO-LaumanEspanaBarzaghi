package org.example.vista;

import org.example.app.GamePanel;

import java.awt.Component;
import java.awt.Graphics2D;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Implementación concreta de IVistaJuego que envuelve GamePanel.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
class VistaJuegoImpl implements IVistaJuego {
    private final GamePanel gamePanel;
    
    VistaJuegoImpl(int ancho, int alto) {
        this.gamePanel = new GamePanel(ancho, alto);
    }
    
    @Override
    public Set<Integer> getPressedKeys() {
        return gamePanel.getPressedKeys();
    }
    
    @Override
    public void setRenderCallback(Consumer<Graphics2D> callback) {
        gamePanel.setRenderCallback(callback);
    }
    
    @Override
    public void repaint() {
        gamePanel.repaint();
    }
    
    @Override
    public void requestFocusInWindow() {
        gamePanel.requestFocusInWindow();
    }
    
    @Override
    public Component getComponent() {
        return gamePanel;
    }
}

