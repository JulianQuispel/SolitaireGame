package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.Card;
import nl.qwapp.solitaire.controllers.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.function.Consumer;

public class MyCanvas extends Canvas implements MouseListener, MouseMotionListener {
    private Game game;
    private ImageMap imageMap;
    private HashMap<ClickRegion, Consumer<ClickRegion>> clickMap = new HashMap<>();
    private HashMap<ClickRegion, Card> cardMap = new HashMap<>(); // Maps click regions to cards
    private Card draggedCard = null;
    private int dragOffsetX;
    private int dragOffsetY;
    private boolean isDragging = false;
    private int dragStartX;
    private int dragStartY;
    
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
        addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        DrawStack drawStack = game.getDrawStack();

        clickMap = new HashMap<>();
        cardMap = new HashMap<>();
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
            cardMap.put(
                    new ClickRegion(140, 20, 140 + ImageMap.IMG_WIDTH, 20 + ImageMap.IMG_HEIGHT),
                    drawStack.getCurrentCard()
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
                cardMap.put(
                        new ClickRegion(minX, minY, minX + ImageMap.IMG_WIDTH, minY + ImageMap.IMG_HEIGHT),
                        card
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
                    cardMap.put(
                            new ClickRegion(minX, minY, minX + ImageMap.IMG_WIDTH, maxY),
                            card
                    );
                }

                Image image = imageMap.getImage(imageName);
                g.drawImage(image, minX, minY, this);
            }
        }
        
        // If we're dragging a card, show it at the mouse position
        if (isDragging && draggedCard != null) {
            int mouseX = dragStartX;
            int mouseY = dragStartY;
            g.drawImage(imageMap.getImage(ImageMap.getCardKey(draggedCard)), mouseX - dragOffsetX, mouseY - dragOffsetY, this);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Check if we're clicking on a card that can be dragged
        for (ClickRegion region : clickMap.keySet()) {
            if (mouseX >= region.minX && mouseX <= region.maxX && mouseY >= region.minY && mouseY <= region.maxY) {
                // Store which card we're dragging
                Card card = cardMap.get(region);
                if (card != null) {
                    draggedCard = card;
                    isDragging = true;
                    dragStartX = mouseX;
                    dragStartY = mouseY;
                    dragOffsetX = mouseX - region.minX;
                    dragOffsetY = mouseY - region.minY;
                    repaint();
                    return;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // If we were dragging, check if we dropped on a valid target
        if (isDragging && draggedCard != null) {
            // For now, just clear the dragging state - we're keeping existing click behavior
            isDragging = false;
            draggedCard = null;
            repaint();
            return;
        }

        // Handle regular click
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
    public void mouseDragged(MouseEvent e) {
        if (isDragging && draggedCard != null) {
            // Update drag start position to follow mouse
            dragStartX = e.getX();
            dragStartY = e.getY();
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}