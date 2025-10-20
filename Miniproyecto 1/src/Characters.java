import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Characters {
    // se ponen todos los atributos que tendran en comun los personajes 
    private String Name;
    private int HP;
    private int maxHP;
    private int MP;
    private int maxMP;
    private int Atack;
    private int Magic_Attack;
    private int Defense;
    private int Speed;
    private float Crit_Damage;
    private float Crit_Change;
    private ArrayList<String> Skills;
    private List<Stateeffects> statusEffects = new ArrayList<>(); // Lista de efectos de estado

    public Characters(String name,int hP, int mP, int atack, int defense, int speed,
                      float crit_Damage, float crit_Change, int magic_Attack, ArrayList<String> skills) {
        this.Name = name;
        this.HP = hP;
        this.maxHP = hP;
        this.MP = mP;
        this.maxMP = mP;
        this.Atack = atack;
        this.Magic_Attack = magic_Attack;
        this.Defense = defense;
        this.Speed = speed;
        this.Crit_Damage = crit_Damage;
        this.Crit_Change = crit_Change;
        this.Skills = skills;
    }
    
    
    public String getName() {
        return Name;
    }

    public ArrayList<String> getSkills() {
        return Skills;
    }

    public void setSkills(ArrayList<String> skills) {
        Skills = skills;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getHP() {
        return HP;
    }
    
    public void setHP(int hP) {
        this.HP = Math.max(0, Math.min(hP, maxHP)); // no exceder el maxHP
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int mP) {
        this.MP = Math.max(0, Math.min(mP, maxMP)); // no exceder el maxMP
    }

    // Porcentaje de vida actual (0 a 100)
    public int getHPPercentage() {
        return (int) ((getHP() * 100.0) / getMaxHP());
    }

    // Porcentaje de maná actual (0 a 100)
    public int getMPPercentage() {
        return (int) ((getMP() * 100.0) / getMaxMP());
    }

    public int getAtack() {
        return Atack;
    }

    public void setAtack(int atack) {
        Atack = atack;
    }

    public int getDefense() {
        return Defense;
    }

    public void setDefense(int defense) {
        Defense = defense;
    }

    public int getSpeed() {
        return Speed;
    }

    public void setSpeed(int speed) {
        Speed = speed;
    }

    public float getCrit_Damage() {
        return Crit_Damage;
    }

    public void setCrit_Damage(float crit_Damage) {
        Crit_Damage = crit_Damage;
    }
    public int getMagic_Attack() {
        return Magic_Attack;
    }
    public void setMagic_Attack(int magic_Attack) {
        Magic_Attack = magic_Attack;
    }
    public float getCrit_Change() {
        return Crit_Change;
    }
    public void setCrit_Change(float crit_Change) {
        Crit_Change = crit_Change;
    }

    public List<Stateeffects> getStatusEffects() {
        return statusEffects;
    }

    public void addStatusEffect(Stateeffects effect) { 
        if (!statusEffects.contains(effect)) {
            statusEffects.add(effect);
        }
    }

    public void removeStatusEffect(Stateeffects effect) {
        statusEffects.remove(effect);
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public void attack(Characters target) {
        int damage = Atack - target.getDefense();
        if (damage < 1) {
            damage = 1;
        }
        double chance = Math.random();
        boolean crit = false;
        if (chance < Crit_Change) {
            crit = true;
            damage = (int)(damage * Crit_Damage);
        }
        int newHP = target.getHP() - damage;
        target.setHP(newHP);
        if (crit) {
            System.out.println(Name + " hizo un golpe crítico!");
        }
        System.out.println(Name + " atacó a " + target.getName() + 
        " y causó " + damage + " de daño.");  
  }

    public void defend() {
        this.Defense += 5; // defensa temporal
        System.out.println(this.getName() + " se defiende y aumenta su defensa a " + this.Defense);
    }

    public void useSkill(SkillsList skillEnum, Characters target) {
        // Obtener la habilidad desde DefineSkills
        Skills skill = DefineSkills.getSkill(skillEnum);
        if (skill == null) {
            System.out.println("No tienes esa habilidad.");
            return;
        }
        if (MP < skill.getManaCost()) {
            System.out.println(Name + " no tiene suficiente MP para usar " + skill.getName());
            return;
        }
        MP = MP - skill.getManaCost();
        int damage = skill.getDamage();
        if (damage < 0) {
            // Curacion de los personajes
            int healAmount = -damage;
            heal(healAmount);
            System.out.println(Name + " usa " + skill.getName() + " y se cura " + healAmount + " HP.");
        } else {
            // Daño con la probabilidad de crítico
            boolean crit = Math.random() < Crit_Change;
            if (crit) {
                damage = (int)(damage * Crit_Damage);
                System.out.println("¡Golpe crítico con habilidad!");
            }
            target.setHP(target.getHP() - damage);
            System.out.println(Name + " usa " + skill.getName() + " contra " + target.getName() + " y causa " + damage + " de daño.");
        }
        // Efectos adicionales de habilidades específicas
        if (skillEnum == SkillsList.SWEET_BREATH) {
            boolean duerme = Math.random() < 0.5;
            if (duerme) {
                target.addStatusEffect(Stateeffects.SLEEP);
                System.out.println(target.getName() + " se ha quedado dormido.");
            } else {
                System.out.println(target.getName() + " resistió el sueño.");
            }
        }
    }

    public boolean processStatusEffects() { 
        // Revisa los efectos de estado que tiene el personaje, se pueden varios efectos en el futuro
        if (statusEffects.contains(Stateeffects.SLEEP)) {
            boolean wakesUp = new Random().nextBoolean();
            if (wakesUp) {
                System.out.println(getName() + " se despierta del sueño.");
                removeStatusEffect(Stateeffects.SLEEP);
            } else {
                System.out.println(getName() + " está dormido y pierde el turno.");
                return true; // pierde el turno
            }
        }
        return false; // puede actuar
    }

    // Método para curar al personaje
    public void heal(int amount) {
        if (getHP() <= 0) {
            System.out.println(getName() + " no puede curarse porque está fuera de combate.");
            return;
        }
        setHP(getHP() + amount);
        System.out.println(getName() + " se cura " + amount + " puntos de vida.");
    }

}