package SpaceInvaders;

import java.awt.*;

public class Invader {

    private Block[] blocks = new Block[8];
    private int life = 2;
    private String direction = "LEFT";
    private String previousDirection = "LEFT";

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
     * Draws the snake by calling the each blocks draw() method
     */
    public void draw(Graphics graphics) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(graphics);
        }
    }

    /**
     * Moves the invader on the x and y axis
     */
    public void move(int x, int y) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move(blocks[i].getX() + x, blocks[i].getY() + y);
        }
    }

    /**
     * Returns the current direction of the invader
     */
    public String getDirection() { return direction; }

    /**
     * Sets the current direction of the invader
     */
    public void setDirection(String direction) { this.direction = direction; }

    /**
     * Returns the previous direction of the invader
     */
    public String getPreviousDirection() { return previousDirection; }

    /**
     * Sets the previous direction of the invader
     */
    public void setPreviousDirection(String direction) { this.previousDirection = direction; }

    /**
     * Returns the total numbers of blocks that are used to construct the invader
     */
    public int getBlockSize() { return blocks.length; }

    /**
     * Returns an individual block that constructs the invader using an index position
     */
    public Block getBlock(int index) { return blocks[index]; }

    /**
     * Deducts one life from the invader
     */
    public void deductLife() { life--; }

    /**
     * Returns the invaders life
     */
    public int getLife() { return life; }

}
