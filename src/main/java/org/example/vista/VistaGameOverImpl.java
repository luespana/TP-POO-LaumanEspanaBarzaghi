package org.example.vista;

import org.example.app.GameOverPanel;

import java.awt.Component;
import java.awt.event.ActionListener;

/**
 * Implementación concreta de IVistaGameOver que envuelve GameOverPanel.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
class VistaGameOverImpl implements IVistaGameOver {
    private final GameOverPanel gameOverPanel;
    
    VistaGameOverImpl(int puntuacion, int nivelesSuperados) {
        this.gameOverPanel = new GameOverPanel(puntuacion, nivelesSuperados);
    }
    
    @Override
    public void agregarListenerVolverMenu(ActionListener listener) {
        gameOverPanel.agregarListenerVolverMenu(listener);
    }
    
    @Override
    public void agregarListenerNuevaPartida(ActionListener listener) {
        gameOverPanel.agregarListenerNuevaPartida(listener);
    }
    
    @Override
    public Component getComponent() {
        return gameOverPanel;
    }
}

