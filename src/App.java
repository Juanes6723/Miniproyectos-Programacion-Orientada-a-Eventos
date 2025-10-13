import java.util.ArrayList;
import java.util.List; 

//ejemplo para verificar que todo este funcionando 

public class App {
    public static void main(String[] args) throws Exception{

        The_Hero the_Hero = new The_Hero("The Hero",22, 0, 8, 6, 6, 1.5f, 3, 3.0f, 5, "");
        Yangus yangus = new Yangus("Yangus", 25, 0, 18, 12, 6, 2.0f, 8, 3.0f, 5, "");
        
        Enemy limo = new Enemy("Limo", 3, 5, 5, 3, 2, 1f, 1, 2f, 2, "", TypeEnemy.AGGRESSIVE);
        limo.addSkills("Placaje");

        Enemy berenjeno = new Enemy("Berenjeno", 5, 10, 2, 3, 6, 1f, 2, 1f, 1, "", TypeEnemy.HEALER);
        berenjeno.addSkills("Cura Total");

        Enemy pinchorugo = new Enemy("Pinchorugo", 10, 5, 2, 10, 1, 3f, 1, 2f, 1, "", TypeEnemy.DEFENSIVE);
        pinchorugo.addSkills("Defensa férrea");

        Enemy labibabosa = new Enemy("Labibabosa", 10, 10, 50, 2, 2, 5f, 5, 1f, 1, "", TypeEnemy.SORCERER);
        labibabosa.addSkills("Bola de fuego");

        ArrayList<Characters> enemy = new ArrayList<>();
        enemy.add(limo);
        enemy.add(berenjeno);
        enemy.add(pinchorugo);
        enemy.add(labibabosa);


        ArrayList <Characters> listaCharacters = new ArrayList<Characters>();
        
        listaCharacters.add(the_Hero);
        listaCharacters.add(yangus);

        //codigo para verificar que todo este en orden 

        System.out.println(listaCharacters);

        System.out.println("\n Enemy:");
        System.out.println(enemy);
    }
}