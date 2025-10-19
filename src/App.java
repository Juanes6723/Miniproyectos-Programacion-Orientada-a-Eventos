import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        // Habilidades por personaje 
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

        // Habilidades del MINIBOSS
        ArrayList<String> skillsMiniBoss = new ArrayList<>();
        skillsMiniBoss.add("DRAGON_FIRE");

        // Crear héroes con atributos reales
        Heroes hero = new Heroes("Heroe", 100, 50, 20, 10, 15, 1.5f, 0.2f, 25, skillsHero);
        Heroes yangus = new Heroes("Yangus", 120, 40, 25, 15, 10, 1.3f, 0.15f, 10, skillsYangus);
        Heroes jessica = new Heroes("Jessica", 90, 60, 18, 8, 12, 1.4f, 0.25f, 30, skillsJessica);
        Heroes angelo = new Heroes("Angelo", 110, 45, 22, 12, 14, 1.2f, 0.18f, 20, skillsAngelo);

        // Crear enemigos con tipo y habilidades
        Enemy limo = new Enemy("Limo", 80, 30, 15, 8, 12, 1.2f, 0.1f, 20, skillsSorcerer, TypeEnemy.SORCERER);
        Enemy berenjeno = new Enemy("Berenjeno", 70, 40, 10, 10, 8, 1.1f, 0.1f, 25, skillsSorcerer, TypeEnemy.HEALER);
        Enemy pinchorugo = new Enemy("Pinchorugo", 90, 20, 18, 12, 10, 1.3f, 0.15f, 15, skillsSorcerer, TypeEnemy.DEFENSIVE);

        // Crear MINIBOSS
        Enemy miniBoss = new Enemy("Dragón Rojo", 200, 100, 35, 20, 18, 2.0f, 0.3f, 40, skillsMiniBoss, TypeEnemy.MINI_BOSS);

        // Listas de personajes
        ArrayList<Characters> heroes = new ArrayList<>();
        heroes.add(hero);
        heroes.add(yangus);
        heroes.add(jessica);
        heroes.add(angelo);

        ArrayList<Characters> enemies = new ArrayList<>();
        enemies.add(limo);
        enemies.add(berenjeno);
        enemies.add(pinchorugo);

        // Orden de ataque por velocidad
        ArrayList<Characters> ordenAtaque = new ArrayList<>();
        ArrayList<Characters> todos = new ArrayList<>();
        todos.addAll(heroes);
        todos.addAll(enemies);

        while (!todos.isEmpty()) {
            Characters fastest = todos.get(0);
            for (Characters c : todos) {
                if (c.getSpeed() > fastest.getSpeed()) {
                    fastest = c;
                }
            }
            ordenAtaque.add(fastest);
            todos.remove(fastest);
        }

        System.out.println("Orden de ataque:");
        for (Characters c : ordenAtaque) {
            System.out.println(c.getName() + " (Velocidad: " + c.getSpeed() + ")");
        }

        System.out.println("\n¡Has entrado en combate!");
        System.out.println("Enemigos: ");
        for (Characters e : enemies) {
            System.out.println("- " + e.getName());
        }

        boolean inCombat = true;

        while (inCombat) {
            ArrayList<Characters> vivos = new ArrayList<>();
            for (Characters c : ordenAtaque) {
                if (c.getHP() > 0) vivos.add(c);
            }

            for (Characters atacante : vivos) {
                if (!inCombat) break;

                System.out.println("\nTurno de: " + atacante.getName());

                if (atacante.processStatusEffects()) continue;

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
                if (heroes.contains(atacante)) {
                    // Turno del jugador
                    System.out.println("¿Qué deseas hacer?");
                    System.out.println("1. Atacar");
                    System.out.println("2. Defender");
                    System.out.println("3. Usar habilidad");
                    System.out.println("4. Huir");
                    System.out.print("Elige una opción: ");
                    int opcion = sc.nextInt();

                    Characters objetivo = null;
                    for (Characters e : enemies) {
                        if (e.getHP() > 0) {
                            objetivo = e;
                            break;
                        }
                    }

                    if (objetivo == null) {
                        System.out.println("No hay enemigos vivos.");
                        inCombat = false;
                        break;
                    }

                    boolean enemigosNormalesVivos = enemies.stream().filter(e -> ((Enemy) e).getType() != TypeEnemy.MINI_BOSS).anyMatch(e -> e.getHP() > 0);

                    boolean miniBossYaAgregado = enemies.contains(miniBoss);

                    if (!enemigosNormalesVivos && !miniBossYaAgregado) {
                        System.out.println("\n¡Un rugido sacude el campo de batalla!");
                        System.out.println("¡" + miniBoss.getName() + " ha aparecido!");

                        enemies.add(miniBoss);
                        ordenAtaque.add(miniBoss);
                        miniBossYaAgregado = true;
                    }

                    switch (opcion) {
                        case 1:
                            atacante.attack(objetivo);
                            break;
                        case 2:
                            atacante.defend();
                            break;
                        case 3:
                            if (!atacante.getSkills().isEmpty()) {
                                String skillName = atacante.getSkills().get(0);
                                SkillsList skillEnum = SkillsList.valueOf(skillName);
                                atacante.useSkill(skillEnum, objetivo);
                            } else {
                                System.out.println("No tienes habilidades.");
                            }
                            break;
                        case 4:
                            System.out.println(atacante.getName() + " ha huido del combate.");
                            inCombat = false;
                            break;
                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }

                } else {
                    // Turno del enemigo automático
                    Characters objetivo = null;
                    for (Characters h : heroes) {
                        if (h.getHP() > 0) {
                            objetivo = h;
                            break;
                        }
                    }

                    if (objetivo == null) {
                        System.out.println("No hay héroes vivos.");
                        inCombat = false;
                        break;
                    }

                    ((Enemy) atacante).takeTurn(objetivo);
                }

                // Verificar si el combate terminó
                boolean heroesVivos = heroes.stream().anyMatch(h -> h.getHP() > 0);
                boolean enemigosVivos = enemies.stream().anyMatch(e -> e.getHP() > 0);

                if (!heroesVivos || !enemigosVivos) {
                    inCombat = false;
                    System.out.println("\n¡El combate ha terminado!");
                    if (heroesVivos) {
                        System.out.println("¡Los héroes han ganado!");
                    } else {
                        System.out.println("Los enemigos han vencido...");
                    }
                    break;
                }

                Thread.sleep(1000); // pausa visual
            }
        }

        sc.close();
    }
}
