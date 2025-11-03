package org.example.app;

import org.example.modelo.Juego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Panel principal donde se renderiza y se controla el juego.
 * 
 * <p>Funcionalidades:
 * <ul>
 *   <li>Captura de entrada del teclado (movimiento y disparos)</li>
 *   <li>Renderizado del juego mediante delegación a la clase Juego</li>
 *   <li>Gestión de teclas presionadas usando ConcurrentHashMap para thread-safety</li>
 * </ul>
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class GamePanel extends JPanel {
    private final Juego juego;
    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();

    public GamePanel(Juego juego, int width, int height) {
        this.juego = juego;
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setDoubleBuffered(true);
        setupKeyHandling();
    }

    private void setupKeyHandling() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                pressedKeys.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                pressedKeys.remove(e.getKeyCode());
            }
        });
    }

    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        juego.render((Graphics2D) g);
    }
}

