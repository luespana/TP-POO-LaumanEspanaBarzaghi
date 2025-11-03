package org.example.modelo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.example.modelo.entidad.*;

public class Juego {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private String estado;
    private Partida partida;
    private List<Nivel> niveles;
    private Ranking ranking;
    private final List<Proyectil> proyectiles;
    private JugadorEnJuego jugadorEnJuego;
    private NaveJugador naveJugador;
    private List<NaveEnemiga> enemigos;
    private List<MuroEnergia> muros;
    private int nivelActual = 1;
    private double nivelMensajeTimer = 0; // segundos para mostrar "Nivel X" en pantalla negra

    public Juego() {
        this.niveles = new ArrayList<>();
        this.proyectiles = new ArrayList<>();
        this.ranking = new Ranking();
        this.enemigos = new ArrayList<>();
        this.muros = new ArrayList<>();
        this.estado = "MENU";
    }

    public void inicializarPartida() {
        this.partida = new Partida();
        this.partida.iniciarPartida();
        this.jugadorEnJuego = new JugadorEnJuego();
        this.naveJugador = new NaveJugador(WIDTH / 2 - 20, HEIGHT - 80, 40, 20, 300);
        inicializarEnemigos();
        inicializarMuros();
        this.estado = "EN_JUEGO";
    }

    private void inicializarEnemigos() {
        enemigos.clear();
        int rows = Math.min(5, Math.max(1, nivelActual)); // Nivel 1: 1 fila; cada nivel agrega una fila hasta 5
        int cols = 11; // ancho clásico fijo
        int startX = 80;
        int startY = 80;
        int hGap = 40;
        int vGap = 30;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * hGap;
                int y = startY + r * vGap;
                enemigos.add(new NaveEnemiga(x, y, 30, 20));
            }
        }
        AlienFormation.resetForLevel(nivelActual);
    }

    private void inicializarMuros() {
        muros.clear();
        int baseY = HEIGHT - 140;
        int[] bases = {140, 320, 500, 680};
        for (int baseX : bases) {
            muros.add(MuroEnergia.createBlock(baseX - 30, baseY, 60, 30, 3));
        }
    }

    public void update(double deltaSeconds, Set<Integer> pressedKeys) {
        if ("TRANSICION_NIVEL".equals(estado)) {
            nivelMensajeTimer -= deltaSeconds;
            if (nivelMensajeTimer <= 0) {
                inicializarEnemigos();
                estado = "EN_JUEGO";
            }
            return;
        }
        if (!"EN_JUEGO".equals(estado)) return;

        boolean left = pressedKeys.contains(KeyEvent.VK_LEFT) || pressedKeys.contains(KeyEvent.VK_A);
        boolean right = pressedKeys.contains(KeyEvent.VK_RIGHT) || pressedKeys.contains(KeyEvent.VK_D);
        boolean shoot = pressedKeys.contains(KeyEvent.VK_SPACE);

        naveJugador.update(deltaSeconds, left, right, shoot, proyectiles);

        AlienFormation.update(enemigos, deltaSeconds, proyectiles);

        for (Proyectil p : new ArrayList<>(proyectiles)) {
            p.update(deltaSeconds);
            if (p.getY() < -20 || p.getY() > HEIGHT + 20) {
                proyectiles.remove(p);
            }
        }

        ResolverColisiones.resolver(proyectiles, enemigos, muros, naveJugador, jugadorEnJuego);
        if (jugadorEnJuego.sinVidas()) {
            estado = "GAME_OVER";
            return;
        }

        if (enemigos.isEmpty()) {
            nivelActual++;
            nivelMensajeTimer = 2.0; // 2 segundos de pantalla de nivel
            proyectiles.clear();
            estado = "TRANSICION_NIVEL";
            return;
        }
    }

    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        if ("EN_JUEGO".equals(estado)) {
            naveJugador.render(g);
            for (NaveEnemiga e : enemigos) e.render(g);
            for (MuroEnergia m : muros) m.render(g);
            for (Proyectil p : proyectiles) p.render(g);

            // HUD más grande
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(20f));
            g.drawString("Score: " + jugadorEnJuego.getPuntaje(), 16, 28);
            g.drawString("Nivel: " + nivelActual, 16, 52);

            // Vidas como círculos grandes en la esquina superior derecha
            int circles = jugadorEnJuego.getVidas();
            int size = 16;
            int pad = 6;
            int cx = WIDTH - 20;
            int cy = 14;
            for (int i = 0; i < circles; i++) {
                int x = cx - i * (size + pad);
                g.setColor(Color.green);
                g.fillOval(x, cy, size, size);
                g.setColor(Color.white);
                g.drawOval(x, cy, size, size);
            }

        } else if ("TRANSICION_NIVEL".equals(estado)) {
            g.setColor(Color.black);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(36f));
            String msg = "Nivel " + nivelActual;
            // centrado aproximado
            int textWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (WIDTH - textWidth) / 2, HEIGHT / 2);
        } else if ("MENU".equals(estado)) {
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(22f));
            g.drawString("Space Invaders - Presiona Enter", WIDTH / 2 - 160, HEIGHT / 2);
        } else if ("GAME_OVER".equals(estado)) {
            g.setColor(Color.red);
            g.setFont(g.getFont().deriveFont(36f));
            g.drawString("GAME OVER", WIDTH / 2 - 120, HEIGHT / 2 - 20);
            g.setColor(Color.white);
            g.setFont(g.getFont().deriveFont(20f));
            g.drawString("Puntaje: " + jugadorEnJuego.getPuntaje(), WIDTH / 2 - 80, HEIGHT / 2 + 18);
        }
    }
}
