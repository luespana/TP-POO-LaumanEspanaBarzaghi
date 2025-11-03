package org.example.modelo;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.example.modelo.entidad.*;

/**
 * Clase principal que representa el estado y lógica del juego Space Invaders.
 * 
 * <p>Esta clase gestiona todos los aspectos del juego:
 * <ul>
 *   <li>Estado del juego (MENU, EN_JUEGO, TRANSICION_NIVEL, GAME_OVER)</li>
 *   <li>Entidades del juego (nave del jugador, enemigos, proyectiles, muros)</li>
 *   <li>Sistema de niveles progresivos</li>
 *   <li>Sistema de puntuación y ranking</li>
 *   <li>Renderizado del juego</li>
 * </ul>
 * 
 * <p>El juego sigue un patrón de estados donde:
 * <ul>
 *   <li>MENU: Estado inicial del juego</li>
 *   <li>EN_JUEGO: El jugador está jugando activamente</li>
 *   <li>TRANSICION_NIVEL: Pantalla de transición entre niveles</li>
 *   <li>GAME_OVER: El juego ha terminado</li>
 * </ul>
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class Juego {
    /** Ancho de la ventana del juego en píxeles */
    public static final int WIDTH = 800;
    /** Alto de la ventana del juego en píxeles */
    public static final int HEIGHT = 600;

    private String estado;
    private Partida partida;
    // Nota: La clase Nivel existe pero no se utiliza actualmente.
    // El sistema de niveles funciona con nivelActual (int) y se incrementa dinámicamente.
    // private List<Nivel> niveles; // Reservado para futuras mejoras
    private Ranking ranking;
    private final List<Proyectil> proyectiles;
    private JugadorEnJuego jugadorEnJuego;
    private NaveJugador naveJugador;
    private List<NaveEnemiga> enemigos;
    private List<MuroEnergia> muros;
    private int nivelActual = 1;
    private double nivelMensajeTimer = 0; // segundos para mostrar "Nivel X" en pantalla negra

    /**
     * Constructor de la clase Juego.
     * Inicializa todas las estructuras de datos necesarias y establece el estado inicial como MENU.
     */
    public Juego() {
        // this.niveles = new ArrayList<>(); // No se usa actualmente
        this.proyectiles = new ArrayList<>();
        this.ranking = new Ranking();
        this.enemigos = new ArrayList<>();
        this.muros = new ArrayList<>();
        this.estado = "MENU";
    }

    /**
     * Inicializa una nueva partida desde cero.
     * 
     * <p>Este método:
     * <ul>
     *   <li>Reinicia el nivel a 1</li>
     *   <li>Crea una nueva instancia de Partida y JugadorEnJuego</li>
     *   <li>Inicializa la nave del jugador en el centro inferior</li>
     *   <li>Crea los enemigos según el nivel actual</li>
     *   <li>Coloca los muros de energía defensivos</li>
     *   <li>Establece el estado a EN_JUEGO</li>
     * </ul>
     */
    public void inicializarPartida() {
        this.partida = new Partida();
        this.partida.iniciarPartida();
        this.jugadorEnJuego = new JugadorEnJuego();
        this.naveJugador = new NaveJugador(WIDTH / 2 - 20, HEIGHT - 80, 40, 20, 300);
        this.nivelActual = 1; // Reiniciar nivel al inicio de cada partida
        this.nivelMensajeTimer = 0;
        inicializarEnemigos();
        inicializarMuros();
        this.estado = "EN_JUEGO";
    }

    /**
     * Inicializa la formación de enemigos según el nivel actual.
     * 
     * <p>El número de filas de enemigos aumenta con el nivel:
     * <ul>
     *   <li>Nivel 1: 1 fila de enemigos</li>
     *   <li>Cada nivel adicional agrega una fila hasta un máximo de 5 filas</li>
     *   <li>Siempre se crean 11 columnas de enemigos</li>
     * </ul>
     * 
     * <p>También reinicia la configuración de movimiento de AlienFormation.
     */
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

    /**
     * Inicializa los muros de energía defensivos.
     * 
     * <p>Crea 4 muros de energía distribuidos horizontalmente en la parte inferior
     * de la pantalla, sirviendo como protección para la nave del jugador.
     */
    private void inicializarMuros() {
        muros.clear();
        int baseY = HEIGHT - 140;
        int[] bases = {140, 320, 500, 680};
        for (int baseX : bases) {
            muros.add(MuroEnergia.createBlock(baseX - 30, baseY, 60, 30, 3));
        }
    }

    /**
     * Actualiza el estado del juego en cada frame.
     * 
     * <p>Este método maneja:
     * <ul>
     *   <li>Transiciones entre niveles</li>
     *   <li>Movimiento de la nave del jugador según las teclas presionadas</li>
     *   <li>Movimiento y disparos de la formación de enemigos</li>
     *   <li>Actualización de proyectiles</li>
     *   <li>Resolución de colisiones</li>
     *   <li>Verificación de condiciones de fin de juego o avance de nivel</li>
     * </ul>
     * 
     * @param deltaSeconds Tiempo transcurrido desde el último frame en segundos
     * @param pressedKeys Conjunto de códigos de teclas actualmente presionadas
     */
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

    /**
     * Renderiza el estado actual del juego en el contexto gráfico proporcionado.
     * 
     * <p>Dependiendo del estado del juego, renderiza:
     * <ul>
     *   <li>EN_JUEGO: Todas las entidades, HUD con puntuación, nivel y vidas</li>
     *   <li>TRANSICION_NIVEL: Pantalla negra con mensaje del nivel</li>
     *   <li>MENU: Mensaje de bienvenida</li>
     *   <li>GAME_OVER: Mensaje de fin de juego con puntuación</li>
     * </ul>
     * 
     * @param g Contexto gráfico donde se dibuja el juego
     */
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

    /**
     * Obtiene el estado actual del juego.
     * 
     * @return Estado actual ("MENU", "EN_JUEGO", "TRANSICION_NIVEL", "GAME_OVER")
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Obtiene el nivel actual en el que se encuentra el jugador.
     * 
     * @return Número del nivel actual (1, 2, 3, ...)
     */
    public int getNivelActual() {
        return nivelActual;
    }

    /**
     * Obtiene la puntuación actual del jugador en la partida.
     * 
     * @return Puntuación actual, o 0 si no hay partida activa
     */
    public int getPuntaje() {
        return jugadorEnJuego != null ? jugadorEnJuego.getPuntaje() : 0;
    }

    /**
     * Obtiene la instancia del sistema de ranking.
     * 
     * @return Objeto Ranking que gestiona las puntuaciones más altas
     */
    public Ranking getRanking() {
        return ranking;
    }

    /**
     * Calcula cuántos niveles fueron superados exitosamente.
     * 
     * <p>Si el jugador perdió en el nivel X, significa que superó X-1 niveles.
     * 
     * @return Número de niveles superados (0 o más)
     */
    public int getNivelesSuperados() {
        // Los niveles superados son nivelActual - 1 (si perdiste en el nivel X, superaste X-1)
        return Math.max(0, nivelActual - 1);
    }
}
