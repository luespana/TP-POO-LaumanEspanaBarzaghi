package org.example.app;

import org.example.modelo.EntradaRanking;
import org.example.modelo.Ranking;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel que muestra el ranking de las 5 mejores puntuaciones.
 * 
 * <p>Muestra una tabla con:
 * <ul>
 *   <li>Posición en el ranking</li>
 *   <li>Nombre del jugador</li>
 *   <li>Niveles superados</li>
 *   <li>Puntuación obtenida</li>
 * </ul>
 * 
 * <p>Incluye un botón para volver al menú principal.
 * 
 * @author LaumanEspanaBarzaghi
 * @version 1.0
 */
public class RankingPanel extends JPanel {
    private Ranking ranking;
    private JTable tablaRanking;
    private JButton btnVolver;

    public RankingPanel(Ranking ranking) {
        this.ranking = ranking;
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        setOpaque(true);
        setPreferredSize(new Dimension(800, 600));

        // Título
        JLabel titulo = new JLabel("RANKING - TOP 5");
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));

        // Tabla de ranking
        String[] columnas = {"Posición", "Nombre", "Niveles Superados", "Puntuación"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaRanking = new JTable(model);
        tablaRanking.setFont(new Font("Arial", Font.PLAIN, 18));
        tablaRanking.setRowHeight(35);
        tablaRanking.setBackground(Color.BLACK);
        tablaRanking.setForeground(Color.WHITE);
        tablaRanking.setGridColor(Color.GRAY);
        tablaRanking.setSelectionBackground(new Color(50, 50, 150));
        tablaRanking.setSelectionForeground(Color.WHITE);
        tablaRanking.getTableHeader().setFont(new Font("Arial", Font.BOLD, 20));
        tablaRanking.getTableHeader().setBackground(new Color(50, 50, 150));
        tablaRanking.getTableHeader().setForeground(Color.WHITE);

        // Establecer ancho de columnas
        tablaRanking.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablaRanking.getColumnModel().getColumn(1).setPreferredWidth(250);
        tablaRanking.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaRanking.getColumnModel().getColumn(3).setPreferredWidth(200);

        // Cargar datos del ranking
        cargarDatosRanking(model);

        JScrollPane scrollPane = new JScrollPane(tablaRanking);
        scrollPane.setBackground(Color.BLACK);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        scrollPane.setPreferredSize(new Dimension(700, 350));

        // Botón Volver
        btnVolver = new JButton("Volver al Menú");
        estiloBoton(btnVolver);
        btnVolver.setPreferredSize(new Dimension(200, 50));
        btnVolver.setMaximumSize(new Dimension(200, 50));

        // Panel central
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
        panelCentral.setOpaque(false);
        panelCentral.setBackground(Color.BLACK);
        panelCentral.add(Box.createVerticalGlue());
        panelCentral.add(titulo);
        panelCentral.add(Box.createVerticalStrut(20));
        panelCentral.add(scrollPane);
        panelCentral.add(Box.createVerticalStrut(30));
        
        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnVolver);
        panelCentral.add(panelBoton);
        panelCentral.add(Box.createVerticalGlue());

        add(panelCentral, BorderLayout.CENTER);
    }

    private void cargarDatosRanking(DefaultTableModel model) {
        List<EntradaRanking> top5 = ranking.getTop5();
        
        if (top5.isEmpty()) {
            model.addRow(new Object[]{"-", "No hay puntuaciones", "-", "-"});
        } else {
            for (int i = 0; i < top5.size(); i++) {
                EntradaRanking entrada = top5.get(i);
                model.addRow(new Object[]{
                    (i + 1),
                    entrada.getNombre(),
                    entrada.getNivelesSuperados(),
                    entrada.getPuntuacion()
                });
            }
        }
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

    public void agregarListenerVolver(java.awt.event.ActionListener listener) {
        btnVolver.addActionListener(listener);
    }
}

