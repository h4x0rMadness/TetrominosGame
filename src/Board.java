import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.*;
import java.util.List;


import javax.swing.*;



class Board extends JComponent {

    private static final int SCALE = 20; // number of pixels per square

    
    private Piece activePiece;
    private List<Piece> pieces ;
    private boolean[][] isOccupied;
    private int SCORE;
    private final int BONUS = 100;
    private int hasScoreAdding = 0;
    private long startTime;
    private int extra;
    private int times;
    private boolean endGame = false;
    private final int cols;
    private final int rows;
    private final static Color[] colorTable = {
            Color.red,
            Color.blue,
            Color.magenta,
            Color.orange,
            Color.green,
            Color.cyan,
            Color.yellow,
    };
    public Board(int c, int r) {
        super();
        cols = c;
        rows = r;
        setPreferredSize(new Dimension(cols * SCALE, rows * SCALE));
        pieces = new ArrayList<>();
        isOccupied = new boolean[cols][rows];
        SCORE = 0;
        startTime = System.currentTimeMillis();
        extra = 1;
        times = 0;

    }


    public void paintComponent(Graphics g) {
        if(endGame) {
            endGame(g);
            return;
        }
        // check is row is full (to sink) or col is full (game over)
        checkRow(g);
        checkCol(g);
        updateTimeBonus();
        // clear the screen with black
        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());
        // display score and play time
        showGameInfo(g);
        // test my kick ass score adding function
        if(hasScoreAdding > 0) {
            scoreAddingEffects(g);
            hasScoreAdding -= 1;
        }

        // draw all inactive pieces
        for(Piece p : pieces) p.drawPiece(g);
        // if active exists, draw it with current location
        // if not, create new one
        if(activePiece == null) createNextPiece(g);
        activePiece.drawPiece(g);
    }

    public void nextTurn() {
        if(activePiece != null) {
            movePiece(activePiece, 0, SCALE);
        }
        repaint();
        // if exists active, update location
        // if no exist, call paint/paintComponents to create one randomly

    }

    public void endGame(Graphics g) {
        g.setColor(Color.orange);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setFont(new Font("Arial Black", Font.PLAIN, 36));
        g.setColor(Color.red);
        String text = "GAME OVER";
        g.drawString(text, 60, 360);

        g.setColor(Color.black);
        g.setFont(new Font("Arial Black", Font.ITALIC, 30));
        String scoreMsg = "SCORE: " + SCORE;
        g.drawString(scoreMsg,100,420);

        g.setFont(new Font("Arial Black", Font.PLAIN, 16));
        g.setColor(Color.black);
        String authorText = "@author: kefan zhang";
        g.drawString(authorText, 100, 700);

    }
    public void checkCol(Graphics g) {
        // check if any col is full
        // if so, show game over and final score
        boolean isFull = false;
        for(int i = 0; i < isOccupied.length; i++) isFull |= isOccupied[i][0];
        //System.out.println();
        //System.out.println("after or operation, isFull is: " + isFull);
        if(isFull) {
            endGame = true;
            repaint();
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
            scoreUpdate(g);
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
        //System.out.println("score adding is called...");
        g.setFont(new Font("Arial Black", Font.ITALIC, 32));
        Random rand = new Random();

        g.setColor(colorTable[rand.nextInt(7)]);

        String text = "SCORE +" + extra * BONUS;
        g.drawString(text, 80, 400);


    }
    public void scoreUpdate(Graphics g) {
        SCORE += BONUS * extra;
        // show some kick-ass cool animation (or not) for gamer
        hasScoreAdding = 2;
        repaint();

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
        if(activePiece != null)
            activePiece.rotate();
        repaint();
    }

    public void createNextPiece(Graphics g) {
        Random rand = new Random();
        activePiece = new Piece(rand.nextInt(7), cols, rows);

    }
    public void movePiece(Piece piece, int dx, int dy) {

        if(dy < 0) return; // Piece can't go upward
        // Piece will be inactive when collide with other Pieces in any direction
        // Piece can't go outside left or right border
        if(piece.getRight() + dx > SCALE * cols || piece.getLeft() + dx < 0 ||
                isOccupied[(piece.locations[0].x + dx) / SCALE][piece.locations[0].y / SCALE] ||
                isOccupied[(piece.locations[1].x + dx) / SCALE][piece.locations[1].y / SCALE] ||
                isOccupied[(piece.locations[2].x + dx) / SCALE][piece.locations[2].y / SCALE] ||
                isOccupied[(piece.locations[3].x + dx) / SCALE][piece.locations[3].y / SCALE]) return;
        else {
            for(Location l : piece.locations) l.x += dx;
        }

        // Piece will be inactive when collide with the bottom border
        if(piece.getBottom() + dy > SCALE * rows ||
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
    public void updateTimeBonus() {
        long elapseTime = System.currentTimeMillis() - startTime;

        if((int)elapseTime / 60000 > times) {
            times = (int) elapseTime / 60000;
            extra *= times;
        }
    }
}
