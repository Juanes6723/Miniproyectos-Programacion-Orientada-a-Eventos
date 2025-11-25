package controller;
import java.util.ArrayList;
import java.util.List;
import models.Characters;
import models.Enemy;
import models.TypeEnemy;


public class EnemyController {


private List<Characters> enemigos;


public EnemyController() {
enemigos = new ArrayList<>();
enemigos.add(new Enemy("Limo", 80, 30, 15, 8, 12, 1.2f, 0.1f, 20, new ArrayList<>(List.of("SWEET_BREATH")), TypeEnemy.SORCERER));
enemigos.add(new Enemy("Berenjeno", 70, 40, 10, 10, 8, 1.1f, 0.1f, 25, new ArrayList<>(List.of("SWEET_BREATH")), TypeEnemy.HEALER));
enemigos.add(new Enemy("Pinchorugo", 90, 20, 18, 12, 10, 1.3f, 0.15f, 15, new ArrayList<>(List.of("SWEET_BREATH")), TypeEnemy.DEFENSIVE));
enemigos.add(new Enemy("Dragón Rojo", 200, 100, 35, 20, 18, 2.0f, 0.3f, 40, new ArrayList<>(List.of("DRAGON_FIRE")), TypeEnemy.MINI_BOSS));
}


public Characters getEnemy(int index) {
return enemigos.get(index);
}


public List<Characters> getAllEnemies() {
return enemigos;
}
}