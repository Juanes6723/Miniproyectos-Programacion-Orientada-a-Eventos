import view.InterfazInicio;
import view.VistaConsola;
import controller.MenuController;
// import java.awt.Menu;
// import java.lang.ModuleLayer.Controller;
import java.util.Scanner;

import controller.ConsoleController;

public class App {

    public static void IniciarConsola(){
        VistaConsola view = new VistaConsola();
        ConsoleController controller = new ConsoleController(view);
        controller.runMenu();
    }

    public static void IniciarInterfazGrafica(){
        InterfazInicio view = new InterfazInicio();
        MenuController controller = new MenuController(view);
        controller.abrirVistaInicio();
    }

    public static void main(String[] args) {
    Scanner scanner1 = new Scanner(System.in);
        
        System.out.println("¿De qué manera desea iniciar el juego?");
        System.out.println("1. Por consola");
        System.out.println("2. Por interfaz gráfica");
        System.out.print("Elija una opción: ");

        int opcion1 = scanner1.nextInt();
        

        if (opcion1 == 1) {
            IniciarConsola();
    }

        else if (opcion1== 2) {
            IniciarInterfazGrafica();
    }

        else {
            System.out.println("Opción inválida. Saliendo del programa.");
        return;
        }
    }
}

