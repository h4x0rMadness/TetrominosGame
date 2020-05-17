import java.awt.*;
import java.util.HashMap;

public class Piece {
    int scale;
    int gameInitialX;
    int gameInitialY;
    char type;
    int colorIndex;
    Block[] blocks;
    Location[] locations;
    int stateCode;
    private static final HashMap<Integer,Character> color2type = new HashMap<>() {{
        put(0, 'I');
        put(1, 'J');
        put(2, 'L');
        put(3, 'O');
        put(4, 'S');
        put(5, 'T');
        put(6, 'Z');
    }};
    private static final int[] maxShapeCode = {2, 4, 4, 1, 2, 4, 2};
    public Piece(int color) {
        scale = 20;
        gameInitialX = 160;
        gameInitialY = 0;
        locations = new Location[4];
        colorIndex = color;
        type = color2type.get(colorIndex);
        blocks = new Block[4];
        initialLocationWithShape(gameInitialX, gameInitialY);
        stateCode = 0;

    }
    public void rotate() {
        // for certain type of piece
        // while current state code is no bigger than maxShapeCode, increase
        // and according to it, adjust the shape
        if(stateCode < maxShapeCode[colorIndex] - 1) stateCode++;
        else stateCode = 0;
        relocateWithCode();
    }
    public void initialLocationWithShape(int initialX, int initialY) {
        if(type == 'I') {
            // - - - -
            locations[0] = new Location(initialX, initialY);
            locations[1] = new Location(initialX + scale, initialY);
            locations[2] = new Location(initialX + scale * 2, initialY);
            locations[3] = new Location(initialX + scale * 3, initialY);
        }
        else if(type == 'J') {
            // -
            // - - -
            locations[0] = new Location(initialX, initialY);
            locations[1] = new Location(initialX, initialY + scale);
            locations[2] = new Location(initialX + scale, initialY + scale);
            locations[3] = new Location(initialX + scale * 2, initialY + scale);
        }
        else if(type == 'L') {
            //     -
            // - - -
            locations[0] = new Location(initialX + scale * 2, initialY);
            locations[1] = new Location(initialX, initialY + scale);
            locations[2] = new Location(initialX + scale, initialY + scale);
            locations[3] = new Location(initialX + scale * 2, initialY + scale);
        }
        else if(type == 'O') {
            //  - -
            //  - -
            locations[0] = new Location(initialX , initialY);
            locations[1] = new Location(initialX, initialY + scale);
            locations[2] = new Location(initialX + scale, initialY);
            locations[3] = new Location(initialX + scale, initialY + scale);
        }
        else if(type == 'S') {
            //   - -
            // - -
            locations[0] = new Location(initialX + scale, initialY);
            locations[1] = new Location(initialX + scale * 2, initialY );
            locations[2] = new Location(initialX, initialY + scale);
            locations[3] = new Location(initialX + scale, initialY + scale);
        }
        else if(type == 'T') {
            //   -
            // - - -
            locations[0] = new Location(initialX + scale, initialY);
            locations[1] = new Location(initialX, initialY  + scale);
            locations[2] = new Location(initialX  + scale, initialY + scale);
            locations[3] = new Location(initialX + scale * 2, initialY + scale);
        }
        else if(type == 'Z') {
            // - -
            //   - -
            locations[0] = new Location(initialX, initialY);
            locations[1] = new Location(initialX + scale, initialY);
            locations[2] = new Location(initialX  + scale, initialY + scale);
            locations[3] = new Location(initialX + scale * 2, initialY + scale);
        }
    }
    public Location getLeftMostLocation() {
        Location leftMost = new Location(Integer.MAX_VALUE, 0);
        for(Location l : locations) {
            if(l.x < leftMost.x) leftMost = l;
        }
        return leftMost;
    }
    public Location getUpperMostLocation() {
        Location upperMost = new Location(0, Integer.MAX_VALUE);
        for(Location l : locations) {
            if(l.y < upperMost.y) upperMost = l;
        }
        return upperMost;
    }
    public void relocateWithCode() {
        if(colorIndex == 0) {
            // for 'I' shape, there are only two states
            Location newInitial = getLeftMostLocation();
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + scale);
                locations[2] = new Location(newInitial.x, newInitial.y + scale * 2);
                locations[3] = new Location(newInitial.x, newInitial.y + scale * 3);
            }
        }
        else if(colorIndex == 1) {
            Location newInitial = getUpperMostLocation();
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else if(stateCode == 1) {
                locations[0] = new Location(newInitial.x + scale, newInitial.y);
                locations[1] = new Location(newInitial.x + scale, newInitial.y + scale);
                locations[2] = new Location(newInitial.x + scale, newInitial.y + scale * 2);
                locations[3] = new Location(newInitial.x, newInitial.y + scale * 2);
            }
            else if(stateCode == 2) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + scale, newInitial.y);
                locations[2] = new Location(newInitial.x + scale * 2, newInitial.y);
                locations[3] = new Location(newInitial.x + scale * 2, newInitial.y + scale);
            }
            else if(stateCode == 3) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + scale, newInitial.y);
                locations[2] = new Location(newInitial.x, newInitial.y + scale);
                locations[3] = new Location(newInitial.x, newInitial.y + scale * 2);
            }
        }
        else if(colorIndex == 2) {
            Location newInitial = getUpperMostLocation();
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else if(stateCode == 1) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + scale);
                locations[2] = new Location(newInitial.x, newInitial.y + scale * 2);
                locations[3] = new Location(newInitial.x + scale, newInitial.y + scale * 2);
            }
            else if(stateCode == 2) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + scale, newInitial.y);
                locations[2] = new Location(newInitial.x + scale * 2, newInitial.y);
                locations[3] = new Location(newInitial.x, newInitial.y + scale);
            }
            else if(stateCode == 3) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + scale, newInitial.y);
                locations[2] = new Location(newInitial.x + scale, newInitial.y + scale);
                locations[3] = new Location(newInitial.x + scale, newInitial.y + scale * 2);
            }

        }
        // colorIndex == 3 is the block, whoo! nothing needed!
        else if(colorIndex == 4) {
            // the new initial should be one scale up than the leftmost block
            Location newInitial = new Location(getLeftMostLocation().x, getLeftMostLocation().y - scale);
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else {
                locations[0] = new Location(newInitial.x, newInitial.y + scale);
                locations[1] = new Location(newInitial.x, newInitial.y + scale * 2);
                locations[2] = new Location(newInitial.x + scale, newInitial.y + scale * 2);
                locations[3] = new Location(newInitial.x + scale, newInitial.y + scale * 3);
            }
        }
        else if(colorIndex == 5) {
            // the new initial should be one scale left than the uppermost block
            Location newInitial = new Location(getUpperMostLocation().x - scale, getUpperMostLocation().y);
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else if(stateCode == 1) {
                locations[0] = new Location(newInitial.x, newInitial.y + scale);
                locations[1] = new Location(newInitial.x + scale, newInitial.y + scale);
                locations[2] = new Location(newInitial.x + scale, newInitial.y + scale * 2);
                locations[3] = new Location(newInitial.x + scale, newInitial.y);
            }
            else if(stateCode == 2) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + scale, newInitial.y);
                locations[2] = new Location(newInitial.x + scale * 2, newInitial.y);
                locations[3] = new Location(newInitial.x + scale, newInitial.y + scale);
            }
            else if(stateCode == 3) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + scale);
                locations[2] = new Location(newInitial.x, newInitial.y + scale * 2);
                locations[3] = new Location(newInitial.x + scale, newInitial.y + scale);
            }
        }
        else if(colorIndex == 6) {
            Location newInitial = getLeftMostLocation();
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else {
                locations[0] = new Location(newInitial.x + scale, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + scale);
                locations[2] = new Location(newInitial.x + scale, newInitial.y + scale);
                locations[3] = new Location(newInitial.x, newInitial.y + scale * 2);
            }
        }
    }
    public int getLeft() {
        // get the most left side block location
        int mostLeft = Integer.MAX_VALUE;
        for(int i = 0; i < 4; i++) {
            mostLeft = Math.min(mostLeft, locations[i].x);
        }
        return mostLeft;
    }
    public int getRight() {
        // get the most right side block location
        int mostRight = 0;
        for(int i = 0; i < 4; i++) {
            mostRight = Math.max(mostRight, locations[i].x + scale);
        }
        return mostRight;
    }
    public int getBottom() {
        // get the bottom of the blocks
        int mostBottom = 0;
        for(int i = 0; i < 4; i++) {
            mostBottom = Math.max(mostBottom, locations[i].y + scale);
        }
        return mostBottom;
    }
    public void drawPiece(Graphics g) {
        //create four blocks
        for(int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(colorIndex);
            blocks[i].draw(g, scale, locations[i].x, locations[i].y);
        }



    }




}
