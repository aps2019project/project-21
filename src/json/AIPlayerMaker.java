package json;

import models.AIPlayer;
import models.Deck;
import models.Item.Usable;
import models.card.Card;
import models.card.Hero;

import java.util.ArrayList;
import java.util.List;

public class AIPlayerMaker {
    public static void main() {
        // ---------- 1 ----------
        Hero hero = CardMaker.heroReader("src//json//heroes//dive_sefid.json");
        Usable usable = CardMaker.usableReader("src//json//usables//taje_danaee.json");
        List<Card> cards = new ArrayList<>();
        cards.add(CardMaker.spellReader("src//json//spells//total_disarm.json"));
        cards.add(CardMaker.spellReader("src//json//spells//lighting_bolt.json"));
        cards.add(CardMaker.spellReader("src//json//spells//all_disarm.json"));
        cards.add(CardMaker.spellReader("src//json//spells//all_poison.json"));
        cards.add(CardMaker.spellReader("src//json//spells//dispel.json"));
        cards.add(CardMaker.spellReader("src//json//spells//sacrifice.json"));
        cards.add(CardMaker.spellReader("src//json//spells//shock.json"));

        cards.add(CardMaker.minionReader("src//json//minions//kamandare_fars.json"));
        cards.add(CardMaker.minionReader("src//json//minions//neyzedare_torani.json"));
        cards.add(CardMaker.minionReader("src//json//minions//gorzdare_torani.json"));
        cards.add(CardMaker.minionReader("src//json//minions//gorzdare_torani.json"));
        cards.add(CardMaker.minionReader("src//json//minions//dive_siah.json"));
        cards.add(CardMaker.minionReader("src//json//minions//ghole_tak_cheshm.json"));
        cards.add(CardMaker.minionReader("src//json//minions//mare_sammi.json"));
        cards.add(CardMaker.minionReader("src//json//minions//mare_ghol_peykar.json"));
        cards.add(CardMaker.minionReader("src//json//minions//gorge_sefid.json"));
        cards.add(CardMaker.minionReader("src//json//minions//jadogare_azam.json"));
        cards.add(CardMaker.minionReader("src//json//minions//siavash.json"));
        cards.add(CardMaker.minionReader("src//json//minions//nane_sarma.json"));
        cards.add(CardMaker.minionReader("src//json//minions//arjang_div.json"));

        List<Card> copy = new ArrayList<>(cards);
        for (Card card : copy)
            if (card == null)
                cards.remove(card);

        Deck deck = new Deck("ai1Deck", cards, usable, hero);
        AIPlayer aiPlayer = new AIPlayer(1, deck, 500);
        CardMaker.saveToFile(aiPlayer);


        // ---------- 2 ----------
        hero = CardMaker.heroReader("src//json//heroes//zahak.json");
        usable = CardMaker.usableReader("src//json//usables//soul_eater.json");
        aiPlayer = new AIPlayer(2, deck, 1000);
        CardMaker.saveToFile(aiPlayer);


        // ---------- 3 ----------
        hero = CardMaker.heroReader("src//json//heroes//arash.json");
        usable = CardMaker.usableReader("src//json//usables//terror_hood.json");
        aiPlayer = new AIPlayer(3, deck, 1500);
        CardMaker.saveToFile(aiPlayer);


    }
}
