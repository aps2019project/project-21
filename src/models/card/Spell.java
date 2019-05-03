package models.card;

import models.Player;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private static List<Spell> spells = new ArrayList<>();
    private TargetType targetType;
    private List<Effect> effects = new ArrayList<>();
    private String desc;

    Spell() {
        super();
    }

    public Spell(String name, int price, int manaCost, TargetType targetType, List<Effect> effects, String desc) {
        super(name, price, manaCost);
        this.targetType = targetType;
        this.effects = effects;
        this.desc = desc;
    }

    public Spell(String name, int price, int manaCost, TargetType targetType, Effect effect, String desc) {
        super(name, price, manaCost);
        this.targetType = targetType;
        this.effects.add(effect);
        this.desc = desc;
    }

    public void castSpell(Match match, Player player, Cell targetCell) {
        List<Cell> targetCells = getTargetCells(match, player, targetCell);
        if (targetCells == null || targetCells.isEmpty())
            return;
        for (Cell cell : targetCells) {
            Attacker target = cell.getAttacker();
            if (target != null)
                target.addEffect(effects);
        }
    }

    private List<Cell> getTargetCells(Match match, Player player, Cell target) {
        List<Cell> cells = new ArrayList<>();
        //  TODO:  extract targets
        return cells;
    }

    public static List<Spell> getSpells() {
        return spells;
    }

    public static void addSpell(Spell spell) {
        if (spell == null)
            return;
        spells.add(spell);
    }

    public static void addSpell(List<Spell> spells) {
        for (Spell spell : spells)
            addSpell(spell);
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public TargetType getTargetType() {
        return targetType;
    }

    public String getDesc() {
        return desc;
    }
}
