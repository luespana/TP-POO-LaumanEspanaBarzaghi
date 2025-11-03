package org.example.app;

import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal de la aplicación.
 * 
 * <p>Extiende JFrame y configura la ventana del juego con:
 * <ul>
 *   <li>Tamaño fijo (no redimensionable)</li>
 *   <li>Centrado en pantalla</li>
 *   <li>Cierre por defecto al salir</li>
 * </ul>
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class GameWindow extends JFrame {
    public GameWindow(String title, JPanel initialPanel, int width, int height) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIgnoreRepaint(true);
        getContentPane().setLayout(new BorderLayout());
        if (initialPanel != null) {
            getContentPane().add(initialPanel, BorderLayout.CENTER);
        }
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);
    }
}

