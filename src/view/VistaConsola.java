package view;
import java.util.List;
import java.util.Scanner;

public class VistaConsola {
    private final Scanner sc = new Scanner(System.in);


    public int showMainMenu() {
        System.out.println("===================================");
        System.out.println(" ¡Bienvenido al RPG de Josue! ");
        System.out.println("===================================");
        System.out.println("1. Empezar combate");
        System.out.println("2. Ver estadísticas de héroes");
        System.out.println("3. Ver estadísticas de enemigos");
        System.out.println("4. Salir");
        System.out.print("Elige una opción: ");
        return readInt();
    }


    public void showHeroesList(List<models.Characters> heroes) {
        System.out.println("-- Héroes --");
            for (models.Characters h : heroes) System.out.println(h);
    }


    public void showEnemiesList(List<models.Characters> enemies) {
        System.out.println("-- Enemigos --");
        for (models.Characters e : enemies) System.out.println(e);
    }


    public void showMessage(String msg) {
        System.out.println(msg);
    }


    public void showStatus(List<models.Characters> heroes, List<models.Characters> enemies) {
        System.out.println("Estado actual:");
        for (models.Characters h : heroes) {
            System.out.println(h.getName() + " - HP: " + h.getHP() + "/" + h.getMaxHP() +
            " (" + h.getHPPercentage() + "%), MP: " + h.getMP() + "/" + h.getMaxMP() +
            " (" + h.getMPPercentage() + "%)");
        }
        for (models.Characters e : enemies) {
            System.out.println(e.getName() + " - HP: " + e.getHP() + "/" + e.getMaxHP() +
                " (" + e.getHPPercentage() + "%), MP: " + e.getMP() + "/" + e.getMaxMP() +
                " (" + e.getMPPercentage() + "%)");
        }
    }
    public int promptPlayerAction(models.Characters attacker) {
        System.out.println("\nTurno de: " + attacker.getName());
        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. Atacar");
        System.out.println("2. Defender");
        System.out.println("3. Usar habilidad");
        System.out.println("4. Huir");
        System.out.print("Elige una opción: ");
        return readInt();
    }


    public int promptTargetChoice(List<models.Characters> group) {
        System.out.println("Elige objetivo:");
        int i = 1;
        for (models.Characters c : group) {
        System.out.println(i + ". " + c.getName() + " - HP: " + c.getHP() + "/" + c.getMaxHP());
        i++;
        }
        System.out.print("Selecciona número (o 0 para cancelar): ");
        return readInt();
    }


    public void pause() {
        try {
        Thread.sleep(800);
        } catch (InterruptedException e) {
        // ignore
        }
    }


    private int readInt() {
        while (true) {
            try {
                String line = sc.nextLine();
                return Integer.parseInt(line.trim());
            } catch (Exception e) {
                System.out.print("Entrada inválida. Intenta de nuevo: ");
            }
        }
    }
}


