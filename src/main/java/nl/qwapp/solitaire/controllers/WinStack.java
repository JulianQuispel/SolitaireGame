package nl.qwapp.solitaire.controllers;

import nl.qwapp.solitaire.Card;
import nl.qwapp.solitaire.CardType;

public class WinStack implements Stack {
    private CardType cardType;
    private Card[] cards;
    private int index = -1;

    public WinStack(CardType cardType) {
        this.cardType = cardType;
        cards = new Card[Card.MAX_VALUE];
    }

    public boolean canAddCard(Card card) {
        return card.getCardType() == cardType && (card.getValue() == 1 ||
                (index > -1 && card.getValue() - cards[index].getValue() == 1));
    }

    public Card getCurrentCard() {
        if(index < 0) return null;
        return cards[index];
    }

    @Override
    public void addCards(Card[] cards) {
        index++;
        System.arraycopy(cards, 0, this.cards, index, cards.length);
    }

    @Override
    public Card[] removeCards(Card fromCard) {
        index--;
        return new Card[] { cards[index+1] };
    }
}
