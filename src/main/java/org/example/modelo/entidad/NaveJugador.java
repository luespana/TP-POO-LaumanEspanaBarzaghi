package org.example.modelo.entidad;

import org.example.modelo.Juego;

import java.awt.*;
import java.util.List;

/**
 * Representa la nave controlada por el jugador.
 * 
 * <p>Gestiona:
 * <ul>
 *   <li>Movimiento horizontal (izquierda/derecha) con límites de pantalla</li>
 *   <li>Sistema de disparo con cooldown mejorado (alta frecuencia de disparo)</li>
 *   <li>Renderizado como nave triangular verde con cañón superior</li>
 * </ul>
 * 
 * <p>El movimiento se controla mediante teclas de flecha o WASD,
 * y el disparo con la barra espaciadora.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class NaveJugador extends Nave {
    private final double speedPixelsPerSecond;
    private double cooldown;

    public NaveJugador(double x, double y, int width, int height, double speedPixelsPerSecond) {
        super(x, y, width, height);
        this.speedPixelsPerSecond = speedPixelsPerSecond;
    }

    public void update(double dt, boolean left, boolean right, boolean shoot, List<Proyectil> proyectiles) {
        double dx = 0;
        if (left) dx -= speedPixelsPerSecond * dt;
        if (right) dx += speedPixelsPerSecond * dt;
        x = Math.max(0, Math.min(Juego.WIDTH - width, x + dx));

        if (cooldown > 0) cooldown -= dt;
        if (shoot && cooldown <= 0) {
            proyectiles.add(Proyectil.playerBullet(x + width / 2.0 - 2, y - 10));
            cooldown = 0.15; // Aumentada frecuencia de disparo (antes 0.35)
        }
    }

    @Override
    public void render(Graphics2D g) {
        int xInt = (int) x;
        int yInt = (int) y;
        
        // Cuerpo principal de la nave (triángulo)
        g.setColor(Color.green);
        int[] xPoints = {xInt + width / 2, xInt, xInt + width};
        int[] yPoints = {yInt, yInt + height, yInt + height};
        g.fillPolygon(xPoints, yPoints, 3);
        
        // Cañón en la parte superior
        int cañonWidth = 6;
        int cañonHeight = 8;
        g.setColor(new Color(0, 200, 0)); // Verde más claro para el cañón
        g.fillRect(xInt + width / 2 - cañonWidth / 2, yInt - cañonHeight, cañonWidth, cañonHeight);
        
        // Detalles del cañón
        g.setColor(new Color(0, 255, 0));
        g.fillRect(xInt + width / 2 - 2, yInt - cañonHeight, 4, cañonHeight);
    }
}

