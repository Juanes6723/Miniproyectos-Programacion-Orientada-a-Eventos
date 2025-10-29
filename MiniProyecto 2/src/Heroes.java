import java.util.ArrayList;

public class Heroes extends Characters {

    public Heroes(String name, int hP, int mP, int atack, int defense, int speed, float crit_Damage, float crit_Change,
            int magic_Attack, ArrayList<String> skills) {
        super(name, hP, mP, atack, defense, speed, crit_Damage, crit_Change, magic_Attack, skills);
    }
    
    public String toString() {
        return getName() +
                "/ HP " + getHP() +
                "/ MP " + getMP() +
                "/ Atack " + getAtack() +
                "/ Magic Atack " + getMagic_Attack() +
                "/ Defense " + getDefense() +
                "/ Speed " + getSpeed() +
                "/ Crit Damage " + getCrit_Damage() +
                "/ Crit Chance " + getCrit_Change() +
                "/ Abilities " + getSkills() + "\n";
    }
}
