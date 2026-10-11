package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.controllers.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyCanvasTest {

    @Test
    void testCanvasCreation() {
        // Test that we can create a MyCanvas instance without errors
        Game game = new Game();
        game.start();
        MyCanvas myCanvas = new MyCanvas(game);
        
        assertNotNull(myCanvas);
        assertNotNull(game);
    }

    @Test
    void testDragAndDropSetup() {
        // Test that the canvas properly implements required interfaces
        Game game = new Game();
        game.start();
        MyCanvas myCanvas = new MyCanvas(game);
        
        assertTrue(myCanvas instanceof java.awt.event.MouseListener);
        assertTrue(myCanvas instanceof java.awt.event.MouseMotionListener);
    }
    
    @Test
    void testDragStateTracking() {
        // Test basic drag state tracking functionality
        Game game = new Game();
        game.start();
        MyCanvas myCanvas = new MyCanvas(game);
        
        assertFalse(myCanvas.isDragging());
    }
}