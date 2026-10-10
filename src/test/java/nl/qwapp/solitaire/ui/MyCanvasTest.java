package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.controllers.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyCanvasTest {

    private Game game;
    private MyCanvas myCanvas;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.start();
        myCanvas = new MyCanvas(game);
    }

    @Test
    void testCanvasCreation() {
        assertNotNull(myCanvas);
        assertNotNull(game);
    }

    @Test
    void testDragAndDropSetup() {
        // Test that the canvas properly implements required interfaces
        assertTrue(myCanvas instanceof java.awt.event.MouseListener);
        assertTrue(myCanvas instanceof java.awt.event.MouseMotionListener);
    }
    
    @Test
    void testDragStateTracking() {
        // Test basic drag state tracking functionality
        assertFalse(myCanvas.isDragging);
    }
}