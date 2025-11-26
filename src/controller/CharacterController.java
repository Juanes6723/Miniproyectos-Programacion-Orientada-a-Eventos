package controller;
import models.Characters;
import models.Heroes;
import java.util.ArrayList;
import java.util.List;

public class CharacterController {

    private ArrayList<Characters> personajes;

    public CharacterController() {
        personajes = new ArrayList<>();

        personajes.add(new Heroes("Heroe", 100, 50, 20, 10, 15, 1.5f, 0.2f, 25, 
            new ArrayList<>(List.of("FIREBALL"))));

        personajes.add(new Heroes("Yangus", 120, 40, 25, 15, 10, 1.3f, 0.15f, 10, 
            new ArrayList<>(List.of("SWEET_BREATH"))));

        personajes.add(new Heroes("Jessica", 90, 60, 18, 8, 12, 1.4f, 0.25f, 30, 
            new ArrayList<>(List.of("HEAL"))));

        personajes.add(new Heroes("Angelo", 110, 45, 22, 12, 14, 1.2f, 0.18f, 20, 
            new ArrayList<>(List.of("FIREBALL"))));
    }

    public Characters getPersonaje(int index) {
        return personajes.get(index);
    }

    public ArrayList<Characters> getPersonajes() {
        return personajes;
    }
}