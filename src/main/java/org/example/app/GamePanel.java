package org.example.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
/**
 * Consumer para el renderizado del juego.
 * Es decir, una función que recibe un Graphics2D y lo renderiza.
 */
import java.util.function.Consumer;

/**
 * Panel principal donde se renderiza y se controla el juego.
 * 
 * <p>Funcionalidades:
 * <ul>
 *   <li>Captura de entrada del teclado (movimiento y disparos)</li>
 *   <li>Renderizado del juego mediante callback proporcionado por el controlador</li>
 *   <li>Gestión de teclas presionadas usando ConcurrentHashMap para thread-safety</li>
 * </ul>
 * 
 * <p>Este panel respeta el patrón MVC al no tener dependencias directas del modelo.
 * El renderizado se delega al controlador mediante un callback.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class GamePanel extends JPanel {
    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();
    private Consumer<Graphics2D> renderCallback;

    public GamePanel(int width, int height) {
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

    /**
     * Establece el callback de renderizado que será llamado desde paintComponent.
     * Este método permite al controlador proporcionar la lógica de renderizado
     * sin que la vista tenga dependencia del modelo.
     * 
     * @param callback Función que recibe un Graphics2D para renderizar el juego
     */
    public void setRenderCallback(Consumer<Graphics2D> callback) {
        this.renderCallback = callback;
    }

    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (renderCallback != null) {
            /*el parametro g es el Graphics2D que se le pasa al callback*/
            renderCallback.accept((Graphics2D) g);
        }
    }
}

