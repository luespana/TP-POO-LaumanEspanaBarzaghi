package org.example.modelo.entidad;

import org.example.modelo.Juego;

import java.util.List;
import java.util.Random;

/**
 * Gestiona el movimiento y comportamiento de la formación completa de enemigos.
 * 
 * <p>Esta clase implementa el comportamiento característico de Space Invaders:
 * <ul>
 *   <li>Movimiento horizontal en grupo con rebote en los bordes</li>
 *   <li>Descenso gradual cuando la formación rebota</li>
 *   <li>Velocidad que aumenta con el nivel y después de cada rebote</li>
 *   <li>Disparos aleatorios desde las naves enemigas</li>
 * </ul>
 * 
 * <p>Utiliza un patrón de diseño estático donde no se instancia la clase,
 * sino que se usan métodos estáticos para gestionar la formación.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class AlienFormation {
    private static double direction = 1;
    private static double speed = 40;
    private static double stepDown = 2; // baja pocos píxeles como el original
    private static final Random rng = new Random();

    public static void resetForLevel(int level) {
        direction = 1;
        speed = 40 + level * 12; // sube más por nivel
        stepDown = 2; // baja fina por rebote
    }

    public static void update(List<NaveEnemiga> enemigos, double dt, List<Proyectil> proyectiles) {
        if (enemigos.isEmpty()) return;
        double dx = speed * direction * dt;

        double minX = Double.MAX_VALUE;
        double maxX = -Double.MAX_VALUE;
        for (NaveEnemiga e : enemigos) {
            minX = Math.min(minX, e.x);
            maxX = Math.max(maxX, e.x + e.width);
        }

        boolean hitLeft = (minX + dx) < 10;
        boolean hitRight = (maxX + dx) > (Juego.WIDTH - 10);

        if (hitLeft || hitRight) {
            // Rebotar: bajar un poco y cambiar dirección, sin salirse del borde
            direction *= -1;
            for (NaveEnemiga e : enemigos) {
                e.y += stepDown;
            }
            speed = Math.min(260, speed + 6);
        } else {
            // Movimiento lateral normal
            for (NaveEnemiga e : enemigos) {
                e.x += dx;
            }
        }

        // Disparos aleatorios desde la formación
        for (NaveEnemiga e : enemigos) {
            if (rng.nextDouble() < 0.002) {
                proyectiles.add(Proyectil.enemyBullet(e.x + e.width / 2.0, e.y + e.height));
            }
        }
    }
}

