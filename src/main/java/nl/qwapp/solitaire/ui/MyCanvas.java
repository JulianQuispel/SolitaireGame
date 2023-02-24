package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.Card;
import nl.qwapp.solitaire.controllers.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.function.Consumer;

public class MyCanvas extends Canvas implements MouseListener {
    private Game game;
    private ImageMap imageMap;
    private HashMap<ClickRegion, Consumer<ClickRegion>> clickMap = new HashMap<>();

    class ClickRegion {
        public int minX;
        public int minY;
        public int maxX;
        public int maxY;

        public ClickRegion(int minX, int minY, int maxX, int maxY) {
            this.minX = minX;
            this.minY = minY;
            this.maxX = maxX;
            this.maxY = maxY;
        }
    }

    public MyCanvas(Game game) {
        this.game = game;
        imageMap = new ImageMap();
        setBackground(Color.green);

        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        DrawStack drawStack = game.getDrawStack();

        clickMap = new HashMap<>();
        g.drawImage(imageMap.getImage("back"), 20, 20, this);
        clickMap.put(
                new ClickRegion(20, 20, 20 + ImageMap.IMG_WIDTH, 20 + ImageMap.IMG_HEIGHT),
                (ClickRegion region) -> drawStack.nextCard()
        );

        // Cards from draw stack
        if (drawStack.hasDrawn()) {
            int cardIndex = drawStack.getCardIndex();
            int maxIndex = Math.min(2, cardIndex);
            for (int i = 0; i < maxIndex; i++) {
                g.drawImage(imageMap.getImage(ImageMap.getCardKey(drawStack.getCard(cardIndex - maxIndex + i))), 140 + 20 * (maxIndex - i), 20, this);
            }

            g.drawImage(imageMap.getImage(
                    ImageMap.getCardKey(drawStack.getCurrentCard()
                    )), 140, 20, this);
            clickMap.put(
                    new ClickRegion(140, 20, 140 + ImageMap.IMG_WIDTH, 20 + ImageMap.IMG_HEIGHT),
                    (ClickRegion region) -> game.moveCards(drawStack.getCurrentCard(), game.getDrawStack())
            );
        }

        WinStack[] winStacks = game.getWinStacks();
        for (int x = 0; x < 4; x++) {
            int minX = 380 + x * 120;
            int minY = 20;
            Card card = winStacks[x].getCurrentCard();

            g.drawImage(imageMap.getImage(ImageMap.getCardKey(card)), minX, minY, this);
            int finalX = x;
            if (winStacks[x].getCurrentCard() != null) {
                clickMap.put(
                        new ClickRegion(minX, minY, minX + ImageMap.IMG_WIDTH, minY + ImageMap.IMG_HEIGHT),
                        (ClickRegion region) -> game.moveCards(card, winStacks[finalX])
                );
            }
        }

        for (int x = 0; x < HiddenStack.NUMBER_OF_STACKS; x++) {
            HiddenStack hiddenStack = game.getHiddenStacks()[x];

            for (int y = 0; y < hiddenStack.getCardCount(); y++) {
                Card card = hiddenStack.getCard(y);
                String imageName = "back";
                if (y >= hiddenStack.getOpenIndex()) {
                    imageName = ImageMap.getCardKey(card);
                }

                int minX = 20 + x * 120;
                int minY = 200 + y * 40;
                int maxY = y == hiddenStack.getCardCount() - 1 ? minY + ImageMap.IMG_HEIGHT : minY + 40;

                if (y >= hiddenStack.getOpenIndex()) {
                    clickMap.put(
                            new ClickRegion(minX, minY, minX + ImageMap.IMG_WIDTH, maxY),
                            (ClickRegion region) -> game.moveCards(card, hiddenStack)
                    );
                }

                Image image = imageMap.getImage(imageName);
                g.drawImage(image, minX, minY, this);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (ClickRegion region : clickMap.keySet()) {
            if (mouseX >= region.minX && mouseX <= region.maxX && mouseY >= region.minY && mouseY <= region.maxY) {
                Consumer<ClickRegion> callable = clickMap.get(region);
                callable.accept(region);
                repaint();
                return;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
