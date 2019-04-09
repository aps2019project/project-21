package models;

import models.Card.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int HAND_CAPACITY = 5;
    private List<Card> cards = new ArrayList<>();
    private Card next;
}
