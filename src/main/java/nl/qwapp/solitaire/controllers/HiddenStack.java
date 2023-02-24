package nl.qwapp.solitaire.controllers;

import nl.qwapp.solitaire.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class HiddenStack implements Stack {
    public static final int NUMBER_OF_STACKS = 7;

    LinkedList<Card> cards = new LinkedList<>();
    private int openIndex = -1;

    public boolean canAddCard(Card card) {
        if(cards.size() == 0) return card.getValue() == Card.MAX_VALUE;

        Card lastCard = cards.getLast();
        return
                lastCard.getValue() - card.getValue() == 1 &&
                        lastCard.getCardType().ordinal() % 2 != card.getCardType().ordinal() % 2;
    }

    public void setOpenIndex(int openIndex) {
        this.openIndex = openIndex;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getOpenIndex() {
        return openIndex;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int getCardCount() {
        return cards.size();
    }

    public Card getCurrentCard() {
        if(openIndex < 0) return null;
        return cards.get(openIndex);
    }

    @Override
    public void addCards(Card[] cards) {
        this.cards.addAll(Arrays.asList(cards));
    }

    @Override
    public Card[] removeCards(Card fromCard) {
        int index = cards.indexOf(fromCard);
        int cardSize = cards.size();
        Card[] removedCards = new Card[cards.size() - index];

        for(int i = 0; i + index < cardSize; i++) {
            Card card = cards.remove(index);
            removedCards[i] = card;
        }

        openIndex = Math.min(openIndex, cards.size() - 1);

        return removedCards;
    }
}
