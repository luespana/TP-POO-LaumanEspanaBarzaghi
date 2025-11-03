package org.example.modelo.entidad;

import org.example.modelo.JugadorEnJuego;
import org.example.modelo.Juego;

import java.awt.*;
import java.util.List;

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
            } else {
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

