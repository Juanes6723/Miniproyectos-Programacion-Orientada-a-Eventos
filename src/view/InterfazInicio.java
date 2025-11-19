package view;

import javax.swing.*;
import java.awt.*;
import controller.MenuController;
import utils.Audio;

public class InterfazInicio extends JFrame {

    public InterfazInicio() {
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Interfaz en Pantalla Completa");

        JPanel panelFondo = new JPanel() {
            private Image imagenFondo = new ImageIcon("img/FondoInicioMiniProyecto2.png").getImage();
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);

        JButton botonCombate = crearBotonInvisible(153, 75, 400, 40, e -> {
            dispose();
            MenuController.abrirVistaCombate();
        });

        JButton botonPersonajes = crearBotonInvisible(153, 124, 260, 42, e -> {
            dispose();
            MenuController.abrirVistaPersonajes();
        });

        JButton botonEnemigos = crearBotonInvisible(153, 173, 232, 42, e -> {
            dispose();
            MenuController.abrirVistaEnemigos();
        });

        JButton botonSalir = crearBotonInvisible(153, 222, 366, 42, e -> {
            int opcion = JOptionPane.showConfirmDialog(
                this, "¿Deseas salir del juego?", "Confirmar salida",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
            );
            if (opcion == JOptionPane.YES_OPTION) System.exit(0);
        });

        panelFondo.add(botonCombate);
        panelFondo.add(botonPersonajes);
        panelFondo.add(botonEnemigos);
        panelFondo.add(botonSalir);

        setContentPane(panelFondo);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);

        try {
            Audio.reproducir("music/musicaInicio.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton crearBotonInvisible(int x, int y, int w, int h, java.awt.event.ActionListener evento) {
        JButton boton = new JButton();
        boton.setBounds(x, y, w, h);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        boton.addActionListener(evento);
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazInicio::new);
    }
}
