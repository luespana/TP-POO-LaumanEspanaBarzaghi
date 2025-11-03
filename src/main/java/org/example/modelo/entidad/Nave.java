package org.example.modelo.entidad;

import java.awt.*;

/**
 * Clase abstracta que representa una nave genérica en el juego.
 * 
 * <p>Proporciona la funcionalidad base para todas las naves:
 * <ul>
 *   <li>Posición y dimensiones</li>
 *   <li>Método para obtener los límites (bounds) para detección de colisiones</li>
 *   <li>Método abstracto para renderizado</li>
 * </ul>
 * 
 * <p>Las clases hijas deben implementar el método render() para definir
 * cómo se dibuja cada tipo de nave.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public abstract class Nave {
    protected double x;
    protected double y;
    protected int width;
    protected int height;

    public Nave(double x, double y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, width, height);
    }

    public abstract void render(Graphics2D g);

    public double getX() { return x; }
    public double getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    protected void setX(double x) { this.x = x; }
    protected void setY(double y) { this.y = y; }
}

