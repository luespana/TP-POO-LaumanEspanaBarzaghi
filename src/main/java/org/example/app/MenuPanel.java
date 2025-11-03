package org.example.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private JButton btnNuevaPartida;
    private JButton btnVerRanking;

    public MenuPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setOpaque(true);
        setPreferredSize(new Dimension(800, 600));
        
        // Panel central para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.setOpaque(false);
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        // Título
        JLabel titulo = new JLabel("SPACE INVADERS");
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 80, 0));
        
        // Botón Nueva Partida
        btnNuevaPartida = new JButton("Comenzar Nueva Partida");
        estiloBoton(btnNuevaPartida);
        btnNuevaPartida.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNuevaPartida.setPreferredSize(new Dimension(300, 60));
        btnNuevaPartida.setMaximumSize(new Dimension(300, 60));
        btnNuevaPartida.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        // Botón Ver Ranking
        btnVerRanking = new JButton("Ver Ranking");
        estiloBoton(btnVerRanking);
        btnVerRanking.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVerRanking.setPreferredSize(new Dimension(300, 60));
        btnVerRanking.setMaximumSize(new Dimension(300, 60));
        btnVerRanking.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        panelBotones.add(titulo);
        panelBotones.add(Box.createVerticalGlue());
        panelBotones.add(btnNuevaPartida);
        panelBotones.add(Box.createVerticalStrut(30));
        panelBotones.add(btnVerRanking);
        panelBotones.add(Box.createVerticalGlue());
        
        add(panelBotones, BorderLayout.CENTER);
    }
    
    private void estiloBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 20));
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
    
    public void agregarListenerNuevaPartida(ActionListener listener) {
        btnNuevaPartida.addActionListener(listener);
    }
    
    public void agregarListenerVerRanking(ActionListener listener) {
        btnVerRanking.addActionListener(listener);
    }
}

