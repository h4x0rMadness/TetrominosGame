import java.awt.*;
import java.util.HashMap;

public class Piece {
    int SCALE;
    int gameInitialX;
    int gameInitialY;
    char type;
    int colorIndex;
    Block[] blocks;
    Location[] locations;
    int stateCode;
    int rows;
    int cols;
    private static final int[] maxShapeCode = {2, 4, 4, 1, 2, 4, 2};
    private static final HashMap<Integer,Character> color2type = new HashMap<>() {{
        put(0, 'I');
        put(1, 'J');
        put(2, 'L');
        put(3, 'O');
        put(4, 'S');
        put(5, 'T');
        put(6, 'Z');
    }};

    public Piece(int color, int c, int r) {

        SCALE = 20;
        cols = c * SCALE;
        rows = r * SCALE;
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
    public boolean isInsideBorder(int x, int y) {
        return (x >= 0 && x < cols && y >= 0 && y < rows);
    }
    public boolean rotateBorderCheck(Location newInitial) {
        if(newInitial.x < 0 || newInitial.x >= cols || newInitial.y < 0 || newInitial.y >= rows) return false;
        int x = newInitial.x, y = newInitial.y;
        if(colorIndex == 0) {
            return (isInsideBorder(x,y) &&
                    isInsideBorder(x + SCALE * 3, y) &&
                    isInsideBorder(x + SCALE * 3, y + SCALE * 3) &&
                    isInsideBorder(x, y + SCALE * 3)
            );
        }
        else if (colorIndex == 1 || colorIndex == 3 || colorIndex == 5 || colorIndex == 6) {
            return (isInsideBorder(x,y) &&
                    isInsideBorder(x + SCALE * 2, y) &&
                    isInsideBorder(x + SCALE * 2, y + SCALE * 2) &&
                    isInsideBorder(x, y + SCALE * 2)
            );
        }
        else if (colorIndex == 2) {
            return (isInsideBorder(x,y) &&
                    isInsideBorder(x + SCALE * 2, y) &&
                    isInsideBorder(x - SCALE * 2, y + SCALE) &&
                    isInsideBorder(x + SCALE, y + SCALE * 2)
            );
        }

        return false;
    }
    public void relocateWithCode() {
        if(colorIndex == 0) {
            // for 'I' shape, there are only two states
            Location newInitial = getLeftMostLocation();
            if(!rotateBorderCheck(newInitial)) return;
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[2] = new Location(newInitial.x, newInitial.y + SCALE * 2);
                locations[3] = new Location(newInitial.x, newInitial.y + SCALE * 3);
            }
        }
        else if(colorIndex == 1) {
            Location newInitial = getUpperMostLocation();
            if(!rotateBorderCheck(newInitial)) return;
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else if(stateCode == 1) {
                locations[0] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y + SCALE);
                locations[2] = new Location(newInitial.x + SCALE, newInitial.y + SCALE * 2);
                locations[3] = new Location(newInitial.x, newInitial.y + SCALE * 2);
            }
            else if(stateCode == 2) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[2] = new Location(newInitial.x + SCALE * 2, newInitial.y);
                locations[3] = new Location(newInitial.x + SCALE * 2, newInitial.y + SCALE);
            }
            else if(stateCode == 3) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[2] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[3] = new Location(newInitial.x, newInitial.y + SCALE * 2);
            }
        }
        else if(colorIndex == 2) {
            Location newInitial = getUpperMostLocation();
            if(!rotateBorderCheck(newInitial)) return;
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else if(stateCode == 1) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[2] = new Location(newInitial.x, newInitial.y + SCALE * 2);
                locations[3] = new Location(newInitial.x + SCALE, newInitial.y + SCALE * 2);
            }
            else if(stateCode == 2) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[2] = new Location(newInitial.x + SCALE * 2, newInitial.y);
                locations[3] = new Location(newInitial.x, newInitial.y + SCALE);
            }
            else if(stateCode == 3) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[2] = new Location(newInitial.x + SCALE, newInitial.y + SCALE);
                locations[3] = new Location(newInitial.x + SCALE, newInitial.y + SCALE * 2);
            }

        }
        // colorIndex == 3 is the block, whoa! nothing needed!
        else if(colorIndex == 4) {
            // the new initial should be one SCALE up than the leftmost block
            Location newInitial = new Location(getLeftMostLocation().x, getLeftMostLocation().y - SCALE);
            if(!rotateBorderCheck(newInitial)) return;
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else {
                locations[0] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[1] = new Location(newInitial.x, newInitial.y + SCALE * 2);
                locations[2] = new Location(newInitial.x + SCALE, newInitial.y + SCALE * 2);
                locations[3] = new Location(newInitial.x + SCALE, newInitial.y + SCALE * 3);
            }
        }
        else if(colorIndex == 5) {
            // the new initial should be one SCALE left than the uppermost block
            Location newInitial = new Location(getUpperMostLocation().x - SCALE, getUpperMostLocation().y);
            if(!rotateBorderCheck(newInitial)) return;
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else if(stateCode == 1) {
                locations[0] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y + SCALE);
                locations[2] = new Location(newInitial.x + SCALE, newInitial.y + SCALE * 2);
                locations[3] = new Location(newInitial.x + SCALE, newInitial.y);
            }
            else if(stateCode == 2) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[2] = new Location(newInitial.x + SCALE * 2, newInitial.y);
                locations[3] = new Location(newInitial.x + SCALE, newInitial.y + SCALE);
            }
            else if(stateCode == 3) {
                locations[0] = new Location(newInitial.x, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[2] = new Location(newInitial.x, newInitial.y + SCALE * 2);
                locations[3] = new Location(newInitial.x + SCALE, newInitial.y + SCALE);
            }
        }
        else if(colorIndex == 6) {
            Location newInitial = getLeftMostLocation();
            if(!rotateBorderCheck(newInitial)) return;
            if(stateCode == 0) initialLocationWithShape(newInitial.x, newInitial.y);
            else {
                locations[0] = new Location(newInitial.x + SCALE, newInitial.y);
                locations[1] = new Location(newInitial.x, newInitial.y + SCALE);
                locations[2] = new Location(newInitial.x + SCALE, newInitial.y + SCALE);
                locations[3] = new Location(newInitial.x, newInitial.y + SCALE * 2);
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
            mostRight = Math.max(mostRight, locations[i].x + SCALE);
        }
        return mostRight;
    }
    public int getBottom() {
        // get the bottom of the blocks
        int mostBottom = 0;
        for(int i = 0; i < 4; i++) {
            mostBottom = Math.max(mostBottom, locations[i].y + SCALE);
        }
        return mostBottom;
    }
    public void drawPiece(Graphics g) {
        //create four blocks
        for(int i = 0; i < blocks.length; i++) {
            blocks[i] = new Block(colorIndex);
            blocks[i].draw(g, SCALE, locations[i].x, locations[i].y);
        }



    }
    public void initialLocationWithShape(int initialX, int initialY) {
        if(initialX >= cols || initialX < 0 || initialY <0 || initialY >= rows) return;
        if(type == 'I') {
            // - - - -
            locations[0] = new Location(initialX, initialY);
            locations[1] = new Location(initialX + SCALE, initialY);
            locations[2] = new Location(initialX + SCALE * 2, initialY);
            locations[3] = new Location(initialX + SCALE * 3, initialY);
        }
        else if(type == 'J') {
            // -
            // - - -
            locations[0] = new Location(initialX, initialY);
            locations[1] = new Location(initialX, initialY + SCALE);
            locations[2] = new Location(initialX + SCALE, initialY + SCALE);
            locations[3] = new Location(initialX + SCALE * 2, initialY + SCALE);
        }
        else if(type == 'L') {
            //     -
            // - - -
            locations[0] = new Location(initialX + SCALE * 2, initialY);
            locations[1] = new Location(initialX, initialY + SCALE);
            locations[2] = new Location(initialX + SCALE, initialY + SCALE);
            locations[3] = new Location(initialX + SCALE * 2, initialY + SCALE);
        }
        else if(type == 'O') {
            //  - -
            //  - -
            locations[0] = new Location(initialX , initialY);
            locations[1] = new Location(initialX, initialY + SCALE);
            locations[2] = new Location(initialX + SCALE, initialY);
            locations[3] = new Location(initialX + SCALE, initialY + SCALE);
        }
        else if(type == 'S') {
            //   - -
            // - -
            locations[0] = new Location(initialX + SCALE, initialY);
            locations[1] = new Location(initialX + SCALE * 2, initialY );
            locations[2] = new Location(initialX, initialY + SCALE);
            locations[3] = new Location(initialX + SCALE, initialY + SCALE);
        }
        else if(type == 'T') {
            //   -
            // - - -
            locations[0] = new Location(initialX + SCALE, initialY);
            locations[1] = new Location(initialX, initialY  + SCALE);
            locations[2] = new Location(initialX  + SCALE, initialY + SCALE);
            locations[3] = new Location(initialX + SCALE * 2, initialY + SCALE);
        }
        else if(type == 'Z') {
            // - -
            //   - -
            locations[0] = new Location(initialX, initialY);
            locations[1] = new Location(initialX + SCALE, initialY);
            locations[2] = new Location(initialX  + SCALE, initialY + SCALE);
            locations[3] = new Location(initialX + SCALE * 2, initialY + SCALE);
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



}
