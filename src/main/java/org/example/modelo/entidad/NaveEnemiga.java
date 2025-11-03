package org.example.modelo.entidad;

import java.awt.*;

public class NaveEnemiga extends Nave {
    public NaveEnemiga(double x, double y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.red);
        g.fillRect((int) x, (int) y, width, height);
    }
}

