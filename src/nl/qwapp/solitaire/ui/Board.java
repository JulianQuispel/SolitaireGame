package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.Card;
import nl.qwapp.solitaire.controllers.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Board extends JFrame {
    Game game;
    URL cardBack;
    MyCanvas canvas;

    public Board(Game game) throws HeadlessException {
        this.game = game;

        setTitle("Solitaire");
        setSize(1000, 800);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenu menu = new JMenu("Game");

        JMenuItem menuItem = new JMenuItem("New game");
        menuItem.addActionListener(e -> {
            game.start();
            draw();
        });
        menu.add(menuItem);


        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);

        setJMenuBar(menuBar);

        cardBack = getClass().getResource("/resources/card_back.png");

        canvas = new MyCanvas(game);
        add(canvas);
    }

    class DrawButton extends JButton {
        DrawButton() {
            super("Draw");

            addActionListener(e -> {
                System.out.println("Button pressed");
                game.getDrawStack().nextCard();
                draw();
            });
        }
    }

    public void draw() {
        setVisible(true);
        canvas.repaint();
    }
}
