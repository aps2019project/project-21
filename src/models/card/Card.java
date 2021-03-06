package models.card;

import models.Item.Item;
import models.Item.Usable;
import models.Player;
import models.match.Cell;
import models.match.Match;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {
    private static final long serialVersionUID = 6529685098267757028L;

    private static List<Card> customCards = new ArrayList<>();
    private static int cardCount = 100;
    protected String name;
    protected int id;
    protected int collectionID;
    protected String cardIDInGame;
    protected int price;
    protected int manaCost;
    protected Cell currentCell;
    protected int count;  // = capacity

    public Card() {

    }

    public Card(String name, int price, int manaCost) {
        this.name = name.replaceAll("\\s+", "_");
        this.price = price;
        this.manaCost = manaCost;
    }

    {
        this.id = cardCount;
        cardCount++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getPrice() {
        return price;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getCardIDInGame() {
        return cardIDInGame;
    }

    public void setCardIDInGame(String cardIDInGame) {
        this.cardIDInGame = cardIDInGame;
    }

    public static void setCardIDInGame(Player player, Hero hero) {
        String cardIDInGame = player.getUsername() + "_" +
                hero.getName() + "_1";
        hero.setCardIDInGame(cardIDInGame);
    }

    public static void setCardIDInGame(Player player, Attacker attacker) {
        String cardIDInGame = player.getUsername() + "_" +
                attacker.getName() + "_1";
        attacker.setCardIDInGame(cardIDInGame);
    }


    public void setCollectionID() {
        this.collectionID = cardCount;
        cardCount++;
    }

    public boolean TwoCardAreSame(Card card) {
        return true;
    }

    public static List<Card> getCards() {
        List<Card> cards = new ArrayList<>();
        cards.addAll(Attacker.getAttackers());
        cards.addAll(Spell.getSpells());
        cards.addAll(Item.getItems());
        return cards;
    }

    public static Card getCardByID(String id) {
        if (!id.matches("\\d+"))
            return null;
        return getCardByID(Integer.parseInt(id));
    }

    public static Card getCardByIDInGame(String id) {
        List<Card> cards = Match.getCurrentMatch().getPlayersMatchInfo()[0].getAllCards();
        List<Card> cards1 = Match.getCurrentMatch().getPlayersMatchInfo()[1].getAllCards();
        for (Card card : cards) {
            if (card.getCardIDInGame() == null)
                continue;
            if (card.getCardIDInGame().equals(id))
                return card;
        }
        for (Card card : cards1) {
            if (card.getCardIDInGame() == null)
                continue;
            if (card.getCardIDInGame().equals(id))
                return card;
        }
        return null;
    }

    public static Card getCardByID(int id) {
        return getCardByID(id, getCards());
    }

    public static Card getCardByName(String name) {
        return getCardByName(name, getCards());
    }

    public static Card getCardByName(String name, List<Card> cards) {
        for (Card card : cards)
            if (card.name.equals(name))
                return card;
        return null;
    }

    public static List<Card> getAllCardByName(String name, List<Card> cards) {
        List<Card> cardList = new ArrayList<>();
        for (Card card : cards)
            if (card.name.equals(name))
                cardList.add(card);
        return cardList;
    }

    public static Card getCardByID(int id, List<Card> cards) {
        for (Card card : cards)
            if (card.id == id)
                return card;
        return null;
    }

    public int getCollectionID() {
        return collectionID;
    }

    public String getDesc() {
        if (this instanceof Hero)
            return ((Hero) this).getDesc();
        else if (this instanceof Minion)
            return ((Minion) this).getDesc();
        else if (this instanceof Spell)
            return ((Spell) this).getDesc();  // these casts are probably not redundant!
        else if (this instanceof Usable)
            return ((Usable) this).getDesc();
        //  shouldn't reach here
        return null;
    }

    public void reset() {
        currentCell = null;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }

    public boolean hasCardInGameID() {
        return cardIDInGame != null;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public int getCount() {
        return count;
    }

    public void decreaseCount() {
        count = Math.max(0, count - 1);
    }

    public void increaseCount() {
        count++;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static void addCustomCard(Card card) {
        if (card == null)
            return;
        customCards.add(card);
        if (card instanceof Hero)
            Hero.addHero((Hero) card);
        else if (card instanceof Minion)
            Minion.addMinion((Minion) card);
        else if (card instanceof Spell)
            Spell.addSpell((Spell) card);
        else if (card instanceof Usable)
            Usable.addUsable((Usable) card);
    }

    public static List<Card> getCustomCards() {
        return customCards;
    }
}