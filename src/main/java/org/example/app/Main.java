package org.example.app;

import org.example.controlador.ControladorJuego;

/**
 * Punto de entrada principal de la aplicación Space Invaders.
 * 
 * <p>Crea el controlador del juego y lo inicializa, lo cual
 * inicia la interfaz gráfica y el flujo de la aplicación.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class Main {
    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        ControladorJuego controlador = new ControladorJuego();
        controlador.iniciar();
    }
}

