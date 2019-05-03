package models.card.effects;

import models.card.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Removes Evil Buffs for Ally attacker
 * and removes Good Buffs for Opps
 */
public class PositiveDispel extends Effect {
    public PositiveDispel() {
        super();
    }

    public void apply() {
        if (cell == null && attacker == null)
            return;
        if (match == null || player == null)
            return;
        Attacker attacker;
        if (cell != null)
            attacker = cell.getAttacker();
        else
            attacker = this.attacker;
        if (attacker == null)
            return;

        List<Effect> effects = new ArrayList<>(attacker.getAppliedEffects());
        if (match.isInTeam(attacker, player)) {
            for (Effect effect : effects) {
                try {
                    if (((Buff) effect).getBuffMode().equals(BuffMode.EVIL))
                        attacker.removeEffect(effect);
                } catch (Exception e) {

                }
            }
        } else {
            for (Effect effect : effects) {
                try {
                    if (((Buff) effect).getBuffMode().equals(BuffMode.GOOD))
                        attacker.removeEffect(effect);
                } catch (Exception e) {

                }
            }
        }
    }
}
