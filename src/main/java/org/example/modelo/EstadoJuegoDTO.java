package org.example.modelo;

import java.util.List;

/**
 * Data Transfer Object (DTO) que contiene todos los datos del estado del juego
 * necesarios para que la vista pueda renderizar sin conocer el modelo.
 * 
 * <p>Este DTO permite que el controlador obtenga datos del modelo y los pase
 * a la vista, respetando la separación de responsabilidades del patrón MVC.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class EstadoJuegoDTO {

    private final String estado;
    private final DatosNaveJugador naveJugador;
    private final List<DatosNaveEnemiga> enemigos;
    private final List<DatosProyectil> proyectiles;
    private final List<DatosMuro> muros;
    private final DatosHUD hud;
    private final int nivelActual;
    
    public EstadoJuegoDTO(String estado, DatosNaveJugador naveJugador, 
                          List<DatosNaveEnemiga> enemigos, 
                          List<DatosProyectil> proyectiles,
                          List<DatosMuro> muros, 
                          DatosHUD hud, 
                          int nivelActual) {
        this.estado = estado;
        this.naveJugador = naveJugador;
        this.enemigos = enemigos;
        this.proyectiles = proyectiles;
        this.muros = muros;
        this.hud = hud;
        this.nivelActual = nivelActual;
    }
    
    public String getEstado() { return estado; }
    public DatosNaveJugador getNaveJugador() { return naveJugador; }
    public List<DatosNaveEnemiga> getEnemigos() { return enemigos; }
    public List<DatosProyectil> getProyectiles() { return proyectiles; }
    public List<DatosMuro> getMuros() { return muros; }
    public DatosHUD getHud() { return hud; }
    public int getNivelActual() { return nivelActual; }
    
    /**
     * Datos de la nave del jugador.
     */
    public static class DatosNaveJugador {
        private final double x;
        private final double y;
        private final int width;
        private final int height;
        
        public DatosNaveJugador(double x, double y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }
    
    /**
     * Datos de una nave enemiga.
     */
    public static class DatosNaveEnemiga {
        private final double x;
        private final double y;
        private final int width;
        private final int height;
        
        public DatosNaveEnemiga(double x, double y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
    }
    
    /**
     * Datos de un proyectil.
     */
    public static class DatosProyectil {
        private final double x;
        private final double y;
        private final int width;
        private final int height;
        private final boolean fromPlayer;
        
        public DatosProyectil(double x, double y, int width, int height, boolean fromPlayer) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.fromPlayer = fromPlayer;
        }
        
        public double getX() { return x; }
        public double getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public boolean isFromPlayer() { return fromPlayer; }
    }
    
    /**
     * Datos de un bloque de muro.
     */
    public static class DatosMuro {
        private final int x;
        private final int y;
        private final int width;
        private final int height;
        private final int hp;
        
        public DatosMuro(int x, int y, int width, int height, int hp) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.hp = hp;
        }
        
        public int getX() { return x; }
        public int getY() { return y; }
        public int getWidth() { return width; }
        public int getHeight() { return height; }
        public int getHp() { return hp; }
    }
    
    /**
     * Datos del HUD (Head-Up Display).
     */
    public static class DatosHUD {
        private final int puntuacion;
        private final int nivel;
        private final int vidas;
        
        public DatosHUD(int puntuacion, int nivel, int vidas) {
            this.puntuacion = puntuacion;
            this.nivel = nivel;
            this.vidas = vidas;
        }
        
        public int getPuntuacion() { return puntuacion; }
        public int getNivel() { return nivel; }
        public int getVidas() { return vidas; }
    }
}

