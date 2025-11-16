import java.util.ArrayList;
import java.util.Scanner;
// import javax.swing.*;
import javax.swing.JOptionPane;
// , "\nTurno de: " + attacker.getName()
public class Game {
    private ArrayList<Characters> heroes;
    private ArrayList<Characters> enemies;
    private ArrayList<Characters> attackOrder;
    private Enemy miniBoss;
    private Scanner sc = new Scanner(System.in);
    private boolean miniBossAlreadyAdded = false;

    public void creation() {
        // Crear habilidades
        ArrayList<String> skillsHero = new ArrayList<>();
        skillsHero.add("FIREBALL");
        ArrayList<String> skillsYangus = new ArrayList<>();
        skillsYangus.add("SWEET_BREATH");
        ArrayList<String> skillsJessica = new ArrayList<>();
        skillsJessica.add("HEAL");
        ArrayList<String> skillsAngelo = new ArrayList<>();
        skillsAngelo.add("FIREBALL");
        ArrayList<String> skillsSorcerer = new ArrayList<>();
        skillsSorcerer.add("SWEET_BREATH");
        ArrayList<String> skillsMiniBoss = new ArrayList<>();
        skillsMiniBoss.add("DRAGON_FIRE");

        // Crea los heroes
        heroes = new ArrayList<>();
        heroes.add(new Heroes("Heroe", 100, 50, 20, 10,
        15, 1.5f, 0.2f, 25, skillsHero));
        heroes.add(new Heroes("Yangus", 120, 40, 25, 15,
        10, 1.3f, 0.15f, 10, skillsYangus));
        heroes.add(new Heroes("Jessica", 90, 60, 18, 8, 
        12, 1.4f, 0.25f, 30, skillsJessica));
        heroes.add(new Heroes("Angelo", 110, 45, 22, 12, 
        14, 1.2f, 0.18f, 20, skillsAngelo));

        // Crea los enemigos
        enemies = new ArrayList<>();
        enemies.add(new Enemy("Limo", 80, 30, 15, 8, 
        12, 1.2f, 0.1f, 20, skillsSorcerer, TypeEnemy.SORCERER));
        enemies.add(new Enemy("Berenjeno", 70, 40, 10, 10,
        8, 1.1f, 0.1f, 25, skillsSorcerer, TypeEnemy.HEALER));
        enemies.add(new Enemy("Pinchorugo", 90, 20, 18, 12,
        10, 1.3f, 0.15f, 15, skillsSorcerer, TypeEnemy.DEFENSIVE));

        // Crea el mini jefe
        miniBoss = new Enemy("Dragón Rojo", 200, 100, 35, 20,
        18, 2.0f, 0.3f, 40, skillsMiniBoss, TypeEnemy.MINI_BOSS);

        // Se define el orden de ataque
        attackOrder = new ArrayList<>();
        ArrayList<Characters> all = new ArrayList<>();
        all.addAll(heroes);
        all.addAll(enemies);

        while (!all.isEmpty()) {
            Characters fastest = all.get(0);
            for (Characters c : all) {
                if (c.getSpeed() > fastest.getSpeed()) {
                    fastest = c;
                }
            }
            attackOrder.add(fastest);
            all.remove(fastest);
        }
    }

    public void start() {
        // System.out.println("\n¡Has entrado en combate!");
        for (Characters e : enemies) {
            System.out.println("- " + e.getName());
        }
        boolean inCombat = true;
        while (inCombat) {
            ArrayList<Characters> alive = new ArrayList<>();
            for (Characters c : attackOrder) {
                if (c.getHP() > 0) alive.add(c);
            }
            for (Characters attacker : alive) {
                if (!inCombat) break;
                JOptionPane.showMessageDialog(null, this, "\nTurno de: " + attacker.getName(), 0);
                if (attacker.processStatusEffects()) continue;
                showStatus();
                if (heroes.contains(attacker)) {
                    inCombat = turnPlayer(attacker);
                } else {
                    turnEnemy((Enemy) attacker);
                }
                if (verifyEndCombat()) {
                    inCombat = false;
                    break;
                }
                pause();
            }
        }
    }

