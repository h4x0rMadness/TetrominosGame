import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.*;
import java.util.List;
import java.util.Timer;

import javax.swing.*;

import static java.lang.Thread.sleep;

class Board extends JComponent {

    private static final int SCALE = 20; // number of pixels per square

    private int cols;
    private int rows;
    private Piece activePiece;
    private boolean hasActive;
    private List<Piece> pieces ;
    private boolean[][] isOccupied;
    private int SCORE;
    private final int BONUS = 100;
    private final static Color[] colorTable = {
            Color.red,
            Color.blue,
            Color.magenta,
            Color.orange,
            Color.green,
            Color.cyan,
            Color.yellow,
    };
    public Board(int cols, int rows) {
        super();
        setPreferredSize(new Dimension(cols * SCALE, rows * SCALE));
        pieces = new ArrayList<>();
        hasActive = false;
        isOccupied = new boolean[cols][rows];
        SCORE = 0;
    }


    public void paintComponent(Graphics g) {

        // check is row is full (to sink) or col is full (game over)
        checkRow(g);
        //checkCol(g);

        // clear the screen with black
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        // display score and play time
        showGameInfo(g);
        // test my kick ass score adding function


        // draw all inactive pieces
        for(Piece p : pieces) p.drawPiece(g);
        // if active exists, draw it with current location
        // if not, create new one
        if(activePiece == null) createNextPiece(g);
        activePiece.drawPiece(g);
    }

    // Move the active piece down one step. Check for collisions.
    //  Check for complete rows that can be destroyed.
    public void nextTurn() {
        if(activePiece != null) {
            movePiece(activePiece, 0, SCALE);
        }
        repaint();
        // if exists active, update location
        // if no exist, call paint/paintComponents to create one randomly

    }
    public void endGame(Graphics g) {
        System.out.println("Game Over!!!!!!!!!!!!!!!!!!!!!");
    }
    public void checkCol(Graphics g) {
        // check if any col is full
        // if so, show game over and final score
        boolean isFull = true;
        for(boolean[] bool_arr : isOccupied) {
            for(boolean bool : bool_arr) {
                isFull &= bool;
            }
            if(isFull) {
                endGame(g);
                return;
            }
            else isFull = true;
        }
    }
    public void checkRow(Graphics g) {
        // check the bottom if row is full
        boolean isFull = true;
        for(int i = 0; i < isOccupied.length; i++) {
            isFull &= isOccupied[i][isOccupied[0].length - 1];
        }
        // if full, sink every inactive piece
        // modify isOccupied
        // and add score
        if(isFull) {
            sink(g);
            scoreUpdate(g, 1);
        }
    }
    public void sink(Graphics g) {
        for(int i = isOccupied[0].length - 1; i >= 0; i--) {
            if(i == 0) {
                for(boolean b : isOccupied[i]) b = false;
            }
            else {
                for(int j = 0; j < isOccupied.length; j++) {
                    isOccupied[j][i] = isOccupied[j][i - 1];
                }
            }
        }
        for(Piece p : pieces) {
            for(int i = 0; i < 4; i++) {
                p.locations[i].y += SCALE;
                p.blocks[i].draw(g, SCALE, p.locations[i].x, p.locations[i].y);
            }
        }
        repaint();


    }
    public void scoreAddingEffects(Graphics g) {
        System.out.println("score adding is called...");
        g.setFont(new Font("Arial Black", Font.ITALIC, 32));
        Random rand = new Random();

        g.setColor(colorTable[rand.nextInt(7)]);
        String text = "SCORE +100 !";
        g.drawString(text, 80, 400);

    }
    public void scoreUpdate(Graphics g, int extra) {
        SCORE += BONUS * extra;
        // show some kick-ass cool animation (or not) for gamer
        scoreAddingEffects(g);

    }

    public void slide(int dx) {
        // TO DO: move the active piece one square in the direction dx
        // (which has a value -1 or 1):
        // request that paintComponent be called again:
        if(activePiece != null) {
            movePiece(activePiece, dx * SCALE, 0);
        }

        repaint();

    }
    public void speedUp() {
        if(activePiece != null) {
            movePiece(activePiece, 0, SCALE);
        }
        repaint();
    }
    public void rotate() {
        // TO DO: rotate the active piece to the right:
        //System.out.println("rotateRight is called...");

        repaint();
    }

    public void createNextPiece(Graphics g) {
        Random rand = new Random();
        activePiece = new Piece(rand.nextInt(7));

    }
    public void movePiece(Piece piece, int dx, int dy) {

        if(dy < 0) return; // Piece can't go upward
        // Piece will be inactive when collide with other Pieces in any direction
        // Piece can't go outside left or right border
        if(piece.getRight() + dx > 400 || piece.getLeft() + dx < 0 ||
                isOccupied[(piece.locations[0].x + dx) / SCALE][piece.locations[0].y / SCALE] ||
                isOccupied[(piece.locations[1].x + dx) / SCALE][piece.locations[1].y / SCALE] ||
                isOccupied[(piece.locations[2].x + dx) / SCALE][piece.locations[2].y / SCALE] ||
                isOccupied[(piece.locations[3].x + dx) / SCALE][piece.locations[3].y / SCALE]) return;
        else {
            for(Location l : piece.locations) l.x += dx;
        }

        // Piece will be inactive when collide with the bottom border
        if(piece.getBottom() + dy > 800 ||
                isOccupied[piece.locations[0].x / SCALE][(piece.locations[0].y + dy) / SCALE] ||
                isOccupied[piece.locations[1].x / SCALE][(piece.locations[1].y + dy) / SCALE] ||
                isOccupied[piece.locations[2].x / SCALE][(piece.locations[2].y + dy) / SCALE] ||
                isOccupied[piece.locations[3].x / SCALE][(piece.locations[3].y + dy) / SCALE]) {
            pieces.add(piece);
            // meanwhile occupy the space in isOccupied
            for(Location l : piece.locations) {
                isOccupied[l.x / SCALE][l.y / SCALE] = true;
            }
            activePiece = null;
            return;
        }
        // otherwise just move the piece with dx and dy

        else {
            for(Location l : piece.locations) l.y += SCALE;
        }


    }
    public void showGameInfo(Graphics g) {
        // display score and / or play time
        g.setFont(new Font("TimesRoman", Font.PLAIN, 16));
        g.setColor(Color.white);
        String text = "Game Score: " + SCORE;
        g.drawString(text, 0, 20);
    }

}
