package SpaceInvaders;

import java.awt.*;

public class Block {

    private int blockSize, x, y;
    private Color blockColor;

    /**
     * Constructor
     */
    public Block(int blockSize, int x, int y, Color blockColor) {
        this.blockSize = blockSize;
        this.x = x;
        this.y = y;
        this.blockColor = blockColor;
    }

    /**
     * Getters
     */
    public int getX() { return x; }
    public int getY() { return y; }

    /**
     * Moves the block
     * @param x - x position of the block
     * @param y - y position of the block
     */
    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws a block
     */
    public void draw(Graphics graphics) {
        graphics.setColor(blockColor);
        graphics.fillRect(x * blockSize, y * blockSize, blockSize, blockSize);
        graphics.setColor(new Color(0,0,0));
        graphics.drawRect(x * blockSize, y * blockSize, blockSize, blockSize);
    }

}
