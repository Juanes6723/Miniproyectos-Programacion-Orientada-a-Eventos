import java.util.HashMap;
import java.util.Map;

public class DefineSkills {
    // Clase para definir las habilidades con sus propiedades
    private static final Map<SkillsList, Skills> skillMap = new HashMap<>();

    static {
        skillMap.put(SkillsList.FIREBALL, new Skills("Fireball", 40, 10));
        skillMap.put(SkillsList.HEAL, new Skills("Heal", -30, 8)); // negativo para curar
        skillMap.put(SkillsList.SWEET_BREATH, new Skills("Sweet Breath", 0, 20));
        skillMap.put(SkillsList.DRAGON_FIRE, new Skills("Dragon Fire", 70, 15));
    }

    public static Skills getSkill(SkillsList skill) {
        return skillMap.get(skill);
    }
}
