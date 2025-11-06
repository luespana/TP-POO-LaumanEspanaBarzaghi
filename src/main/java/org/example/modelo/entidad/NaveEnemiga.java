package org.example.modelo.entidad;

import java.awt.*;

/**
 * Representa una nave enemiga en el juego.
 * 
 * <p>Las naves enemigas se renderizan como polígonos con forma de nave espacial roja,
 * incluyendo detalles como ventanas/cabina. Su movimiento se controla colectivamente
 * por la clase AlienFormation, que gestiona la formación completa de enemigos.
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
        int xInt = (int) x;
        int yInt = (int) y;
        
        // Forma de nave enemiga (polígono)
        g.setColor(Color.red);
        
        // Cuerpo principal (forma de nave)
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
}

