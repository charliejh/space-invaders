package SpaceInvaders;

import java.awt.*;

public class SpaceshipInvader {

    private Block[] blocks = new Block[11];
    private Color color;

    /**
     *
     */
    public SpaceshipInvader(Color color) {
        this.color = color;
        for (int i = 0; i < 5; i++) {
            blocks[i] = new Block(10, 85 + i, 6, color);
            blocks[i + 5] = new Block(10, 85 + i, 5, color);
        }
        blocks[10] = new Block(10, 87, 7, color);
    }

    /**
     * Draws the spaceship invader
     */
    public void draw(Graphics graphics) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].draw(graphics);
        }
    }

    /**
     * Moves the spaceship invader on the x axis
     */
    public void move(int x) {
        for (int i = 0; i < blocks.length; i++) {
            blocks[i].move(blocks[i].getX() + x, blocks[i].getY());
        }
    }

    /**
     * Moves ths spaceship invader back off the screen to the right
     */
    public void moveOffScreen() {
        for (int i = 0; i < 5; i++) {
            blocks[i] = new Block(10, 85 + i, 6, color);
            blocks[i + 5] = new Block(10, 85 + i, 5, color);
        }
        blocks[10] = new Block(10, 87, 7, color);
    }

    /**
     * Returns an individual block that constructs the spaceship invader using an index position
     */
    public Block getBlock(int index) { return blocks[index]; }
}
