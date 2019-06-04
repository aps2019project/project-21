package models.Item;

import models.Player;
import models.card.*;
import models.match.Cell;
import models.match.Match;

import java.util.ArrayList;
import java.util.List;

public class Usable extends Item {
    private static List<Usable> usables = new ArrayList<>();

    public Usable(String name, int price, TargetType targetType,
                  Effect effect, ApplyType applyType, String desc) {
        super(name, price, targetType, effect, applyType, desc);
    }

    public Usable(String name, int price, TargetType targetType,
                  List<Effect> effects, ApplyType applyType, String desc) {
        super(name, price, targetType, effects, applyType, desc);
    }

    public void castItem(Match match, Player player, Cell cell) {
        if (this.getApplyType().equals(ApplyType.ON_ATTACKER)) {
            List<Attacker> attackers = getTargetAttackers(match, player, cell);
            for (Attacker attacker : attackers)
                if (attacker != null)
                    attacker.addEffect(effects);
        } else {
            Match match1 = Match.getCurrentMatch();
            effects.get(0).apply();
        }
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

    public static List<Usable> getUsables() {
        return usables;
    }

    private static void addUsable(Usable usable) {
        if (usable == null)
            return;
        usables.add(usable);
    }

    public static void addUsable(List<Usable> usables) {
        if (usables == null)
            return;
        for (Usable usable : usables)
            addUsable(usable);
    }

    public void reset() {
        super.reset();
    }
}
