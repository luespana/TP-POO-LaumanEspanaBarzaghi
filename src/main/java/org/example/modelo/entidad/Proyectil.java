package org.example.modelo.entidad;

import java.awt.*;

/**
 * Representa un proyectil en el juego.
 * 
 * <p>Los proyectiles pueden ser:
 * <ul>
 *   <li>Del jugador: se mueven hacia arriba (velocidad negativa), color cyan</li>
 *   <li>Del enemigo: se mueven hacia abajo (velocidad positiva), color rojo</li>
 * </ul>
 * 
 * <p>Los proyectiles se crean mediante métodos estáticos de fábrica
 * que establecen la velocidad y dirección correcta según el origen.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class Proyectil {
    private double x;
    private double y;
    private final double vy;
    private final int width = 4;
    private final int height = 10;
    private final boolean fromPlayer;

    private Proyectil(double x, double y, double vy, boolean fromPlayer) {
        this.x = x;
        this.y = y;
        this.vy = vy;
        this.fromPlayer = fromPlayer;
    }

    public static Proyectil playerBullet(double x, double y) {
        return new Proyectil(x, y, -500, true);
    }

    public static Proyectil enemyBullet(double x, double y) {
        return new Proyectil(x, y, 240, false);
    }

    public void update(double dt) {
        y += vy * dt;
    }

    public boolean isFromPlayer() {
        return fromPlayer;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public void render(Graphics2D g) {
        g.setColor(fromPlayer ? Color.cyan : Color.red);
        g.fillRect((int) x, (int) y, width, height);
    }

    public double getX() { return x; }
    public double getY() { return y; }
}

