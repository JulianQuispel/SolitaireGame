package nl.qwapp.solitaire;

import nl.qwapp.solitaire.controllers.Game;
import nl.qwapp.solitaire.ui.Board;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.start();

        Board b = new Board(game);

        b.draw();
    }
}
