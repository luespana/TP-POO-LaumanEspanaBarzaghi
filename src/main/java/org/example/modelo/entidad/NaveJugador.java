package org.example.modelo.entidad;

import org.example.modelo.Juego;

import java.awt.*;
import java.util.List;

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
            cooldown = 0.35;
        }
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.green);
        g.fillRect((int) x, (int) y, width, height);
    }
}

