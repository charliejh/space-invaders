package SpaceInvaders;

import java.awt.*;

public class Invader {

    private Block[] blocks = new Block[8];
    private int life = 2;

    /**
     *
     */
    public Invader(Color color) {
        for (int i = 0; i < 2; i++) {
            blocks[i] = new Block(10, 10 + i, 10, color);
            blocks[i + 2] = new Block(10, 10 + i, 11, color);
        }
        blocks[4] = new Block(10, 9, 9, color);
        blocks[5] = new Block(10, 12, 9, color);
        blocks[6] = new Block(10, 9, 12, color);
        blocks[7] = new Block(10, 12, 12, color);
    }

    /**
     *
     * @param x
     * @param y
     */
    public void move(int x, int y) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move(blocks[i].getX() + x, blocks[i].getY() + y);
        }
    }

    /**
     * Draws the snake by calling the each blocks draw() method
     */
    public void draw(Graphics graphics) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(graphics);
        }
    }

    /**
     *
     */
    public int getBlockSize() { return blocks.length; }

    /**
     *
     */
    public Block getBlock(int index) { return blocks[index]; }

    /**
     *
     */
    public void deductLife() { life--; }

    /**
     *
     */
    public int getLife() { return life; }

}
