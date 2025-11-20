package org.example.app;

import org.example.modelo.EstadoJuegoDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;

/**
 * Panel principal donde se renderiza y se controla el juego.
 * 
 * <p>Funcionalidades:
 * <ul>
 *   <li>Captura de entrada del teclado (movimiento y disparos)</li>
 *   <li>Renderizado del juego basado en datos obtenidos del controlador</li>
 *   <li>Gestión de teclas presionadas usando ConcurrentHashMap para thread-safety</li>
 * </ul>
 * 
 * <p>Este panel respeta el patrón MVC al no tener dependencias directas del modelo.
 * Obtiene los datos del estado del juego a través del controlador y los renderiza.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class GamePanel extends JPanel {
    private final Set<Integer> pressedKeys = ConcurrentHashMap.newKeySet();
    private Supplier<EstadoJuegoDTO> proveedorEstado;

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
     * Establece el proveedor de estado que será llamado desde paintComponent.
     * Este método permite al controlador proporcionar los datos del juego
     * sin que la vista tenga dependencia del modelo.
     * 
     * @param proveedorEstado Función que retorna el estado actual del juego
     */
    public void setProveedorEstado(Supplier<EstadoJuegoDTO> proveedorEstado) {
        this.proveedorEstado = proveedorEstado;
    }

    public Set<Integer> getPressedKeys() {
        return pressedKeys;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        if (proveedorEstado == null) {
            return;
        }
        
        EstadoJuegoDTO estado = proveedorEstado.get();
        if (estado == null) {
            return;
        }
        
        renderizarEstado(g2d, estado);
    }
    
    private void renderizarEstado(Graphics2D g, EstadoJuegoDTO estado) {
        // Fondo negro
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        
        String estadoStr = estado.getEstado();
        
        if ("EN_JUEGO".equals(estadoStr)) {
            renderizarEnJuego(g, estado);
        } else if ("TRANSICION_NIVEL".equals(estadoStr)) {
            renderizarTransicionNivel(g, estado);
        } else if ("MENU".equals(estadoStr)) {
            renderizarMenu(g);
        } else if ("GAME_OVER".equals(estadoStr)) {
            renderizarGameOver(g, estado);
        }
    }
    
    private void renderizarEnJuego(Graphics2D g, EstadoJuegoDTO estado) {
        // Renderizar nave del jugador
        EstadoJuegoDTO.DatosNaveJugador naveJugador = estado.getNaveJugador();
        if (naveJugador != null) {
            renderizarNaveJugador(g, naveJugador);
        }
        
        // Renderizar enemigos
        for (EstadoJuegoDTO.DatosNaveEnemiga enemigo : estado.getEnemigos()) {
            renderizarNaveEnemiga(g, enemigo);
        }
        
        // Fondo para la franja de escudos
        int escudosY = getHeight() - 140;
        int escudosHeight = 60;
        g.setColor(new Color(20, 20, 40));
        g.fillRect(0, escudosY - 10, getWidth(), escudosHeight + 20);
        
        // Renderizar muros
        for (EstadoJuegoDTO.DatosMuro bloque : estado.getMuros()) {
            if (bloque.getHp() > 0) {
                g.setColor(new Color(0, 180, 0));
                g.fillRect(bloque.getX(), bloque.getY(), bloque.getWidth(), bloque.getHeight());
            }
        }
        
        // Renderizar proyectiles
        for (EstadoJuegoDTO.DatosProyectil proyectil : estado.getProyectiles()) {
            g.setColor(proyectil.isFromPlayer() ? Color.cyan : Color.red);
            g.fillRect((int) proyectil.getX(), (int) proyectil.getY(), 
                      proyectil.getWidth(), proyectil.getHeight());
        }
        
        // Renderizar HUD
        EstadoJuegoDTO.DatosHUD hud = estado.getHud();
        if (hud != null) {
            renderizarHUD(g, hud);
        }
    }
    
    private void renderizarNaveJugador(Graphics2D g, EstadoJuegoDTO.DatosNaveJugador datos) {
        int xInt = (int) datos.getX();
        int yInt = (int) datos.getY();
        int width = datos.getWidth();
        int height = datos.getHeight();
        
        // Cuerpo principal de la nave (triángulo)
        g.setColor(Color.green);
        int[] xPoints = {xInt + width / 2, xInt, xInt + width};
        int[] yPoints = {yInt, yInt + height, yInt + height};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // Cañón en la parte superior
        int cañonWidth = 6;
        int cañonHeight = 8;
        g.setColor(new Color(0, 200, 0));
        g.fillRect(xInt + width / 2 - cañonWidth / 2, yInt - cañonHeight, cañonWidth, cañonHeight);
        
        // Detalles del cañón
        g.setColor(new Color(0, 255, 0));
        g.fillRect(xInt + width / 2 - 2, yInt - cañonHeight, 4, cañonHeight);
    }
    
    private void renderizarNaveEnemiga(Graphics2D g, EstadoJuegoDTO.DatosNaveEnemiga datos) {
        int xInt = (int) datos.getX();
        int yInt = (int) datos.getY();
        int width = datos.getWidth();
        int height = datos.getHeight();
        
        // Forma de nave enemiga (polígono)
        g.setColor(Color.red);
        
        int[] xPoints = {
            xInt + width / 2,      // Punta superior
            xInt,                   // Esquina superior izquierda
            xInt + width / 4,       // Parte media izquierda
            xInt,                   // Esquina inferior izquierda
            xInt + width / 2,       // Centro inferior
            xInt + width,           // Esquina inferior derecha
            xInt + 3 * width / 4,  // Parte media derecha
            xInt + width            // Esquina superior derecha
        };
        int[] yPoints = {
            yInt,                   // Punta superior
            yInt + height / 4,      // Parte superior
            yInt + height / 2,      // Parte media
            yInt + height,          // Parte inferior izquierda
            yInt + height - 2,      // Centro inferior
            yInt + height,          // Parte inferior derecha
            yInt + height / 2,      // Parte media
            yInt + height / 4       // Parte superior
        };
        g.fillPolygon(xPoints, yPoints, 8);
        
        // Detalles: ventanas/cabina
        g.setColor(new Color(200, 0, 0));
        g.fillOval(xInt + width / 4, yInt + height / 4, width / 2, height / 3);
    }
    
    private void renderizarHUD(Graphics2D g, EstadoJuegoDTO.DatosHUD hud) {
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Score: " + hud.getPuntuacion(), 16, 28);
        g.drawString("Nivel: " + hud.getNivel(), 16, 52);
        
        // Vidas debajo del nivel
        int circles = hud.getVidas();
        int size = 16;
        int pad = 6;
        int startX = 16;
        int startY = 76;
        for (int i = 0; i < circles; i++) {
            int x = startX + i * (size + pad);
            g.setColor(Color.green);
            g.fillOval(x, startY, size, size);
            g.setColor(Color.white);
            g.drawOval(x, startY, size, size);
        }
    }
    
    private void renderizarTransicionNivel(Graphics2D g, EstadoJuegoDTO estado) {
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(36f));
        String msg = "Nivel " + estado.getNivelActual();
        int textWidth = g.getFontMetrics().stringWidth(msg);
        g.drawString(msg, (getWidth() - textWidth) / 2, getHeight() / 2);
    }
    
    private void renderizarMenu(Graphics2D g) {
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(22f));
        g.drawString("Space Invaders - Presiona Enter", getWidth() / 2 - 160, getHeight() / 2);
    }
    
    private void renderizarGameOver(Graphics2D g, EstadoJuegoDTO estado) {
        EstadoJuegoDTO.DatosHUD hud = estado.getHud();
        if (hud == null) return;
        
        g.setColor(Color.red);
        g.setFont(g.getFont().deriveFont(36f));
        g.drawString("GAME OVER", getWidth() / 2 - 120, getHeight() / 2 - 20);
        g.setColor(Color.white);
        g.setFont(g.getFont().deriveFont(20f));
        g.drawString("Puntaje: " + hud.getPuntuacion(), getWidth() / 2 - 80, getHeight() / 2 + 18);
    }
}

