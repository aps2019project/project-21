package models.match;

import json.CardMaker;
import models.Deck;
import models.Hand;
import models.Item.Collectable;
import models.Player;
import models.card.Attacker;
import models.card.Card;
import models.card.Effect;
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
    private List<Effect> effects = new ArrayList<>();

    PlayerMatchInfo(Player player) {
        deck = CardMaker.deepCopy(player.getCollection().getMainDeck(), Deck.class);
        deck.shuffle();
        hand = Hand.extractHand(deck);
    }

    public List<Card> getGraveyard() {
        return graveyard;
    }

    public List<Collectable> getAchievedCollectables() {
        return achievedCollectables;
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
        for (Card card : deck.getAllCards())
            if (card instanceof Hero && ((Attacker) card).isAlive())
                return (Hero) card;
        return null;
    }

    public void addEffect(Effect effect) {
        if (effect == null)
            return;
        effects.add(effect);
    }

    public void addEffect(List<Effect> effects) {
        if (effects == null)
            return;
        for (Effect effect : effects)
            addEffect(effect);
    }

    public void reset() {
        deck.reset();
    }
}