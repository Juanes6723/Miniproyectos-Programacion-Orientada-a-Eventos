import java.util.ArrayList;
import java.util.List; 

//ejemplo para verificar que todo este funcionando 

public class App {
    public static void main(String[] args) throws Exception{

        The_Hero the_Hero = new The_Hero("The Hero",22, 0, 8, 6, 6, 1.5f, 3, 3.0f, 5, "");
        Yangus yangus = new Yangus("Yangus", 25, 0, 18, 12, 6, 2.0f, 8, 3.0f, 5, "");

        ArrayList <Characters> listaCharacters = new ArrayList<Characters>();
        
        listaCharacters.add(the_Hero);
        listaCharacters.add(yangus);

        //codigo para verificar que todo este en orden 

        System.out.println(listaCharacters);

    }
}