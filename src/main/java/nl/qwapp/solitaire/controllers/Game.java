package nl.qwapp.solitaire.controllers;

import nl.qwapp.solitaire.Card;
import nl.qwapp.solitaire.CardType;

import java.util.Arrays;
import java.util.Random;

public class Game {
    Stack[] stacks;

    public Game() {
        stacks = new Stack[12];
    }

    public void start() {
        Card[] cards = makeCards();
        shuffleCards(cards);

        for (int i = 0; i < 4; i++) {
            stacks[i] = new WinStack(CardType.values()[i]);
        }

        int i = 0;

        // Add cards to hidden stacks
        for (int x = 0; x < 7; x++) {
            HiddenStack hiddenStack = new HiddenStack();
            for (int y = 0; y < x + 1; y++) {
                hiddenStack.addCard(cards[i]);
                i++;
            }
            hiddenStack.setOpenIndex(x);
            stacks[x + 4] = hiddenStack;
        }

        DrawStack drawStack = new DrawStack();
        drawStack.addCards(Arrays.copyOfRange(cards, i, cards.length - 1));
        stacks[11] = drawStack;
    }

    private Card[] makeCards() {
        Card[] cards = new Card[52];

        int x = 0;
        for (CardType cardType : CardType.values()) {
            for (int i = 0; i < Card.MAX_VALUE; i++) {
                cards[x * Card.MAX_VALUE + i] = new Card(cardType, i + 1);
            }
            x++;
        }

        return cards;
    }

    private void shuffleCards(Card[] cards) {
        Random rand = new Random();
        for (int i = 0; i < cards.length - 1; i++) {
            int index = rand.nextInt(i + 1);

            Card g = cards[index];
            cards[index] = cards[i];
            cards[i] = g;
        }
    }

    public WinStack[] getWinStacks() {
        WinStack[] winStacks = new WinStack[4];

        for (int i = 0; i < 4; i++) {
            winStacks[i] = (WinStack) stacks[i];
        }

        return winStacks;
    }

    public HiddenStack[] getHiddenStacks() {
        HiddenStack[] hiddenStacks = new HiddenStack[7];

        for (int i = 0; i < 7; i++) {
            hiddenStacks[i] = (HiddenStack) stacks[i+4];
        }

        return hiddenStacks;
    }

    public DrawStack getDrawStack() {
        return (DrawStack) stacks[11];
    }

    public void moveCards(Card firstCard, Stack fromStack) {
        for (Stack stack : this.stacks) {
            if (!stack.equals(fromStack) && stack.canAddCard(firstCard)) {
                Card[] cards = fromStack.removeCards(firstCard);
                stack.addCards(cards);
                return;
            }
        }
    }
}
