package org.example.app;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(String title, JPanel gamePanel, int width, int height) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIgnoreRepaint(true);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(gamePanel, BorderLayout.CENTER);
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);
    }
}

