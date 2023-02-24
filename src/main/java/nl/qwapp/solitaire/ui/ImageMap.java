package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.Card;
import nl.qwapp.solitaire.CardType;

import java.awt.*;
import java.util.HashMap;

public class ImageMap {
    public static final int IMG_WIDTH = 100;
    public static final int IMG_HEIGHT = 144;

    private HashMap<String, Image> imageMap;

    public ImageMap() {
        imageMap = new HashMap<>();

        Toolkit t = Toolkit.getDefaultToolkit();

        imageMap.put("back", t.getImage(getClass().getResource("/resources/card_back.png")));

        for (CardType type : CardType.values()) {
            for (int i = 1; i <= Card.MAX_VALUE; i++) {
                imageMap.put(
                        i + "_" + type.toString().toLowerCase(),
                        t.getImage(getClass().getResource("/resources/" + i + "_" + type.toString().toLowerCase() + ".png"))
                );
            }
        }
    }

    public Image getImage(String name) {
        return imageMap.get(name);
    }

    public static String getCardKey(Card card) {
        if(card == null) return "back";
        int value = card.getValue();
        return value + "_" + card.getCardType().toString().toLowerCase();
    }
}
