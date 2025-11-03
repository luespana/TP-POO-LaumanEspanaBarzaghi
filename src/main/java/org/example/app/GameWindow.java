package org.example.app;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(String title, JPanel initialPanel, int width, int height) {
        super(title);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setIgnoreRepaint(true);
        getContentPane().setLayout(new BorderLayout());
        if (initialPanel != null) {
            getContentPane().add(initialPanel, BorderLayout.CENTER);
        }
        pack();
        setSize(width, height);
        setLocationRelativeTo(null);
    }
}

