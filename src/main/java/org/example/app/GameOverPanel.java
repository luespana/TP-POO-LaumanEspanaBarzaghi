package org.example.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Panel que se muestra cuando termina la partida.
 * 
 * <p>Muestra:
 * <ul>
 *   <li>Mensaje "GAME OVER"</li>
 *   <li>Puntuación obtenida</li>
 *   <li>Niveles superados</li>
 *   <li>Botones para volver al menú o comenzar nueva partida</li>
 * </ul>
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class GameOverPanel extends JPanel {
    private JButton btnVolverMenu;
    private JButton btnNuevaPartida;

    public GameOverPanel(int puntuacion, int nivelesSuperados) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setOpaque(true);
        setPreferredSize(new Dimension(800, 600));

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setOpaque(false);
        panelCentral.setBackground(Color.BLACK);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Título GAME OVER
        JLabel titulo = new JLabel("GAME OVER");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.RED);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        // Puntaje
        JLabel labelPuntaje = new JLabel("Puntaje: " + puntuacion);
        labelPuntaje.setFont(new Font("Arial", Font.BOLD, 24));
        labelPuntaje.setForeground(Color.WHITE);
        labelPuntaje.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelPuntaje.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // Niveles superados
        JLabel labelNiveles = new JLabel("Niveles superados: " + nivelesSuperados);
        labelNiveles.setFont(new Font("Arial", Font.PLAIN, 20));
        labelNiveles.setForeground(Color.WHITE);
        labelNiveles.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelNiveles.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));

        // Botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.X_AXIS));
        panelBotones.setOpaque(false);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        btnVolverMenu = new JButton("Volver al Menú");
        estiloBoton(btnVolverMenu);
        btnVolverMenu.setPreferredSize(new Dimension(200, 50));
        btnVolverMenu.setMaximumSize(new Dimension(200, 50));

        btnNuevaPartida = new JButton("Nueva Partida");
        estiloBoton(btnNuevaPartida);
        btnNuevaPartida.setPreferredSize(new Dimension(200, 50));
        btnNuevaPartida.setMaximumSize(new Dimension(200, 50));

        panelBotones.add(Box.createHorizontalGlue());
        panelBotones.add(btnVolverMenu);
        panelBotones.add(Box.createHorizontalStrut(30));
        panelBotones.add(btnNuevaPartida);
        panelBotones.add(Box.createHorizontalGlue());

        panelCentral.add(Box.createVerticalGlue());
        panelCentral.add(titulo);
        panelCentral.add(labelPuntaje);
        panelCentral.add(labelNiveles);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(panelBotones);
        panelCentral.add(Box.createVerticalGlue());

        add(panelCentral, BorderLayout.CENTER);
    }

    private void estiloBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setForeground(Color.WHITE);
        boton.setBackground(new Color(50, 50, 150));
        boton.setFocusPainted(false);
        boton.setBorderPainted(true);
        boton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        
        // Efecto hover
        boton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(80, 80, 200));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                boton.setBackground(new Color(50, 50, 150));
            }
        });
    }

    public void agregarListenerVolverMenu(ActionListener listener) {
        btnVolverMenu.addActionListener(listener);
    }

    public void agregarListenerNuevaPartida(ActionListener listener) {
        btnNuevaPartida.addActionListener(listener);
    }
}

