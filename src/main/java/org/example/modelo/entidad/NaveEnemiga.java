package org.example.modelo.entidad;

import java.awt.*;

/**
 * Representa una nave enemiga en el juego.
 * 
 * <p>Las naves enemigas se renderizan como rectángulos rojos.
 * Su movimiento se controla colectivamente por la clase AlienFormation,
 * que gestiona la formación completa de enemigos.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
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

