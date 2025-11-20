package org.example.modelo.entidad;

import java.awt.*;

/**
 * Representa una tronera en el juego.
 * 
 * Es decir, los escudos que protegen la nave del jugador.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class Tronera {
    private double x;
    private double y;
    private final int width = 36;
    private final int height = 16;
    private final double speed = 120;
    private boolean alive;

    public void spawn(double startX, double y) {
        this.x = startX;
        this.y = y;
        this.alive = true;
    }

    public void update(double dt) {
        if (!alive) return;
        x += speed * dt;
        if (x > 820) alive = false;
    }

    public Rectangle getBounds() { return new Rectangle((int)x, (int)y, width, height); }
    public boolean isAlive() { return alive; }
    public void kill() { alive = false; }

    public void render(Graphics2D g) {
        if (!alive) return;
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, width, height);
    }
}

