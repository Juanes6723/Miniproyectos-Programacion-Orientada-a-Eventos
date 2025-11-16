import javax.swing.*;

import utils.Audio;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InterfazCombate extends JFrame {

    private List<Personaje> personajes = new ArrayList<>();
    private JTextArea areaMensajes;
    private JButton botonAtacar, botonCurar, botonEspecial, botonHuir;
    private JPanel panelHUD;
    private boolean enCombate = false;
    private int turnoActual = 0;

    public InterfazCombate() {
        setTitle("Combate - Dragon Quest Style");
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelFondo = new JPanel() {
            private final Image fondo = new ImageIcon("img/Combate.jpg").getImage();
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
            }
        };
        panelFondo.setLayout(null);


        areaMensajes = new JTextArea();
        areaMensajes.setEditable(false);
        areaMensajes.setFont(new Font("Cambria", Font.BOLD, 22));
        areaMensajes.setOpaque(false);
        areaMensajes.setForeground(Color.WHITE);
        areaMensajes.setLineWrap(true);
        areaMensajes.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(areaMensajes);
        scroll.setBounds(750, 550, 650, 300);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        panelFondo.add(scroll);

        panelHUD = new JPanel();
        panelHUD.setLayout(new GridLayout(4, 1));
        panelHUD.setBounds(100, 100, 400, 200);
        panelHUD.setOpaque(false);
        panelFondo.add(panelHUD);

        botonAtacar = crearBoton("Atacar", 100, 630);
        botonCurar = crearBoton("Curar", 100, 685);
        botonEspecial = crearBoton("Especial", 100, 740);
        botonHuir = crearBoton("Huir", 100, 795);

        botonAtacar.addActionListener(e -> accionJugador("Atacar"));
        botonCurar.addActionListener(e -> accionJugador("Curar"));
        botonEspecial.addActionListener(e -> accionJugador("Especial"));
        botonHuir.addActionListener(e -> accionJugador("Huir"));

        panelFondo.add(botonAtacar);
        panelFondo.add(botonCurar);
        panelFondo.add(botonEspecial);
        panelFondo.add(botonHuir);

        add(panelFondo);
        setVisible(true);
        try {
            Audio.reproducir("music/musicaBatalla.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }


        iniciarCombate();
    }

    private void iniciarCombate() {
        mostrarMensaje(" ¡El combate comienza!");


        personajes.add(new Personaje("Héroe", 120, 25, 15, 18, true));
        personajes.add(new Personaje("Yangus", 150, 30, 12, 10, true));
        personajes.add(new Personaje("Jessica", 90, 20, 14, 22, true));
        personajes.add(new Personaje("Angelo", 100, 20, 13, 17, true));

        personajes.add(new Personaje("Limo", 60, 10, 8, 16, false));
        personajes.add(new Personaje("Dragón Rojo", 200, 50, 20, 12, false));

        // Ordenar por velocidad (mayor primero)
        personajes.sort(Comparator.comparingInt(p -> -p.speed));

        actualizarHUD();
        enCombate = true;
        ejecutarTurno();
    }

    private void ejecutarTurno() {
        if (!enCombate) return;

        if (todosMuertos(false)) {
            mostrarMensaje("¡Has ganado el combate!");
            terminarCombate();
            return;
        }

        if (todosMuertos(true)) {
            mostrarMensaje("¡Todos los héroes han caído!");
            terminarCombate();
            return;
        }

        if (turnoActual >= personajes.size()) turnoActual = 0;

        Personaje actual = personajes.get(turnoActual);

        // Saltar personajes muertos
        if (actual.hp <= 0) {
            turnoActual++;
            ejecutarTurno();
            return;
        }

        mostrarMensaje("Turno de " + actual.nombre);

        if (actual.esHeroe) {
            habilitarBotones(true);
        } else {
            // Ataque enemigo
            SwingUtilities.invokeLater(() -> {
                try { Thread.sleep(800); } catch (InterruptedException ignored) {}
                realizarAtaque(actual);
                turnoActual++;
                ejecutarTurno();
            });
        }
    }

    private void accionJugador(String accion) {
        Personaje actual = personajes.get(turnoActual);
        Personaje objetivo = obtenerEnemigoVivo();

        if (objetivo == null) return;

        switch (accion) {
            case "Atacar" -> {
                int daño = (int) (Math.random() * (actual.ataque + 10));
                objetivo.hp -= daño;
                mostrarMensaje( actual.nombre + " inflige " + daño + " de daño a " + objetivo.nombre);
            }
            case "Curar" -> {
                int cura = (int) (Math.random() * 20 + 10);
                actual.hp = Math.min(actual.hp + cura, actual.hpMax);
                mostrarMensaje(actual.nombre + " se cura " + cura + " puntos de vida.");
            }
            case "Especial" -> {
                int daño = (int) (Math.random() * (actual.ataque + 25));
                objetivo.hp -= daño;
                mostrarMensaje(actual.nombre + " usa su habilidad especial causando " + daño + " de daño crítico.");
            }
            case "Huir" -> {
                mostrarMensaje("Escapas del combate...");
                terminarCombate();
                return;
            }
        }

        if (objetivo.hp <= 0) {
            objetivo.hp = 0;
            mostrarMensaje(objetivo.nombre + " ha sido derrotado.");
        }

        actualizarHUD();
        habilitarBotones(false);
        turnoActual++;
        ejecutarTurno();
    }

    private void realizarAtaque(Personaje enemigo) {
        Personaje objetivo = obtenerHeroeVivo();
        if (objetivo == null) return;

        int daño = (int) (Math.random() * (enemigo.ataque + 10));
        objetivo.hp -= daño;
        mostrarMensaje(enemigo.nombre + " ataca a " + objetivo.nombre + " e inflige " + daño + " de daño.");

        if (objetivo.hp <= 0) {
            objetivo.hp = 0;
            mostrarMensaje(objetivo.nombre + " ha caído en combate.");
        }

        actualizarHUD();
    }

    private Personaje obtenerEnemigoVivo() {
        return personajes.stream().filter(p -> !p.esHeroe && p.hp > 0).findFirst().orElse(null);
    }

    private Personaje obtenerHeroeVivo() {
        return personajes.stream().filter(p -> p.esHeroe && p.hp > 0).findFirst().orElse(null);
    }

    private boolean todosMuertos(boolean heroes) {
        return personajes.stream()
                .filter(p -> p.esHeroe == heroes)
                .noneMatch(p -> p.hp > 0);
    }

    private void mostrarMensaje(String texto) {
        areaMensajes.append(texto + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }

    private void actualizarHUD() {
        panelHUD.removeAll();
        for (Personaje p : personajes) {
            if (p.esHeroe)
                panelHUD.add(new JLabel(p.nombre + " HP: " + p.hp + "/" + p.hpMax, JLabel.LEFT));
        }
        panelHUD.revalidate();
        panelHUD.repaint();
    }

    private void habilitarBotones(boolean activar) {
        botonAtacar.setEnabled(activar);
        botonCurar.setEnabled(activar);
        botonEspecial.setEnabled(activar);
        botonHuir.setEnabled(activar);
    }

    private void terminarCombate() {
        enCombate = false;
        habilitarBotones(false);
        botonHuir.setText("Volver al menú");
        botonHuir.setEnabled(true);
        botonHuir.addActionListener(e -> dispose());
    }

    private JButton crearBoton(String texto, int x, int y) {
        JButton boton = new JButton(texto);
        boton.setBounds(x, y, 200, 40);
        boton.setFont(new Font("Cambria", Font.BOLD, 20));
        boton.setFocusPainted(false);
        boton.setBackground(new Color(50, 50, 50, 200));
        boton.setForeground(Color.WHITE);
        return boton;
    }

    static class Personaje {
        String nombre;
        int hp, hpMax, ataque, speed;
        boolean esHeroe;

        Personaje(String nombre, int hp, int ataque, int defensa, int speed, boolean esHeroe) {
            this.nombre = nombre;
            this.hp = this.hpMax = hp;
            this.ataque = ataque;
            this.speed = speed;
            this.esHeroe = esHeroe;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazCombate::new);
    }
}
