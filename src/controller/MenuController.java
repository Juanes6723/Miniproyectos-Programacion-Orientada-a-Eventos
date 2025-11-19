package controller;

import view.InterfazCombate;
import view.InterfazPersonajes;
import view.InterfazEnemigos;

public class MenuController {

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
