package nl.qwapp.solitaire;

import nl.qwapp.solitaire.controllers.Game;
import nl.qwapp.solitaire.ui.Board;

public class Main {
    public static void main(String[] args) {
        // Create and start the game
        Game game = new Game();
        game.start();
        
        // Create the UI
        Board board = new Board(game);
        board.setVisible(true);
    }
}