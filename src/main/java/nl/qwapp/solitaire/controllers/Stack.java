package nl.qwapp.solitaire.controllers;

import nl.qwapp.solitaire.Card;

import java.util.List;

public interface Stack {
    boolean canAddCard(Card card);
    Card getCurrentCard();
    void addCards(Card[] cards);
    Card[] removeCards(Card fromCard);
}
