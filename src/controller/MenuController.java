package controller;

import view.InterfazCombate;
import view.InterfazPersonajes;
import view.InterfazEnemigos;
import view.InterfazInicio;

public class MenuController {
    private final InterfazInicio view;

    public MenuController(InterfazInicio view) {
        this.view = view;
    }
    public void abrirVistaInicio() {
    }
    public static void abrirVistaCombate() {
        new InterfazCombate();
    }

    public static void abrirVistaPersonajes() {
        new InterfazPersonajes();
    }

    public static void abrirVistaEnemigos() {
        new InterfazEnemigos();
    }
}
