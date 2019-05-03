package models.match;

import models.Deck;
import models.Hand;
import models.Item.Collectable;
import models.Player;
import models.card.Attacker;
import models.card.Card;
import models.card.Hero;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatchInfo {
    private static final int MAX_MANA = 9;

    private int mp = 3;
    private Deck deck;
    private Hand hand;
    private List<Card> graveyard = new ArrayList<>();
    private List<Collectable> achievedCollectables = new ArrayList<>();
    private List<Attacker> groundedAttackers = new ArrayList<>();

    PlayerMatchInfo(Player player) {
        deck = Deck.copyDeck(player.getCollection().getMainDeck());
        hand = Hand.extractHand(deck);
    }

    public List<Card> getGraveyard() {
        return graveyard;
    }

    public int getMp() {
        return mp;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Attacker> getGroundedAttackers() {
        return groundedAttackers;
    }

    public Hand getHand() {
        return hand;
    }

    public void increaseMana(int value) {
        this.mp += value;
    }

    public Hero getHero() {
        for (Attacker attacker : groundedAttackers)
            if (attacker.getClass().equals(Hero.class))
                return (Hero) attacker;
        return null;
    }
}