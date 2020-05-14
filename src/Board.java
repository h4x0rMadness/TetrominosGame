import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

class Board extends JComponent {

    private static final int SCALE = 20; // number of pixels per square

    private int cols;
    private int rows;
    private List<Piece> pieces;
    private Piece activePiece;
    public Board(int cols, int rows) {
        super();

        setPreferredSize(new Dimension(cols * SCALE, rows * SCALE));
        this.pieces = new ArrayList<>();
    }

    public void paintComponent(Graphics g) {
        // clear the screen with black
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());


    }

    // Move the active piece down one step. Check for collisions.
    //  Check for complete rows that can be destroyed.
    public void nextTurn() {
        createNextPiece();
        for(int i = 0; i < 4; i++) {
            this.activePiece.locations[i].y -= 1;
        }



    }
    public void createNextPiece(Graphics g) {
        Random rand = new Random();
        this.activePiece = new Piece(rand.nextInt(7),g);

    }

    public void slide(int dx) {
        // TO DO: move the active piece one square in the direction dx
        // (which has a value -1 or 1):

        // request that paintComponent be called again:
        repaint();

    }

    public void rotateRight() {
        // TO DO: rotate the active piece to the right:
        System.out.println("rotateRight is called...");

        repaint();
    }

    public void rotateLeft() {
        // TO DO: rotate the active piece to the left:
        System.out.println("rotateLeft is called...");
        repaint();
    }

}
