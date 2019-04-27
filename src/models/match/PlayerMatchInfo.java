package models.match;

import models.Deck;
import models.Hand;
import models.Item.Collectable;
import models.Player;
import models.card.Attacker;
import models.card.Card;

import java.util.ArrayList;
import java.util.List;

public class PlayerMatchInfo {
    private static final int MAX_MANA = 9;

    private int mana = 3;
    private Deck deck;
    private Hand hand;
    private List<Card> graveyard = new ArrayList<>();
    private List<Collectable> achievedCollectables = new ArrayList<>();
    private List<Attacker> groundedAttackers = new ArrayList<>();

    PlayerMatchInfo(Player player) {
        this.deck = player.getCollection().getMainDeck();
        hand = Hand.extractHand(deck);
    }
}