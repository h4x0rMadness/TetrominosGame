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
    Graphics g;
    boolean isActive;
    private static HashMap<Integer,Character> type2color = new HashMap<>() {{
        put(0, 'I');
        put(1, 'J');
        put(2, 'L');
        put(3, 'O');
        put(4, 'S');
        put(5, 'T');
        put(6, 'Z');
    }};
    public Piece(int colorIndex, Graphics g) {
        this.isActive = true;
        this.scale = 20;
        this.initialX = 160;
        this.initialY = 0;
        this.locations = new Location[4];
        this.colorIndex = colorIndex;
        this.g = g;
        this.type = type2color.get(this.colorIndex);
        this.blocks = new Block[4];
        this.createBlocks();

    }

    public void createBlocks() {
        //create four blocks
        this.initialLocationWithShape();

        for(int i = 0; i < this.blocks.length; i++) {
            this.blocks[i] = new Block(this.colorIndex);
            this.blocks[i].draw(this.g, this.scale, this.locations[i].x, this.locations[i].y);
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
