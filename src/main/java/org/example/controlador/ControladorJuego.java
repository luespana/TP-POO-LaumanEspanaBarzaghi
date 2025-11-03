package org.example.controlador;

import org.example.app.GamePanel;
import org.example.app.GameOverPanel;
import org.example.app.GameWindow;
import org.example.app.MenuPanel;
import org.example.app.RankingPanel;
import org.example.modelo.Juego;

import javax.swing.*;
import java.awt.*;

public class ControladorJuego {
    private final Juego juego;
    private GamePanel gamePanel;
    private MenuPanel menuPanel;
    private GameWindow window;
    private volatile boolean running = false;
    private boolean enJuego = false;
    private Thread gameThread;
    private boolean gameOverProcesado = false;

    public ControladorJuego() {
        this.juego = new Juego();
    }

    public void iniciar() {
        SwingUtilities.invokeLater(() -> {
            window = new GameWindow("Space Invaders", null, Juego.WIDTH, Juego.HEIGHT);
            mostrarMenu();
            window.setVisible(true);
        });
    }

    private void mostrarMenu() {
        menuPanel = new MenuPanel();
        window.getContentPane().removeAll();
        window.getContentPane().add(menuPanel, BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
        
        menuPanel.agregarListenerNuevaPartida(e -> comenzarNuevaPartida());
        menuPanel.agregarListenerVerRanking(e -> verRanking());
    }

    private void comenzarNuevaPartida() {
        // Detener el bucle anterior si existe
        detenerBucle();
        gameOverProcesado = false;
        
        SwingUtilities.invokeLater(() -> {
            gamePanel = new GamePanel(juego, Juego.WIDTH, Juego.HEIGHT);
            window.getContentPane().removeAll();
            window.getContentPane().add(gamePanel, BorderLayout.CENTER);
            window.revalidate();
            window.repaint();
            gamePanel.requestFocusInWindow();
        });

        juego.inicializarPartida();
        enJuego = true;
        iniciarBucle();
    }
    
    private void detenerBucle() {
        running = false;
        enJuego = false;
        if (gameThread != null && gameThread.isAlive()) {
            try {
                gameThread.interrupt();
                gameThread.join(100); // Esperar hasta 100ms para que termine
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void verRanking() {
        RankingPanel rankingPanel = new RankingPanel(juego.getRanking());
        rankingPanel.agregarListenerVolver(e -> mostrarMenu());
        
        window.getContentPane().removeAll();
        window.getContentPane().add(rankingPanel, BorderLayout.CENTER);
        window.revalidate();
        window.repaint();
    }

    private void iniciarBucle() {
        // Detener cualquier bucle anterior
        if (running && gameThread != null && gameThread.isAlive()) {
            detenerBucle();
        }
        
        running = true;
        gameThread = new Thread(() -> {
            final double targetFps = 60.0;
            final double frameTime = 1.0 / targetFps;
            long last = System.nanoTime();
            while (running && enJuego) {
                long now = System.nanoTime();
                double dt = (now - last) / 1_000_000_000.0;
                last = now;

                if (gamePanel != null) {
                    juego.update(dt, gamePanel.getPressedKeys());
                    SwingUtilities.invokeLater(() -> gamePanel.repaint());
                    
                    // Verificar si el juego terminó
                    if ("GAME_OVER".equals(juego.getEstado()) && !gameOverProcesado) {
                        gameOverProcesado = true;
                        procesarGameOver();
                    }
                }

                long sleepNanos = (long) Math.max(0, (frameTime - dt) * 1_000_000_000L);
                try {
                    Thread.sleep(sleepNanos / 1_000_000, (int) (sleepNanos % 1_000_000));
                } catch (InterruptedException ignored) {
                    break;
                }
            }
        });
        gameThread.setDaemon(true);
        gameThread.start();
    }

    private void procesarGameOver() {
        SwingUtilities.invokeLater(() -> {
            int puntuacion = juego.getPuntaje();
            int nivelesSuperados = juego.getNivelesSuperados();
            
            // Verificar si la puntuación entra en el ranking
            if (juego.getRanking().esPuntuacionValida(puntuacion)) {
                // Solicitar nombre al usuario
                String nombre = JOptionPane.showInputDialog(
                    window,
                    "¡Felicidades! Tu puntuación está en el TOP 5.\n" +
                    "Puntuación: " + puntuacion + "\n" +
                    "Niveles superados: " + nivelesSuperados + "\n\n" +
                    "Ingresa tu nombre:",
                    "Nuevo Record",
                    JOptionPane.PLAIN_MESSAGE
                );
                
                // Si el usuario canceló o dejó vacío, usar un nombre por defecto
                if (nombre == null || nombre.trim().isEmpty()) {
                    nombre = "Jugador";
                }
                
                // Agregar al ranking
                juego.getRanking().agregarEntrada(nombre.trim(), nivelesSuperados, puntuacion);
            }
            
            // Detener el bucle del juego
            detenerBucle();
            
            // Mostrar panel de Game Over con botones
            GameOverPanel gameOverPanel = new GameOverPanel(puntuacion, nivelesSuperados);
            gameOverPanel.agregarListenerVolverMenu(e -> mostrarMenu());
            gameOverPanel.agregarListenerNuevaPartida(e -> comenzarNuevaPartida());
            
            window.getContentPane().removeAll();
            window.getContentPane().add(gameOverPanel, BorderLayout.CENTER);
            window.revalidate();
            window.repaint();
        });
    }
}
