// InterfazEnemigos MVC Adaptado
// Vista: InterfazEnemigos
// Controlador: EnemyController
// Modelo: Characters, Enemy, TypeEnemy ya existen

package view;

import javax.swing.*;
import java.awt.*;
// import java.util.ArrayList;
import controller.EnemyController;
import models.Characters;
import models.Enemy;
// import models.TypeEnemy;

public class InterfazEnemigos extends JFrame {
    private EnemyController controller;
    private JLabel nombreLabel, tipoLabel, hpLabel, mpLabel, ataqueLabel, defensaLabel, speedLabel, critDLabel, critCLabel, habilidadesLabel;
    private JPanel panelInfo;

    public InterfazEnemigos() {

        controller = new EnemyController();

        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Interfaz en Pantalla Completa");

        JPanel panelFondo = new JPanel() {
            private Image imagenFondo = new ImageIcon("img/Enemigos.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);

        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(10, 1));
        panelInfo.setOpaque(false);

        Font font = new Font("Cambria", Font.BOLD, 36);
        Color colorTexto = Color.BLACK;

        nombreLabel = crearLabel("", font, colorTexto);
        tipoLabel = crearLabel("", font, colorTexto);
        hpLabel = crearLabel("", font, colorTexto);
        mpLabel = crearLabel("", font, colorTexto);
        ataqueLabel = crearLabel("", font, colorTexto);
        defensaLabel = crearLabel("", font, colorTexto);
        speedLabel = crearLabel("", font, colorTexto);
        critCLabel = crearLabel("", font, colorTexto);
        critDLabel = crearLabel("", font, colorTexto);
        habilidadesLabel = crearLabel("", font, colorTexto);

        panelInfo.add(nombreLabel);
        panelInfo.add(tipoLabel);
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

        JButton botonDragon = crearBotonInvisible(110, 108, 150, 108);
        botonDragon.addActionListener(e -> mostrarInformacion(controller.getEnemy(3)));

        JButton botonBerenjeno = crearBotonInvisible(110, 247, 150, 108);
        botonBerenjeno.addActionListener(e -> mostrarInformacion(controller.getEnemy(1)));

        JButton botonPinchorugo = crearBotonInvisible(110, 380, 150, 108);
        botonPinchorugo.addActionListener(e -> mostrarInformacion(controller.getEnemy(2)));

        JButton botonLimo = crearBotonInvisible(109, 520, 150, 108);
        botonLimo.addActionListener(e -> mostrarInformacion(controller.getEnemy(0)));

        JButton botonBack = crearBotonInvisible(54, 710, 200, 60);
        botonBack.addActionListener(e -> {
            dispose();
            new InterfazInicio();
        });

        panelFondo.add(botonDragon);
        panelFondo.add(botonBerenjeno);
        panelFondo.add(botonPinchorugo);
        panelFondo.add(botonLimo);
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
        tipoLabel.setText("Tipo: " + ((Enemy) personaje).getType().toString());
        hpLabel.setText("HP: " + personaje.getHP());
        mpLabel.setText("MP: " + personaje.getMP());
        ataqueLabel.setText("Ataque: " + personaje.getAtack());
        defensaLabel.setText("Defensa: " + personaje.getDefense());
        speedLabel.setText("Velocidad: " + personaje.getSpeed());
        critCLabel.setText("Prob. Crítico: " + personaje.getCrit_Change() * 100 + "%");
        critDLabel.setText("Daño Crítico: " + personaje.getCrit_Damage() * 100 + "%");
        habilidadesLabel.setText("Habilidades: " + String.join(", ", personaje.getSkills()));
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazEnemigos::new);
    }
}

