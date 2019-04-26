package models.card;

import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Card {
    private TargetType targetType;
    private Effect effect;
    private String description;

//    public Spell(String name) {
//        super(name);
//    }

    public void castSpell(Match match, Cell targetCell) {
        List<Cell> targetCells = getTargetCells(match, targetCell);
        if (targetCells == null || targetCells.isEmpty())
            return;
        for (Cell cell : targetCells) {
            Attacker target = cell.getAttacker();
            if (target != null)
                target.addEffect(effect);
        }
    }

    private List<Cell> getTargetCells(Match match, Cell target) {
        List<Cell> cells = new ArrayList<>();
        switch (targetType) {
            case HIMSELF:
//                cells.add(match.getSelectedCard().getCurrentCell());
                break;
            case SINGLE_OPP:
                cells.add(target);
                break;
            case ALL_OPPS:
                cells.addAll(match.getOppCells());
                break;
            case SINGLE_CELL:
                cells.add(target);
                break;
            case OPPS_IN_ROW:
//                cells.addAll(match.getOppCellsInRow(match.getSelectedCard().getCurrentCell()));
                break;
        }
        return cells;
    }
}
