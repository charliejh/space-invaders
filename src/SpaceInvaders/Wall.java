package SpaceInvaders;

import java.awt.*;
import java.util.ArrayList;

public class Wall {

    private ArrayList<Block> blocks = new ArrayList<>();

    public Wall(Color color) {
        for (int i = 0; i < 5; i++) {
            blocks.add(new Block(10, 7 + i, 50, color));
            blocks.add(new Block(10, 7 + i, 49, color));
            blocks.add(new Block(10, 7 + i, 48, color));
        }

        blocks.add(new Block(10, 7, 51, color));
        blocks.add(new Block(10, 11, 51, color));
    }

    /**
     * Draws the snake by calling the each blocks draw() method
     */
    public void draw(Graphics graphics) {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).draw(graphics);
        }
    }

    /**
     * Moves the wall for repositioning
     * @param x - x axis value
     */
    public void move(int x) {
        for (int i = 0; i < blocks.size(); i++) {
            blocks.get(i).move(blocks.get(i).getX() + x, blocks.get(i).getY());
        }
    }

    /**
     *
     */
    public int getBlocksSize() { return blocks.size(); }

    /**
     *
     */
    public Block getBlock(int index) { return blocks.get(index); }

    /**
     *
     */
    public void removeBlock(int index) { blocks.remove(index); }

}
