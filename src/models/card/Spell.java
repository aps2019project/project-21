package models.card;

import models.Player;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private ApplyType applyType = ApplyType.ON_ATTACKER;
    private static List<Spell> spells = new ArrayList<>();
    private TargetType targetType = new TargetType();
    private List<Effect> effects = new ArrayList<>();
    private String desc = "";

    public Spell(){
    }

    public Spell(String name, int price, int manaCost, TargetType targetType,
                 List<Effect> effects, ApplyType applyType, String desc) {
        super(name, price, manaCost);
        this.targetType = targetType;
        this.effects = effects;
        this.desc = desc;
        this.applyType = applyType;
    }

    public Spell(String name, int price, int manaCost, TargetType targetType,
                 Effect effect, ApplyType applyType, String desc) {
        super(name, price, manaCost);
        this.targetType = targetType;
        this.effects.add(effect);
        this.desc = desc;
        this.applyType = applyType;
    }

    public void castSpell(Match match, Player player, Cell targetCell) {
        if (applyType.equals(ApplyType.ON_ATTACKER)) {
            List<Attacker> attackers = getTargetAttackers(match, player, targetCell);
            for (Attacker attacker : attackers)
                if (attacker != null)
                    attacker.addEffect(effects);
        } else {
            List<Cell> cells = getTargetCells(match, player, targetCell);
            for (Cell cell : cells)
                if (cell != null)
                    cell.addEffect(effects);
        }
    }

    private List<Cell> getTargetCells(Match match, Player player, Cell targetCell) {
        List<Cell> cells = new ArrayList<>();
        return cells;
    }

    private List<Attacker> getTargetAttackers(Match match, Player player, Cell targetCell) {
        List<Attacker> attackers = new ArrayList<>(match.getAllGroundedAttacker());
        List<Attacker> copy = new ArrayList<>(attackers);
        return attackers;
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
