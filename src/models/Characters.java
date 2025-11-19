package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Characters {
    private String Name;
    private int HP, maxHP, MP, maxMP;
    private int Atack, Magic_Attack, Defense, Speed;
    private float Crit_Damage, Crit_Change;
    private ArrayList<String> Skills;
    private List<Stateeffects> statusEffects = new ArrayList<>();

    public Characters(String name, int hP, int mP, int atack, int defense, int speed,
                      float crit_Damage, float crit_Change, int magic_Attack, ArrayList<String> skills) {
        this.Name = name;
        this.HP = this.maxHP = hP;
        this.MP = this.maxMP = mP;
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

    public void setName(String name) { 
        Name = name; 
    }

    public int getHP() {
         return HP; 
    }

    public void setHP(int hP) {
    HP = Math.max(0, Math.min(hP, maxHP));
    }

    public int getMP() {
        return MP;
    }
    public void setMP(int mP) {
        MP = Math.max(0, Math.min(mP, maxMP));
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public int getHPPercentage() {
        return (int) ((HP * 100.0) / maxHP);
    }

    public int getMPPercentage() {
        return (int) ((MP * 100.0) / maxMP);
    }

    public int getAtack() {
        return Atack;
    }

    public void setAtack(int atack) {
        Atack = atack;
    }

    public int getMagic_Attack() {
        return Magic_Attack;
    }

    public void setMagic_Attack(int magic_Attack) {
        Magic_Attack = magic_Attack;
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

    public float getCrit_Change() {
        return Crit_Change;
    }

    public void setCrit_Change(float crit_Change) {
        Crit_Change = crit_Change;
    }

    public ArrayList<String> getSkills() {
        return Skills;
    }

    public void setSkills(ArrayList<String> skills) {
        Skills = skills;
    }

    public List<Stateeffects> getStatusEffects() {
        return statusEffects;
    }

    public void addStatusEffect(Stateeffects effect) {
        if (!statusEffects.contains(effect)){
            statusEffects.add(effect);
        }
    }

    public void removeStatusEffect(Stateeffects effect) {
        statusEffects.remove(effect);
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> data = new ArrayList<>();
        data.add(getName());
        data.add(String.valueOf(getHP()));
        data.add(String.valueOf(getMaxHP()));
        data.add(String.valueOf(getMP()));
        data.add(String.valueOf(getMaxMP()));
        data.add(String.valueOf(getAtack()));
        data.add(String.valueOf(getMagic_Attack()));
        data.add(String.valueOf(getDefense()));
        data.add(String.valueOf(getSpeed()));
        data.add(String.valueOf(getCrit_Damage()));
        data.add(String.valueOf(getCrit_Change()));
        data.add(getSkills() != null ? String.join(", ", getSkills()) : "");
        if (getStatusEffects() != null && !getStatusEffects().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Stateeffects e : getStatusEffects()) {
                if (sb.length() > 0) sb.append(", ");
                sb.append(e.toString());
            }
            data.add(sb.toString());
        } else {
            data.add("");
        }
        return data;
    }

    public String attack(Characters target) {
        int damage = Math.max(1, Atack - target.getDefense());
        boolean crit = Math.random() < Crit_Change;
        if (crit) {
            damage *= Crit_Damage;
        }
        target.setHP(target.getHP() - damage);
        String msg = Name + " atacó a " + target.getName() + " y causó " + damage + " de daño.";
        return crit ? Name + " hizo un golpe crítico!\n" + msg : msg;
    }

    public String defend() {
        Defense += 5;
        return Name + " se defiende y aumenta su defensa a " + Defense;
    }

    public String heal(int amount) {
        if (HP <= 0) {
            return Name + " no puede curarse porque está fuera de combate.";
            }
        setHP(HP + amount);
        return Name + " se cura " + amount + " puntos de vida.";
    }

    public String useSkill(SkillsList skillEnum, Characters target) {
        Skills skill = DefineSkills.getSkill(skillEnum);
        if (skill == null) {
            return "No tienes esa habilidad.";
            }
        if (MP < skill.getManaCost()) {
            return Name + " no tiene suficiente MP para usar " + skill.getName();
        } 
        MP -= skill.getManaCost();
        int damage = skill.getDamage();
        StringBuilder msg = new StringBuilder();

        if (damage < 0) {
            msg.append(heal(-damage));
        } else {
            boolean crit = Math.random() < Crit_Change;
            if (crit) {
                damage *= Crit_Damage;
            }
            target.setHP(target.getHP() - damage);
            msg.append(Name).append(" usa ").append(skill.getName())
               .append(" contra ").append(target.getName())
               .append(" y causa ").append(damage).append(" de daño.");
            if (crit) {
                msg.insert(0, "¡Golpe crítico con habilidad!\n");
            }
        }

        if (skillEnum == SkillsList.SWEET_BREATH) {
            if (Math.random() < 0.5) {
                target.addStatusEffect(Stateeffects.SLEEP);
                msg.append("\n").append(target.getName()).append(" se ha quedado dormido.");
            } else {
                msg.append("\n").append(target.getName()).append(" resistió el sueño.");
            }
        }

        return msg.toString();
    }

    public boolean processStatusEffects() {
        if (statusEffects.contains(Stateeffects.SLEEP)) {
            if (new Random().nextBoolean()) {
                removeStatusEffect(Stateeffects.SLEEP);
                return false;
            }
            return true;
        }
        return false;
    }
}
