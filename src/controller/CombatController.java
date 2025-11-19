package controller;

import models.Characters;
import models.Enemy;
import models.Heroes;
import models.SkillsList;

import java.util.List;

public class CombatController {

    public boolean esTurnoDeJugador(Characters actual) {
        return actual instanceof Heroes;
    }

    public boolean estaDerrotado(Characters personaje) {
        return personaje.getHP() <= 0;
    }

    public void marcarDerrotado(Characters personaje) {
        personaje.setHP(0);
    }

    public boolean combateFinalizado(List<Characters> personajes) {
        return todosMuertos(personajes, true) || todosMuertos(personajes, false);
    }

    public String mensajeFinal(List<Characters> personajes) {
        if (todosMuertos(personajes, false)) return "¡Has ganado el combate!";
        if (todosMuertos(personajes, true)) return "¡Todos los héroes han caído!";
        return "";
    }

    public int siguienteTurno(List<Characters> personajes, int turnoActual) {
        int total = personajes.size();
        for (int i = 1; i <= total; i++) {
            int siguiente = (turnoActual + i) % total;
            if (personajes.get(siguiente).getHP() > 0) return siguiente;
        }
        return 0; // fallback
    }

    public String ejecutarTurno(Characters actual, List<Characters> personajes) {
        if (estaDerrotado(actual)) return actual.getName() + " está fuera de combate.";
        if (actual.processStatusEffects()) return actual.getName() + " está dormido y pierde el turno.";

        if (actual instanceof Enemy) {
            Characters objetivo = obtenerHeroeVivo(personajes);
            if (objetivo == null) return "No hay héroes vivos.";
            return ((Enemy) actual).takeTurn(objetivo);
        }

        return "Turno de " + actual.getName();
    }

    public String realizarAccion(String accion, Characters actual, Characters objetivo) {
        try {
            return switch (accion) {
                case "Atacar" -> actual.attack(objetivo);
                case "Curar" -> actual.heal(20);
                case "Especial" -> actual.useSkill(SkillsList.FIREBALL, objetivo);
                default -> "Acción no reconocida.";
            };
        } catch (Exception e) {
            return "Error al ejecutar la acción: " + e.getMessage();
        }
    }

    public Characters obtenerHeroeVivo(List<Characters> personajes) {
        return personajes.stream()
                .filter(p -> p instanceof Heroes && p.getHP() > 0)
                .findFirst()
                .orElse(null);
    }

    public Characters obtenerEnemigoVivo(List<Characters> personajes) {
        return personajes.stream()
                .filter(p -> p instanceof Enemy && p.getHP() > 0)
                .findFirst()
                .orElse(null);
    }

    private boolean todosMuertos(List<Characters> personajes, boolean heroes) {
        return personajes.stream()
                .filter(p -> (heroes ? p instanceof Heroes : p instanceof Enemy))
                .noneMatch(p -> p.getHP() > 0);
    }
}
