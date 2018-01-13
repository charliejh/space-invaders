package SpaceInvaders;

import java.awt.*;
import java.util.ArrayList;

public class Spaceship {

    private Block[] blocks = new Block[11];
    private int life = 5;
    private ArrayList<Block> bullets = new ArrayList<>();
    private Color color;

    /**
     *
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
     *
     * @param x
     */
    public void move(int x) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move(blocks[i].getX() + x, blocks[i].getY());
        }
    }

    /**
     *
     */
    public void shoot() {
        bullets.add(new Block(10, blocks[blocks.length -1].getX(), blocks[blocks.length -1].getY() - 1, color));
    }

    /**
     *
     */
    public void draw(Graphics graphics) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(graphics);
        }
    }

    /**
     *
     */
    public void drawBullets(Graphics graphics) {
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).draw(graphics);
        }
    }

    /**
     *
     */
    public Block getBlock(int index) { return blocks[index]; }

    /**
     *
     */
    public int getBulletsSize() { return bullets.size(); }

    /**
     *
     */
    public Block getBullet(int index) { return bullets.get(index); }

    /**
     *
     */
    public void removeBullet(int index) { bullets.remove(index); }

    /**
     *
     */
    public int getNumberOfBlocks() { return blocks.length; }

    /**
     *
     */
    public void deductLife() { life--; }

    /**
     *
     */
    public int getLife() { return life; }

}
