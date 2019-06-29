package models.match;

import json.CardMaker;
import models.Deck;
import models.Hand;
import models.Item.Collectable;
import models.Player;
import models.card.*;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatchInfo {
    private static final int MAX_MANA = 9;

    private int mp = 7;
    private Deck deck;
    private Hand hand;
    private List<Card> graveyard = new ArrayList<>();
    private List<Collectable> achievedCollectables = new ArrayList<>();
    private List<Attacker> groundedAttackers = new ArrayList<>();
    private List<Spell> usedSpells = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();

    PlayerMatchInfo(Player player) {
        deck = CardMaker.deepCopy(player.getCollection().getMainDeck(), Deck.class);
        deck.shuffle();
        hand = Hand.extractHand(deck);
    }

    List<Card> getGraveyard() {
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

    private void addEffect(Effect effect) {
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

    void addCollectable(Collectable collectable) {
        if (collectable == null)
            return;
        this.achievedCollectables.add(collectable);
    }

    void pushToHand() {
        hand.pushToHandFromDeck(deck);
    }

    void addToGroundedAttackers(Attacker attacker) {
        if (attacker == null)
            return;
        groundedAttackers.add(attacker);
    }

    List<Card> getAllUsedCards() {
        List<Card> cards = new ArrayList<>();
        cards.add(getHero());
        cards.addAll(groundedAttackers);
        cards.addAll(graveyard);
        cards.addAll(usedSpells);
        return cards;
    }

    public List<Card> getAllCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(getAllUsedCards());
        cards.addAll(deck.getAllCards());
        return cards;
    }

    boolean hasManaForThis(Card card) {
        return card.getManaCost() <= mp;
    }

    void decreaseMP(int value) {
        if (value > mp)
            return;
        this.mp -= value;
    }

    public void kill(Attacker attacker) {
        if (attacker == null)
            return;
        groundedAttackers.remove(attacker);
        graveyard.add(attacker);
        attacker.getCurrentCell().setEmpty();
        if (attacker.getFlag() != null) {
            attacker.getFlag().setCurrentCell(attacker.getCurrentCell());
            attacker.getCurrentCell().setFlag(attacker.getFlag());
            attacker.setFlag(null);
        }
    }

    void setMp(int mp) {
        this.mp = Math.min(mp, MAX_MANA);
    }

    private List<Minion> getGroundedMinions() {
        List<Minion> minions = new ArrayList<>();
        for (Attacker attacker : groundedAttackers)
            if (attacker instanceof Minion)
                minions.add((Minion) attacker);
        return minions;
    }

    Minion getGroundedMinionByID(String id) {
        for (Minion minion : getGroundedMinions())
            if (minion.getCardIDInGame().equalsIgnoreCase(id))
                return minion;
        return null;
    }

    Card getGraveyardCard(String cardID) {
        for (Card card : graveyard)
            if (card.getCardIDInGame().equalsIgnoreCase(cardID))
                return card;
        return null;
    }

    int gatheredFlags() {
        int ret = 0;
        for (Attacker attacker : groundedAttackers)
            if (attacker.getFlag() != null)
                ret++;
        return ret;
    }


}