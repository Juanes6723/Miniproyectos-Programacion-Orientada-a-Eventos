package view;

import javax.swing.*;

import controller.CombatController;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import models.Characters;
import models.Heroes;
import models.Enemy;
import models.SkillsList;
import models.TypeEnemy;
import utils.Audio;

public class InterfazCombate extends JFrame {

    private List<Characters> personajes = new ArrayList<>();
    private CombatController controlador = new CombatController();
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
        mostrarMensaje("¡El combate comienza!");

        ArrayList<String> skillsHeroe = new ArrayList<>();
        skillsHeroe.add("FIREBALL");
        skillsHeroe.add("HEAL");

        ArrayList<String> skillsLimo = new ArrayList<>();
        skillsLimo.add("SWEET_BREATH");

        personajes.add(new Heroes("Héroe", 120, 25, 15, 18, turnoActual, 1.5f, 0.2f, 10, skillsHeroe));
        personajes.add(new Enemy("Limo", 60, 10, 8, 16, turnoActual, 1.2f, 0.1f, 5, skillsLimo, TypeEnemy.AGGRESSIVE));

        personajes.sort(Comparator.comparingInt(p -> -p.getSpeed()));

        actualizarHUD();
        enCombate = true;
        ejecutarTurno();
    }

    private void ejecutarTurno() {
        try {
            if (!enCombate) return;

            if (controlador.combateFinalizado(personajes)) {
                mostrarMensaje(controlador.mensajeFinal(personajes));
                terminarCombate();
                return;
            }

            if (turnoActual >= personajes.size()) turnoActual = 0;

            Characters actual = personajes.get(turnoActual);

            if (controlador.estaDerrotado(actual)) {
                turnoActual = controlador.siguienteTurno(personajes, turnoActual);
                ejecutarTurno();
                return;
            }

            mostrarMensaje(controlador.ejecutarTurno(actual, personajes));

            if (controlador.esTurnoDeJugador(actual)) {
                habilitarBotones(true);
            } else {
                SwingUtilities.invokeLater(() -> {
                    try { Thread.sleep(800); } catch (InterruptedException ignored) {}
                    mostrarMensaje(controlador.ejecutarTurno(actual, personajes));
                    turnoActual = controlador.siguienteTurno(personajes, turnoActual);
                    ejecutarTurno();
                });
            }
        } catch (Exception e) {
            mostrarMensaje("Error al ejecutar el turno: " + e.getMessage());
        }
    }

    private void accionJugador(String accion) {
        try {
            Characters actual = personajes.get(turnoActual);
            Characters objetivo = controlador.obtenerEnemigoVivo(personajes);

            if (accion.equals("Huir")) {
                mostrarMensaje("Escapas del combate...");
                terminarCombate();
                return;
            }

            if (objetivo == null) return;

            String resultado = controlador.realizarAccion(accion, actual, objetivo);
            mostrarMensaje(resultado);

            if (controlador.estaDerrotado(objetivo)) {
                controlador.marcarDerrotado(objetivo);
                mostrarMensaje(objetivo.getName() + " ha sido derrotado.");
            }

            actualizarHUD();
            habilitarBotones(false);
            turnoActual = controlador.siguienteTurno(personajes, turnoActual);
            ejecutarTurno();
        } catch (Exception e) {
            mostrarMensaje("Error al procesar la acción: " + e.getMessage());
        }
    }


    private Characters obtenerEnemigoVivo() {
        return controlador.obtenerEnemigoVivo(personajes);
    }

    private Characters obtenerHeroeVivo() {
        return controlador.obtenerHeroeVivo(personajes);
    }

    private boolean todosMuertos(boolean heroes) {
        return personajes.stream()
                .filter(p -> (heroes ? p instanceof Heroes : p instanceof Enemy))
                .noneMatch(p -> p.getHP() > 0);
    }

    private void mostrarMensaje(String texto) {
        areaMensajes.append(texto + "\n");
        areaMensajes.setCaretPosition(areaMensajes.getDocument().getLength());
    }

    private void actualizarHUD() {
        panelHUD.removeAll();
        for (Characters p : personajes) {
            if (p instanceof Heroes) {
                panelHUD.add(new JLabel(p.getName() + " HP: " + p.getHP() + "/" + p.getMaxHP(), JLabel.LEFT));
            }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfazCombate::new);
    }
}
