package models;
import java.util.ArrayList;
import java.util.Random;

public class Enemy extends Characters {

    private TypeEnemy type; // Se guarda el tipo de enemigo
    private Random random; // Randomiza el tipo de accion que utiliza
    
    public Enemy(String name, int hP, int mP, int atack, int defense, int speed, float crit_Damage, float crit_Change,
            int magic_Attack, ArrayList<String> skills, TypeEnemy type) {
        super(name, hP, mP, atack, defense, speed, crit_Damage, crit_Change, magic_Attack, skills);
        this.type = type;
        this.random = new Random();
    }
    
    public TypeEnemy getType() {
        return type;
    }

    public void setType(TypeEnemy type) {
        this.type = type;
    }

    
    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }
    
    public String decideAction(){ // Acciones de los enemigos
        switch (type) {
            case AGGRESSIVE:
                return "attack";
            case HEALER:
                return getHP() < getMaxHP() * 0.4 ? "heal" : "attack";
            case DEFENSIVE:
                return random.nextBoolean() ? "defend" : "attack";
            case SORCERER:
                return getMP() >= 5 ? "Use skill" : "attack";
            case MINI_BOSS:
                if (getHP() < getMaxHP() * 0.3 && getMP() >= 8) {
                    return "Use skill"; 
                } else if (getMP() >= 5) {
                    return random.nextBoolean() ? "Use skill" : "attack";
                } else {
                    return "attack";
                }
            default:
                return "attack";
        }
    }

    public String takeTurn(Characters target) {
        if (getHP() <= 0){
            return getName() + " está fuera de combate.";
        }
        if (processStatusEffects()){
            return getName() + " está dormido y pierde el turno.";
        }
        String action = decideAction();
        switch (action) {
            case "attack":
                return attack(target);
            case "defend":
                return defend();
            case "Use skill":
                if (!getSkills().isEmpty()) {
                    int index = random.nextInt(getSkills().size());
                    String skillName = getSkills().get(index);
                    try {
                        SkillsList skillEnum = SkillsList.valueOf(skillName);
                        return useSkill(skillEnum, target);
                    } catch (IllegalArgumentException e) {
                        return getName() + " intentó usar una habilidad inválida.";
                    }
                } else {
                    return getName() + " no tiene habilidades, así que ataca.\n" + attack(target);
                }
            case "heal":
                return heal(20);
            default:
                return getName() + " no hace nada.";
        }
    }

    @Override
    public String toString() {
        return getName() + 
        "/ Type " + type +
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