package view;

import javax.swing.*;
import controller.CharacterController;
import models.Characters;

import java.awt.*;
public class InterfazPersonajes extends JFrame {

    private CharacterController controller;
    private JLabel nombreLabel, hpLabel, mpLabel, ataqueLabel, defensaLabel,
            speedLabel, critDLabel, critCLabel, habilidadesLabel;
    private JPanel panelInfo;
    public InterfazPersonajes() {

        controller = new CharacterController();

        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Interfaz en Pantalla Completa");

        JPanel panelFondo = new JPanel() {
            private Image imagenFondo =
                    new ImageIcon("img/Personajes.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);

        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(9, 1));
        panelInfo.setOpaque(false);

        Font font = new Font("Cambria", Font.BOLD, 36);
        Color colorTexto = Color.BLACK;

        nombreLabel = crearLabel("", font, colorTexto);
        hpLabel = crearLabel("", font, colorTexto);
        mpLabel = crearLabel("", font, colorTexto);
        ataqueLabel = crearLabel("", font, colorTexto);
        defensaLabel = crearLabel("", font, colorTexto);
        speedLabel = crearLabel("", font, colorTexto);
        critCLabel = crearLabel("", font, colorTexto);
        critDLabel = crearLabel("", font, colorTexto);
        habilidadesLabel = crearLabel("", font, colorTexto);

        panelInfo.add(nombreLabel);
        panelInfo.add(hpLabel);
        panelInfo.add(mpLabel);
        panelInfo.add(ataqueLabel);
        panelInfo.add(defensaLabel);
        panelInfo.add(speedLabel);
        panelInfo.add(critCLabel);
        panelInfo.add(critDLabel);
        panelInfo.add(habilidadesLabel);

        panelInfo.setBounds(732, 100, 800, 400);

        panelFondo.add(panelInfo);

        // Botones invisibles
        JButton botonHeroe = crearBotonInvisible(110, 108, 150, 108);
        botonHeroe.addActionListener(e ->
                mostrarInformacion(controller.getPersonaje(0)));

        JButton botonYangus = crearBotonInvisible(110, 247, 150, 108);
        botonYangus.addActionListener(e ->
                mostrarInformacion(controller.getPersonaje(1)));

        JButton botonAngelo = crearBotonInvisible(110, 380, 150, 108);
        botonAngelo.addActionListener(e ->
                mostrarInformacion(controller.getPersonaje(3)));

        JButton botonJessica = crearBotonInvisible(109, 520, 150, 108);
        botonJessica.addActionListener(e ->
                mostrarInformacion(controller.getPersonaje(2)));

        JButton botonBack = crearBotonInvisible(54, 710, 200, 60);
        botonBack.addActionListener(e -> {
            dispose();
            new InterfazInicio();
        });

        panelFondo.add(botonHeroe);
        panelFondo.add(botonYangus);
        panelFondo.add(botonAngelo);
        panelFondo.add(botonJessica);
        panelFondo.add(botonBack);

        setContentPane(panelFondo);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }


    private JLabel crearLabel(String texto, Font font, Color color) {
        JLabel label = new JLabel(texto);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private JButton crearBotonInvisible(int x, int y, int w, int h) {
        JButton boton = new JButton();
        boton.setBounds(x, y, w, h);
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return boton;
    }

    private void mostrarInformacion(Characters personaje) {
        nombreLabel.setText("Nombre: " + personaje.getName());
        hpLabel.setText("HP: " + personaje.getHP());
        mpLabel.setText("MP: " + personaje.getMP());
        ataqueLabel.setText("Ataque: " + personaje.getAtack());
        defensaLabel.setText("Defensa: " + personaje.getDefense());
        speedLabel.setText("Velocidad: " + personaje.getSpeed());
        critCLabel.setText("Prob. Crítico: " + personaje.getCrit_Change() * 100 + "%");
        critDLabel.setText("Daño Crítico: " + personaje.getCrit_Damage() * 100 + "%");
        habilidadesLabel.setText("Habilidades: " + String.join(", ", personaje.getSkills()));
    }
}
