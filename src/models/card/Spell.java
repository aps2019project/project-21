package models.card;

import models.Player;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private static List<Spell> spells = new ArrayList<>();
    private ApplyType applyType = ApplyType.ON_ATTACKER;
    private TargetType targetType = new TargetType();
    private List<Effect> effects = new ArrayList<>();
    private String desc = "";
    private boolean isCommandValid = true;

    public Spell() {
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

    private List<Cell> getTargetCells(Match match, Player player, Cell cell) {
        List<Cell> cells = new ArrayList<>();
        switch (targetType.getCellType()) {
            case SINGLE_CELL:
                cells.add(cell);
                break;
            case NONE:
                return cells;
            case SQUARE_3_3:
                cells.add(cell);
                break;
            case SQUARE_2_2:
                cells.add(cell);
                break;
            case IN_ROW:
                cells.add(cell);
                break;
            case IN_COLUMN:
                cells.add(cell);
                break;
        }
        return cells;
    }

    private List<Attacker> getTargetAttackers(Match match, Player player, Cell cell) {
        List<Attacker> attackers = new ArrayList<>(match.getBothGroundedAttackers());
        List<Attacker> copy = new ArrayList<>(attackers);

        switch (targetType.getOppOrAlly()) {
            case OPP:
                for (Attacker attacker : copy)
                    if (match.getCardsTeam(attacker) == match.getTeamOfPlayer(player))
                        attackers.remove(attacker);
                break;
            case ALLY:
                for (Attacker attacker : copy)
                    if (match.getCardsTeam(attacker) != match.getTeamOfPlayer(player))
                        attackers.remove(attacker);
                break;
        }

        switch (targetType.getHeroOrMinion()) {
            case MINION:
                for (Attacker attacker : copy)
                    if (!(attacker instanceof Minion))
                        attackers.remove(attacker);
                break;
            case HERO:
                for (Attacker attacker : copy)
                    if (!(attacker instanceof Hero))
                        attackers.remove(attacker);
                break;
        }

        switch (targetType.getChooseType()) {
            case SELECTED_OPP:
                if (cell.isEmpty() || match.getCardsTeam(cell.getCurrentAttacker()) == match.getTeamOfPlayer(player))
                    isCommandValid = false;
                attackers = new ArrayList<>();
                attackers.add(cell.getCurrentAttacker());
                break;
            case SELECTED_ALLY:
                if (cell.isEmpty() || match.getCardsTeam(cell.getCurrentAttacker()) != match.getTeamOfPlayer(player))
                    isCommandValid = false;
                attackers = new ArrayList<>();
                attackers.add(cell.getCurrentAttacker());
                break;
        }
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

    public void reset() {
        super.reset();
    }
}
