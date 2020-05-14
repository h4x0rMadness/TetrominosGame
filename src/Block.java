import java.awt.Color;
import java.awt.Graphics;

class Block {

    public int colorIndex;
    public Location location;
    public static Color[] colors = {Color.red,
                                    Color.blue,
                                    Color.magenta,
                                    Color.orange,
                                    Color.green,
                                    Color.cyan,
                                    Color.yellow};

    public Block(int colorIndex) {
        this.colorIndex = colorIndex;

    }

    public void draw(Graphics g, int scale, int x, int y) {
        g.setColor(colors[colorIndex]);
        g.fillRect(x,y,scale - 2,scale - 2);
    }

}

