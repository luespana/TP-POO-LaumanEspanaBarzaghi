package org.example.controlador;

import org.example.app.GamePanel;
import org.example.app.GameWindow;
import org.example.modelo.Juego;

import javax.swing.*;

public class ControladorJuego {
    private final Juego juego;
    private GamePanel gamePanel;
    private GameWindow window;
    private volatile boolean running = false;

    public ControladorJuego() {
        this.juego = new Juego();
    }

    public void iniciarJuego() {
        SwingUtilities.invokeLater(() -> {
            gamePanel = new GamePanel(juego, Juego.WIDTH, Juego.HEIGHT);
            window = new GameWindow("Space Invaders", gamePanel, Juego.WIDTH, Juego.HEIGHT);
            window.setVisible(true);
            gamePanel.requestFocusInWindow();
        });

        juego.inicializarPartida();
        iniciarBucle();
    }

    private void iniciarBucle() {
        running = true;
        final double targetFps = 60.0;
        final double frameTime = 1.0 / targetFps;
        long last = System.nanoTime();
        while (running) {
            long now = System.nanoTime();
            double dt = (now - last) / 1_000_000_000.0;
            last = now;

            if (gamePanel != null) {
                juego.update(dt, gamePanel.getPressedKeys());
                gamePanel.repaint();
            }

            long sleepNanos = (long) Math.max(0, (frameTime - dt) * 1_000_000_000L);
            try {
                Thread.sleep(sleepNanos / 1_000_000, (int) (sleepNanos % 1_000_000));
            } catch (InterruptedException ignored) { }
        }
    }
}
