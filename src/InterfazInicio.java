import javax.swing.*;

import utils.Audio;

import java.awt.*;

public class InterfazInicio extends JFrame {

    public InterfazInicio() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Interfaz en Pantalla Completa");

        JPanel panelFondo = new JPanel() {
            private Image imagenFondo = new ImageIcon(
                "img/FondoInicioMiniProyecto2.png"
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);

        // Creacion de los botones invisibles y sus respectivos eventos
        JButton botonCombate = new JButton();
        botonCombate.setBounds(153, 75, 400, 40);
        botonCombate.setOpaque(false);
        botonCombate.setContentAreaFilled(false);
        botonCombate.setBorderPainted(false);
        botonCombate.setFocusPainted(false);
        botonCombate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botonCombate.addActionListener(e -> {
            dispose();
            new InterfazCombate();
        }); 

        JButton botonPersonajes = new JButton();
        botonPersonajes.setBounds(153, 124, 260, 42);
        botonPersonajes.setOpaque(false);
        botonPersonajes.setContentAreaFilled(false);
        botonPersonajes.setBorderPainted(false);
        botonPersonajes.setFocusPainted(false);
        botonPersonajes.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botonPersonajes.addActionListener(e -> {
            dispose();
            new InterfazPersonajes();
        });

        JButton botonEnemigos = new JButton();
        botonEnemigos.setBounds(153, 173, 232, 42);
        botonEnemigos.setOpaque(false);
        botonEnemigos.setContentAreaFilled(false);
        botonEnemigos.setBorderPainted(false);
        botonEnemigos.setFocusPainted(false);
        botonEnemigos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botonEnemigos.addActionListener(e -> {
            dispose();
            new InterfazEnemigos();
        });

        JButton botonSalir = new JButton();
        botonSalir.setBounds(153, 222, 366, 42);
        botonSalir.setOpaque(false);
        botonSalir.setContentAreaFilled(false);
        botonSalir.setBorderPainted(false);
        botonSalir.setFocusPainted(false);
        botonSalir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        botonSalir.addActionListener(e -> {
            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas salir del juego?",
                    "Confirmar salida",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (opcion == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        panelFondo.add(botonCombate);
        panelFondo.add(botonPersonajes);
        panelFondo.add(botonEnemigos);
        panelFondo.add(botonSalir);

        setContentPane(panelFondo);
        
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        try {
            Audio.reproducir("music/musicaInicio.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazInicio());
    }
}
