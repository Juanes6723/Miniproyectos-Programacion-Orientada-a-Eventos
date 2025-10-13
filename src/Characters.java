import java.util.ArrayList;

public class Characters {
    // se ponen todos los atributos que tendran en comun los personajes 
    private String Name;
    private int HP;
    private int MP;
    private int Atack;
    private int Defense;
    private int Speed;
    private float Crit_Damage;
    private int Luck;
    private float Precision;
    private int Wisdom;
    private ArrayList<String> Skills;
    
    public Characters(String name,int hP, int mP, int atack, int defense, int speed,
                      float crit_Damage, int luck, float precision,int wisdom, ArrayList<String> skills) {
        this.Name = name;
        this.HP = hP;
        this.MP = mP;
        this.Atack = atack;
        this.Defense = defense;
        this.Speed = speed;
        this.Crit_Damage = crit_Damage;
        this.Luck = luck;
        this.Precision = precision;
        this.Wisdom = wisdom;
        this.Skills = skills;
    }
    public void attack(Characters target) {
        int damage = this.Atack - target.getDefense();
        damage = Math.max(damage, 1); // mínimo 1 de daño
        target.setHP(target.getHP() - damage);
        System.out.println(this.getName() + " ataca a " + target.getName() + " causando " + damage + " de daño.");
    }

    public void defend() {
        this.Defense += 5; // defensa temporal
        System.out.println(this.getName() + " se defiende y aumenta su defensa a " + this.Defense);
    }

    public void useSkill(Characters target) {
        if (Skills.isEmpty()) {
            System.out.println(this.getName() + " no tiene habilidades.");
            return;
        }
        String skill = Skills.get(0); // usa la primera habilidad
        int skillDamage = this.Wisdom + this.Atack;
        target.setHP(target.getHP() - skillDamage);
        System.out.println(this.getName() + " usa " + skill + " contra " + target.getName() + " causando " + skillDamage + " de daño.");
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

    public int getWisdom() {
        return Wisdom;
    }

    public void setWisdom(int wisdom) {
        Wisdom = wisdom;
    }

    public void setHP(int hP) {
        HP = hP;
    }

    public int getMP() {
        return MP;
    }

    public void setMP(int mP) {
        MP = mP;
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

    public int getLuck() {
        return Luck;
    }

    public void setLuck(int luck) {
        Luck = luck;
    }

    public float getPrecision() {
        return Precision;
    }

    public void setPrecision(float precision) {
        Precision = precision;
    }


    
    
}