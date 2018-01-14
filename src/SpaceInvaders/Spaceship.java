package SpaceInvaders;

import java.awt.*;
import java.util.ArrayList;

public class Spaceship {

    private Block[] blocks = new Block[11];
    private int life = 3;
    private ArrayList<Block> bullets = new ArrayList<>();
    private Color color;

    /**
     * Constructor
     */
    public Spaceship(Color color) {
        this.color = color;
        for (int i = 0; i < 5; i++) {
            blocks[i] = new Block(10, 13 + i, 58, color);
            blocks[i + 5] = new Block(10, 13 + i, 57, color);
        }
        blocks[10] = new Block(10, 15, 56, color);
    }

    /**
     * Draws the spaceship
     */
    public void draw(Graphics graphics) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(graphics);
        }
    }

    /**
     * Moves the ship on the x axis
     */
    public void move(int x) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move(blocks[i].getX() + x, blocks[i].getY());
        }
    }

    /**
     * Moves the ship on the x axis
     */
    public void moveY(int y) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move(blocks[i].getX(), blocks[i].getY() + y);
        }
    }

    /**
     * Adds a blocks to the bullets ArrayList and positions it accordingly
     */
    public void shoot() {
        bullets.add(new Block(10, blocks[blocks.length -1].getX(), blocks[blocks.length -1].getY() - 1, color));
    }

    /**
     * Draws the spaceships bullets
     */
    public void drawBullets(Graphics graphics) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(graphics);
        }
    }

    /**
     * Returns the total numbers of blocks that are used to construct the spaceship
     */
    public int getNumberOfBlocks() { return blocks.length; }

    /**
     * Returns an individual block that constructs the spaceship using an index position
     */
    public Block getBlock(int index) { return blocks[index]; }

    /**
     * Returns the total numbers of bullets the spaceship currently has
     */
    public int getBulletsSize() { return bullets.size(); }

    /**
     * Gets in individual bullet from the bullets ArrayList
     */
    public Block getBullet(int index) { return bullets.get(index); }

    /**
     * Removes a bullet from the bullets ArrayList using an index position
     */
    public void removeBullet(int index) { bullets.remove(index); }

    /**
     * Deducts one life from the spaceship
     */
    public void deductLife() { life--; }

    /**
     * Returns the spaceships life
     */
    public int getLife() { return life; }

}
