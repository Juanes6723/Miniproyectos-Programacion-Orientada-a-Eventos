import java.util.ArrayList;
import java.util.List; 
import java.util.Scanner;


//ejemplo para verificar que todo este funcionando 

public class App {
    public static void main(String[] args) throws Exception{
        Scanner sc = new Scanner(System.in);

        // Listas donde se almacenaran las habilidades de cada personaje y de los enemigos segun su tipo de comportamiento

        ArrayList<String> skillsThe_Hero = new ArrayList<>();
        skillsThe_Hero.add("lightning jump");

        ArrayList<String> skillsYangus = new ArrayList<>();
        skillsYangus.add("Stun Axe");

        ArrayList<String> skillsJessica = new ArrayList<>();
        skillsJessica.add("hellfire");

        ArrayList<String> skillsAngelo = new ArrayList<>();
        skillsAngelo.add("falcon cut");

        ArrayList<String> skillsAggressive = new ArrayList<>();
        skillsAggressive.add("Placaje");

        ArrayList<String> skillsHealer = new ArrayList<>();
        skillsHealer.add("Cura Total");

        ArrayList<String> skillsDefensive = new ArrayList<>();
        skillsDefensive.add("Defensa Ferrea");

        ArrayList<String> skillsSorcerer = new ArrayList<>();
        skillsSorcerer.add("Bola de Fuego");

        // Creacion de los personajes principales y enemigos con sus atributos predeterminados de nivel 1

        The_Hero the_Hero = new The_Hero("The Hero",22, 0, 8, 6, 6, 1.5f, 3, 3.0f, 5, skillsThe_Hero);
        Yangus yangus = new Yangus("Yangus", 25, 0, 18, 12, 6, 2.0f, 8, 3.0f, 5, skillsYangus);
        Jessica jessica = new Jessica("Jessica",16, 10, 6, 6, 10, 1.2f, 7, 3.0f, 12, skillsJessica);
        Angelo angelo = new Angelo("Angelo", 18, 8, 8, 8, 9, 1.3f, 9, 3.0f, 11, skillsAngelo);

        
        Enemy limo = new Enemy("Limo", 3, 5, 5, 3, 2, 1f, 1, 2f, 2, skillsAggressive, TypeEnemy.AGGRESSIVE);
        Enemy berenjeno = new Enemy("Berenjeno", 5, 10, 2, 3, 6, 1f, 2, 1f, 1, skillsHealer, TypeEnemy.HEALER);
        Enemy pinchorugo = new Enemy("Pinchorugo", 10, 5, 2, 10, 1, 3f, 1, 2f, 1, skillsDefensive, TypeEnemy.DEFENSIVE);
        Enemy labibabosa = new Enemy("Labibabosa", 10, 10, 50, 2, 2, 5f, 5, 1f, 1, skillsSorcerer, TypeEnemy.SORCERER);

        // Listas con todos los personajes y enemigos 

        ArrayList<Characters> enemy = new ArrayList<>();
        enemy.add(limo);
        enemy.add(berenjeno);
        enemy.add(pinchorugo);
        enemy.add(labibabosa);


        ArrayList <Characters> listaCharacters = new ArrayList<Characters>();    
        listaCharacters.add(the_Hero);
        listaCharacters.add(yangus);
        listaCharacters.add(jessica);
        listaCharacters.add(angelo);
        
        // Lista segun el orden de ataque

        ArrayList <Characters> OrdenAtaque = new ArrayList<Characters>();

        // Lista con TODOS los personajes

        ArrayList <Characters> todos = new ArrayList<Characters>();
        todos.add(the_Hero);
        todos.add(yangus);
        todos.add(jessica);
        todos.add(angelo);
        todos.add(limo);
        todos.add(berenjeno);
        todos.add(pinchorugo);
        todos.add(labibabosa);

        while(!todos.isEmpty()){
            Characters mosFast = todos.get(0);

            for(Characters c : todos){
                if(c.getSpeed() > mosFast.getSpeed()){
                    mosFast = c;
                }
            }

            OrdenAtaque.add(mosFast);

            todos.remove(mosFast);
            }


            System.out.println("orden de ataque");
            for (Characters c : OrdenAtaque){
            System.out.println(c.getName() + "(velocidad: " + c.getSpeed() + ")");
            }

        boolean inCombat = true;

    
        // codigo para verificar que todo este en orden 
    
        // System.out.println(listaCharacters);
    
        // System.out.println("\n Enemy:");
        // System.out.println(enemy);

        System.out.println("Has entrado en combate");
        System.out.println("Te enfrentas a: " + enemy.get(0).getName() + " " + enemy.get(1).getName() + " " + enemy.get(2).getName() + " " + enemy.get(3).getName());
        System.out.println("Primero en atacar sera: " + OrdenAtaque.get(0).getName());

        while(inCombat){

            System.out.println("\n¿Qué deseas hacer?");
            System.out.println("1. Atacar");
            System.out.println("2. Defenderse");
            System.out.println("3. Usar habilidad");
            System.out.println("4. Huir");
            System.out.print("Elige una opción: ");

            int opcion = sc.nextInt();

            switch (opcion) {
                case 1:
                    
                    
                    break;
            
                default:
                    break;
            }
        }


    }

}