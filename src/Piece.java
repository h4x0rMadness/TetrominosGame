import java.awt.*;
import java.util.HashMap;

public class Piece {
    int scale;
    int initialX;
    int initialY;
    char type;
    int colorIndex;
    Block[] blocks;
    Location[] locations;
    boolean isActive;
    private static final HashMap<Integer,Character> color2type = new HashMap<>() {{
        put(0, 'I');
        put(1, 'J');
        put(2, 'L');
        put(3, 'O');
        put(4, 'S');
        put(5, 'T');
        put(6, 'Z');
    }};
    public Piece(int colorIndex) {
        this.isActive = true;
        this.scale = 20;
        this.initialX = 160;
        this.initialY = 0;
        this.locations = new Location[4];
        this.colorIndex = colorIndex;
        this.type = color2type.get(this.colorIndex);
        this.blocks = new Block[4];
        this.initialLocationWithShape();

    }
    public int getLeft() {
        // get the most left side block location
        int mostLeft = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++) {
            mostLeft = Math.min(mostLeft, this.locations[i].x);
        }
        return mostLeft;
    }
    public int getRight() {
        // get the most right side block location
        int mostRight = 0;
        for(int i = 0; i < 4; i++) {
            mostRight = Math.max(mostRight, this.locations[i].x + scale);
        }
        return mostRight;
    }
    public int getBottom() {
        // get the bottom of the blocks
        int mostBottom = 0;
        for(int i = 0; i < 4; i++) {
            mostBottom = Math.max(mostBottom, this.locations[i].y + scale);
        }
        return mostBottom;
    }
    public void drawPiece(Graphics g) {
        //create four blocks


        for(int i = 0; i < this.blocks.length; i++) {
            this.blocks[i] = new Block(this.colorIndex);
            this.blocks[i].draw(g, this.scale, this.locations[i].x, this.locations[i].y);
        }



    }
    public void initialLocationWithShape() {
        if(this.type == 'I') {
            // - - - -

            this.locations[0] = new Location(this.initialX, this.initialY);
            this.locations[1]  = new Location(this.initialX + this.scale, this.initialY);
            this.locations[2]  = new Location(this.initialX + this.scale * 2, this.initialY);
            this.locations[3]  = new Location(this.initialX + this.scale * 3, this.initialY);
        }
        else if(this.type == 'J') {
            // -
            // - - -
            this.locations[0]  = new Location(this.initialX, this.initialY);
            this.locations[1]  = new Location(this.initialX, this.initialY + this.scale);
            this.locations[2]  = new Location(this.initialX + this.scale, this.initialY + this.scale);
            this.locations[3]  = new Location(this.initialX + this.scale * 2, this.initialY + this.scale);
        }
        else if(this.type == 'L') {
            //     -
            // - - -
            this.locations[0]  = new Location(this.initialX + this.scale * 2, this.initialY);
            this.locations[1]  = new Location(this.initialX, this.initialY + this.scale);
            this.locations[2]  = new Location(this.initialX + this.scale, this.initialY + this.scale);
            this.locations[3]  = new Location(this.initialX + this.scale * 2, this.initialY + this.scale);
        }
        else if(this.type == 'O') {
            //  - -
            //  - -
            this.locations[0]  = new Location(this.initialX , this.initialY);
            this.locations[1]  = new Location(this.initialX, this.initialY + this.scale);
            this.locations[2]  = new Location(this.initialX + this.scale, this.initialY);
            this.locations[3]  = new Location(this.initialX + this.scale, this.initialY + this.scale);
        }
        else if(this.type == 'S') {
            //   - -
            // - -
            this.locations[0]  = new Location(this.initialX + this.scale, this.initialY);
            this.locations[1]  = new Location(this.initialX + this.scale * 2, this.initialY );
            this.locations[2]  = new Location(this.initialX, this.initialY + this.scale);
            this.locations[3]  = new Location(this.initialX + this.scale, this.initialY + this.scale);
        }
        else if(this.type == 'T') {
            //   -
            // - - -
            this.locations[0]  = new Location(this.initialX + this.scale, this.initialY);
            this.locations[1]  = new Location(this.initialX, this.initialY  + this.scale);
            this.locations[2]  = new Location(this.initialX  + this.scale, this.initialY + this.scale);
            this.locations[3]  = new Location(this.initialX + this.scale * 2, this.initialY + this.scale);
        }
        else if(this.type == 'Z') {
            // - -
            //   - -
            this.locations[0]  = new Location(this.initialX, this.initialY);
            this.locations[1]  = new Location(this.initialX + this.scale, this.initialY);
            this.locations[2]  = new Location(this.initialX  + this.scale, this.initialY + this.scale);
            this.locations[3]  = new Location(this.initialX + this.scale * 2, this.initialY + this.scale);
        }
    }



}
