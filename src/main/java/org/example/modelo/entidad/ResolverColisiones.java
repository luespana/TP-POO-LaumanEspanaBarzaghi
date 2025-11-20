package org.example.modelo.entidad;

import org.example.modelo.JugadorEnJuego;
import org.example.modelo.Juego;

import java.awt.*;
import java.util.List;

/**
 * Clase utilitaria que resuelve todas las colisiones en el juego.
 * 
 * <p>Gestiona las siguientes interacciones:
 * <ul>
 *   <li>Proyectiles del jugador con enemigos: elimina enemigo y proyectil, otorga puntos</li>
 *   <li>Proyectiles del enemigo con el jugador: resta vida, reposiciona jugador</li>
 *   <li>Proyectiles con muros de energía: destruye bloque del muro, elimina proyectil</li>
 * </ul>
 * 
 * <p>Utiliza el patrón de diseño estático donde todos los métodos son estáticos.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class ResolverColisiones {
    public static void resolver(List<Proyectil> proyectiles,
                                List<NaveEnemiga> enemigos,
                                List<MuroEnergia> muros,
                                NaveJugador jugador,
                                JugadorEnJuego estadoJugador) {
        for (int i = proyectiles.size() - 1; i >= 0; i--) {
            Proyectil p = proyectiles.get(i);
            Rectangle pb = p.getBounds();

            boolean eliminado = false;

            // Shields
            for (MuroEnergia m : muros) {
                if (m.hit(pb)) {
                    proyectiles.remove(i);
                    eliminado = true;
                    break;
                }
            }
            if (eliminado) continue;

            // Colisiones con enemigos
            if (p.isFromPlayer()) {
                for (int e = enemigos.size() - 1; e >= 0; e--) {
                    if (pb.intersects(enemigos.get(e).getBounds())) {
                        enemigos.remove(e);
                        proyectiles.remove(i);
                        estadoJugador.agregarPuntos(10);
                        eliminado = true;
                        break;
                    }
                }
            } else { // Colisiones con jugador
                if (pb.intersects(jugador.getBounds())) {
                    estadoJugador.perderVida();
                    proyectiles.remove(i);
                    // reset player position simple
                    jugador.setX(Juego.WIDTH / 2.0 - jugador.getWidth() / 2.0);
                }
            }
        }
    }
}

