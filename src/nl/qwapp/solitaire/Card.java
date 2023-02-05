package nl.qwapp.solitaire;

public class Card {
    public static final int MAX_VALUE = 13;

    private CardType cardType;
    private int value;

    public Card(CardType cardType, int value) {
        this.cardType = cardType;
        this.value = value;
    }

    public CardType getCardType() {
        return this.cardType;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return cardType + ":" + value;
    }
}
