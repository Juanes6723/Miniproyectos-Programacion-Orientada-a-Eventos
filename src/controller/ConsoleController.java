package controller;
import java.util.ArrayList;
import java.util.List;
import models.Characters;
import models.Enemy;
import models.Heroes;
import models.SkillsList;
import models.TypeEnemy;
import view.VistaConsola;

public class ConsoleController {
    private List<Characters> heroes;
    private List<Characters> enemies;
    private List<Characters> attackOrder;
    private Enemy miniBoss;
    private boolean miniBossAlreadyAdded = false;

    private final VistaConsola view;

    public ConsoleController(VistaConsola view) {
        this.view = view;
    }

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
        view.showMessage("\n¡Has entrado en combate!");
        for (Characters e : enemies) {
            view.showMessage("- " + e.getName());
        }
        boolean inCombat = true;
        while (inCombat) {
            ArrayList<Characters> alive = new ArrayList<>();
            for (Characters c : attackOrder) {
                if (c.getHP() > 0) alive.add(c);
            }
            for (Characters attacker : alive) {
                if (!inCombat) break;
                if (attacker.processStatusEffects()) continue;
                view.showStatus(heroes, enemies);
                if (heroes.contains(attacker)) {
                    inCombat = turnPlayer(attacker);
                } else {
                    turnEnemy((Enemy) attacker);
                }
                if (verifyEndCombat()) {
                    inCombat = false;
                    break;
                }
                view.pause();
            }
        }
    }

    private boolean turnPlayer(Characters attacker) {
        int option = view.promptPlayerAction(attacker);

        // Preparar lista de objetivos vivos
        List<Characters> aliveEnemies = new ArrayList<>();
        for (Characters e : enemies) if (e.getHP() > 0) aliveEnemies.add(e);
        if (aliveEnemies.isEmpty()) {
            view.showMessage("No hay enemigos vivos.");
            return false;
        }

        Characters target = null;
        if (!aliveEnemies.isEmpty()) {
            int choice = view.promptTargetChoice(aliveEnemies);
            if (choice > 0 && choice <= aliveEnemies.size()) {
                target = aliveEnemies.get(choice - 1);
            } else {
                view.showMessage("Acción cancelada o selección inválida.");
                return true; // seguir combate
            }
        }

        switch (option) {
            case 1:
                attacker.attack(target);
                view.showMessage(attacker.getName() + " ataca a " + target.getName());
                break;
            case 2:
                attacker.defend();
                view.showMessage(attacker.getName() + " se defiende.");
                break;
            case 3:
                if (!attacker.getSkills().isEmpty()) {
                    String skillName = attacker.getSkills().get(0);
                    SkillsList skillEnum = SkillsList.valueOf(skillName);
                    attacker.useSkill(skillEnum, target);
                    view.showMessage(attacker.getName() + " usa " + skillName + " sobre " + target.getName());
                } else {
                    view.showMessage("No tienes habilidades.");
                }
                break;
            case 4:
                view.showMessage(attacker.getName() + " ha huido del combate.");
                return false;
            default:
                view.showMessage("Opción inválida.");
                break;
        }
        verifyMiniBoss();
        return true;
    }

    private void turnEnemy(Enemy attacker) {
        Characters target = chooseTarget(heroes);
        if (target == null) {
            view.showMessage("No hay héroes vivos.");
            return;
        }
        attacker.takeTurn(target);
        view.showMessage(attacker.getName() + " ataca a " + target.getName());
    }

    private Characters chooseTarget(List<Characters> group) {
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
            view.showMessage("¡" + miniBoss.getName() + " ha aparecido!");

            enemies.add(miniBoss);
            attackOrder.add(miniBoss);
            miniBossAlreadyAdded = true;
        }
    }

    private boolean verifyEndCombat() {
        boolean livingHeroes = heroes.stream().anyMatch(h -> h.getHP() > 0);
        boolean livingEnemies = enemies.stream().anyMatch(e -> e.getHP() > 0);

        if (!livingHeroes || !livingEnemies) {
            view.showMessage("\n¡El combate ha terminado!");
            if (livingHeroes) {
                view.showMessage("¡Los héroes han ganado!");
            } else {
                view.showMessage("Los enemigos han vencido...");
            }
            return true;
        }
        return false;
    }

    // Métodos para acceder desde la vista principal
    public void runMenu() {
        boolean exit = false;
        while (!exit) {
            int opcion = view.showMainMenu();
            switch (opcion) {
                case 1:
                    creation();
                    miniBossAlreadyAdded = false;
                    start();
                    break;
                case 2:
                    if (heroes != null) view.showHeroesList(heroes);
                    else view.showMessage("No hay héroes cargados. Comienza un combate primero.");
                    break;
                case 3:
                    if (enemies != null) view.showEnemiesList(enemies);
                    else view.showMessage("No hay enemigos cargados. Comienza un combate primero.");
                    break;
                case 4:
                    view.showMessage("¡Gracias por jugar!");
                    exit = true;
                    break;
                default:
                    view.showMessage("Opción inválida.");
                    break;
            }
            System.out.println();
        }
    }
}

