package com.blackjack;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    
    private static final String[] suits = {"spades", "hearts", "clubs", "diamonds"};
    private static final String[] values = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
    private final ArrayList<Card> cards;

    //Create and shuffle a new deck
    public Deck() {
        cards = new ArrayList<>();
        for (String suit : suits) {
            for (String value : values) {
                try {
                    cards.add(new Card(suit, value));
                } catch (FileNotFoundException ex) {
                    System.out.println("shouldn't happen");
                }
            }
        }
        this.shuffle();
    }

    //Deals first card in deck if available; return null otherwise
    public Card dealCard() {
        if (cards.isEmpty()) {
            System.out.println("this shouldn't happen");
            return null;
        } else {
            return cards.remove(0);
        }
    }

    //shuffle the deck
    private void shuffle() {
        Collections.shuffle(this.cards);
    }
}
