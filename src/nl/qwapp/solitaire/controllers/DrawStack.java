package nl.qwapp.solitaire.controllers;

import nl.qwapp.solitaire.Card;

import java.util.Arrays;
import java.util.LinkedList;

public class DrawStack implements Stack {

    private LinkedList<Card> cards;
    private int cardIndex = -1;

    public DrawStack() {
        cards = new LinkedList<>();
    }

    public boolean hasDrawn() {
        return cardIndex > -1;
    }

    @Override
    public boolean canAddCard(Card card) {
        return false;
    }

    public Card getCurrentCard() {
        if(cardIndex < 0) return null;
        return cards.get(cardIndex);
    }

    @Override
    public void addCards(Card[] cards) {
        this.cards.addAll(Arrays.asList(cards));
        cardIndex = -1;
    }

    public int size() {
        return cards.size();
    }

    public int getCardIndex() {
        return cardIndex;
    }

    @Override
    public Card[] removeCards(Card fromCard) {
        Card removedCard = cards.remove(cardIndex);
        cardIndex--;
        if(cardIndex < 0) {
            cardIndex = -1;
        }

        return new Card[] { removedCard };
    }

    public void nextCard() {
        cardIndex++;
        if(cardIndex >= cards.size()) {
            cardIndex = -1;
        }
    }

    public Card getCard(int i) {
        return cards.get(i);
    }
}
