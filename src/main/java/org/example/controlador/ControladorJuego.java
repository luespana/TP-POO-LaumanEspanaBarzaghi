package org.example.controlador;

import org.example.modelo.Juego;
import org.example.vista.*;
import javax.swing.*;

/**
 * Controlador principal que gestiona el flujo de la aplicación y coordina
 * entre el modelo (Juego) y la vista (paneles de interfaz).
 * 
 * <p>Responsabilidades:
 * <ul>
 *   <li>Gestión del ciclo de vida de la aplicación</li>
 *   <li>Coordinación entre diferentes paneles (menú, juego, ranking, game over)</li>
 *   <li>Bucle de juego principal con actualización a 60 FPS</li>
 *   <li>Detección de fin de partida y procesamiento del ranking</li>
 *   <li>Gestión de hilos para el bucle de juego</li>
 * </ul>
 * 
 * <p>Utiliza el patrón MVC (Modelo-Vista-Controlador) donde:
 * <ul>
 *   <li>Modelo: Clase Juego que contiene la lógica del juego</li>
 *   <li>Vista: Interfaces (IVistaJuego, IVistaMenu, etc.) que abstraen las implementaciones concretas</li>
 *   <li>Controlador: Esta clase que coordina todo sin conocer las clases concretas de vista</li>
 * </ul>
 * 
 * <p>El controlador respeta el principio de inversión de dependencias al trabajar exclusivamente
 * con interfaces de vista, obtenidas a través de VistaFactory. Esto permite cambiar las implementaciones
 * de vista sin modificar el controlador.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class ControladorJuego {
    private final Juego juego;
    private IVistaJuego vistaJuego;
    private IVistaMenu vistaMenu;
    private IVistaPrincipal vistaPrincipal;
    private volatile boolean running = false;
    private boolean enJuego = false;
    private Thread gameThread;
    private boolean gameOverProcesado = false;

    public ControladorJuego() {
        this.juego = new Juego();
    }

    public void iniciar() {
        SwingUtilities.invokeLater(() -> {
            vistaPrincipal = VistaFactory.crearVistaPrincipal("Space Invaders", Juego.WIDTH, Juego.HEIGHT);
            mostrarMenu();
            vistaPrincipal.setVisible(true);
        });
    }

    private void mostrarMenu() {
        vistaMenu = VistaFactory.crearVistaMenu();
        vistaPrincipal.setContenido(vistaMenu.getComponent());
        
        vistaMenu.agregarListenerNuevaPartida(e -> comenzarNuevaPartida());
        vistaMenu.agregarListenerVerRanking(e -> verRanking());
    }

    private void comenzarNuevaPartida() {
        // Detener el bucle anterior si existe
        detenerBucle();
        gameOverProcesado = false;
        
        SwingUtilities.invokeLater(() -> {
            vistaJuego = VistaFactory.crearVistaJuego(Juego.WIDTH, Juego.HEIGHT);
            vistaJuego.setProveedorEstado(() -> juego.obtenerEstadoParaRenderizado());
            vistaPrincipal.setContenido(vistaJuego.getComponent());
            vistaJuego.requestFocusInWindow();
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
        IVistaRanking vistaRanking = VistaFactory.crearVistaRanking(juego.getRanking());
        vistaRanking.agregarListenerVolver(e -> mostrarMenu());
        
        vistaPrincipal.setContenido(vistaRanking.getComponent());
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

                if (vistaJuego != null) {
                    juego.update(dt, vistaJuego.getPressedKeys());
                    SwingUtilities.invokeLater(() -> vistaJuego.repaint());
                    
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
                    vistaPrincipal.getComponent(),
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
            IVistaGameOver vistaGameOver = VistaFactory.crearVistaGameOver(puntuacion, nivelesSuperados);
            vistaGameOver.agregarListenerVolverMenu(e -> mostrarMenu());
            vistaGameOver.agregarListenerNuevaPartida(e -> comenzarNuevaPartida());
            
            vistaPrincipal.setContenido(vistaGameOver.getComponent());
        });
    }
}
