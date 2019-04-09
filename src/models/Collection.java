package models;

import models.Card.Card;
import models.Card.Hero;
import models.Item.Item;

import java.util.ArrayList;
import java.util.List;

public class Collection {
    private List<Deck> decks = new ArrayList<>();
    private Deck mainDeck;
    private List<Card> cards = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
}