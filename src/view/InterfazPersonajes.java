package view;
import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;

public class InterfazPersonajes extends JFrame {
    private ArrayList<Characters> personajes;
    private JLabel nombreLabel, hpLabel, mpLabel, ataqueLabel, defensaLabel, speedLabel, critDLabel,  critCLabel, habilidadesLabel;
    private JPanel panelInfo; // panel transparente para mostrar datos

    public InterfazPersonajes() {

        // Datos de prueba
        personajes = new ArrayList<>();
        personajes.add(new Heroes("Heroe", 100, 50, 20, 10, 15, 1.5f, 0.2f, 25, new ArrayList<>(java.util.List.of("FIREBALL"))));
        personajes.add(new Heroes("Yangus", 120, 40, 25, 15, 10, 1.3f, 0.15f, 10, new ArrayList<>(java.util.List.of("SWEET_BREATH"))));
        personajes.add(new Heroes("Jessica", 90, 60, 18, 8, 12, 1.4f, 0.25f, 30, new ArrayList<>(java.util.List.of("HEAL"))));
        personajes.add(new Heroes("Angelo", 110, 45, 22, 12, 14, 1.2f, 0.18f, 20, new ArrayList<>(java.util.List.of("FIREBALL"))));

        // Configuración base
        setUndecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Interfaz en Pantalla Completa");

        JPanel panelFondo = new JPanel() {
            private Image imagenFondo = new ImageIcon(
                "img/Personajes.jpg"
            ).getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);

        // 🧾 Panel transparente para info
        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(9, 1)); // 6 líneas de texto
        panelInfo.setOpaque(false); // transparente

        // Estilo común de etiquetas
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

        // 🔧 Posición del panel de información (ajústala según tu fondo)
        panelInfo.setBounds(732, 100, 800, 400);

        panelFondo.add(panelInfo);

        // 🧍 Botones invisibles sobre los personajes
        JButton botonHeroe = crearBotonInvisible(110, 108, 150, 108);
        botonHeroe.addActionListener(e -> mostrarInformacion(personajes.get(0)));

        JButton botonYangus = crearBotonInvisible(110, 247, 150, 108);
        botonYangus.addActionListener(e -> mostrarInformacion(personajes.get(1)));

        JButton botonAngelo = crearBotonInvisible(110, 380, 150, 108);
        botonAngelo.addActionListener(e -> mostrarInformacion(personajes.get(3)));

        JButton botonJessica = crearBotonInvisible(109, 520, 150, 108);
        botonJessica.addActionListener(e -> mostrarInformacion(personajes.get(2)));

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
        critCLabel.setText("Prob. Crítico: " + personaje.getCrit_Change()*100+"%");
        critDLabel.setText("Daño Crítico: " + personaje.getCrit_Damage()*100+"%");
        habilidadesLabel.setText("Habilidades: " + String.join(", ", personaje.getSkills()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazPersonajes::new);
    }
}