    private void showStatus() {
        System.out.println("Estado actual:");
        for (Characters h : heroes) {
            System.out.println(h.getName() + " - HP: " + h.getHP() + "/" + h.getMaxHP() +
                " (" + h.getHPPercentage() + "%), MP: " + h.getMP() + "/" + h.getMaxMP() +
                " (" + h.getMPPercentage() + "%)");
        }
        for (Characters e : enemies) {
            System.out.println(e.getName() + " - HP: " + e.getHP() + "/" + e.getMaxHP() +
                " (" + e.getHPPercentage() + "%), MP: " + e.getMP() + "/" + e.getMaxMP() +
                " (" + e.getMPPercentage() + "%)");
        }
    }

    private boolean turnPlayer(Characters attacker) {
        System.out.println("¿Qué deseas hacer?");
        System.out.println("1. Atacar");
        System.out.println("2. Defender");
        System.out.println("3. Usar habilidad");
        System.out.println("4. Huir");
        System.out.print("Elige una opción: ");
        int option = sc.nextInt();

        Characters target = chooseTarget(enemies);
        if (target == null) {
            System.out.println("No hay enemigos vivos.");
            return false;
        }

        switch (option) {
            case 1:
                attacker.attack(target);
                break;
            case 2:
                attacker.defend();
                break;
            case 3:
                if (!attacker.getSkills().isEmpty()) {
                    String skillName = attacker.getSkills().get(0);
                    SkillsList skillEnum = SkillsList.valueOf(skillName);
                    attacker.useSkill(skillEnum, target);
                } else {
                    System.out.println("No tienes habilidades.");
                }
                break;
            case 4:
                System.out.println(attacker.getName() + " ha huido del combate.");
                return false;
            default:
                System.out.println("Opción inválida.");
                break;
        }
        verifyMiniBoss();
        return true;
    }

    private void turnEnemy(Enemy attacker) {
        Characters target = chooseTarget(heroes);
        if (target == null) {
            System.out.println("No hay héroes vivos.");
            return;
        }
        attacker.takeTurn(target);
    }

    private Characters chooseTarget(ArrayList<Characters> group) {
        for (Characters c : group) {
            if (c.getHP() > 0) return c;
        }
        return null;
    }

    private void verifyMiniBoss() {
        boolean enemiesNormalAlive = enemies.stream()
            .filter(e -> e instanceof Enemy)
            .filter(e -> ((Enemy) e).getType() != TypeEnemy.MINI_BOSS)
            .anyMatch(e -> e.getHP() > 0);

        if (!enemiesNormalAlive && !miniBossAlreadyAdded) {
            System.out.println("¡" + miniBoss.getName() + " ha aparecido!");

            enemies.add(miniBoss);
            attackOrder.add(miniBoss);
            miniBossAlreadyAdded = true;
        }
}


    private boolean verifyEndCombat() {
        boolean livingHeroes = heroes.stream().anyMatch(h -> h.getHP() > 0);
        boolean livingEnemies = enemies.stream().anyMatch(e -> e.getHP() > 0);

        if (!livingHeroes || !livingEnemies) {
            System.out.println("\n¡El combate ha terminado!");
            if (livingHeroes) {
                System.out.println("¡Los héroes han ganado!");
            } else {
                System.out.println("Los enemigos han vencido...");
            }
            return true;
        }
        return false;
    }

    private void pause() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Error en la pausa.");
        }
    }

    public void showMenu() {
        boolean exit = false;

        while (!exit) {
            System.out.println("===================================");
            System.out.println("     ¡Bienvenido al RPG de Josue!  ");
            System.out.println("===================================");
            System.out.println("1. Empezar combate");
            System.out.println("2. Ver estadísticas de héroes");
            System.out.println("3. Ver estadísticas de enemigos");
            System.out.println("4. Salir");
            System.out.print("Elige una opción: ");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    creation();  // Reinicia héroes, enemigos, miniBoss y orden de ataque
                    miniBossAlreadyAdded = false;  // Reinicia el estado del mini jefe
                    start();
                    break;
                case 2:
                    for (Characters h : heroes) System.out.println(h);
                    break;
                case 3:
                    for (Characters e : enemies) System.out.println(e);
                    break;
                case 4:
                    System.out.println("¡Gracias por jugar!");
                    exit = true;
                    break;
                default:
                    System.out.println("Opción inválida.");
                    break;
            }
            System.out.println();
        }
    }
}