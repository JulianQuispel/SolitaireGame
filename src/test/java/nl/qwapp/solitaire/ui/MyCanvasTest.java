package nl.qwapp.solitaire.ui;

import nl.qwapp.solitaire.controllers.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyCanvasTest {

    @Test
    void testCanvasCreation() {
        // This test focuses on verifying the class structure without resource loading
        Game game = new Game();
        game.start();
        // Just verify the constructor can be called without throwing exceptions
        assertDoesNotThrow(() -> new MyCanvas(game));
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