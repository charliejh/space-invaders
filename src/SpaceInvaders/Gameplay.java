package SpaceInvaders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Gameplay extends JComponent implements KeyListener {

    Frame frame;
    Spaceship spaceship;
    ArrayList<Invader> invaders = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();


    /**
     *
     */
    public Gameplay(Frame frame) {
        this.frame = frame;
        spaceship = new Spaceship(new Color(140, 188, 93));
        createWalls();
        createInvaders();
        frame.addKeyListener(this);
    }

    /**
     * Starts the game
     */
    public void start() {
        Timer invaderTimer = new Timer(500, e -> invaderUpdate());
        invaderTimer.start();
        Timer bulletTimer = new Timer(50, e -> bulletUpdate());
        bulletTimer.start();
    }

    /**
     *
     */
    private void invaderUpdate() {
        frame.repaint();
    }

    /**
     *
     */
    private void bulletUpdate() {
        outerloop:
        for (int i = 0; i < spaceship.getBulletsSize(); i++) {
            spaceship.getBullet(i).move(spaceship.getBullet(i).getX(), spaceship.getBullet(i).getY() - 1);
            if (spaceship.getBullet(i).getY() < 0) {
                spaceship.removeBullet(i);
                break outerloop;
            }
            for (int w = 0; w < walls.size(); w++) {
                for (int wb = 0; wb < walls.get(w).getBlocksSize(); wb++) {
                    if (spaceship.getBullet(i).getX() == walls.get(w).getBlock(wb).getX() && spaceship.getBullet(i).getY() == walls.get(w).getBlock(wb).getY()) {
                        walls.get(w).removeBlock(wb);
                        spaceship.removeBullet(i);
                        break outerloop;
                    }
                }

            }
            for (int j = 0; j < invaders.size(); j++) {
                for (int k = 0; k < invaders.get(j).getBlockSize(); k++) {
                    if (spaceship.getBullet(i).getX() == invaders.get(j).getBlock(k).getX() && spaceship.getBullet(i).getY() == invaders.get(j).getBlock(k).getY()) {
                        if (invaders.get(j).getLife() - 1 < 1) {
                            invaders.remove(j);
                            spaceship.removeBullet(i);
                            break outerloop;
                        }
                        else {
                            invaders.get(j).deductLife();
                            spaceship.removeBullet(i);
                            break outerloop;
                        }
                    }
                }
            }
        }
        frame.repaint();
    }

    /**
     *
     */
    private void createInvaders() {
        for (int i = 0; i < 10; i++) {
            invaders.add(new Invader(Color.white));
        }
        invaders.get(1).move(10, 0);
        invaders.get(2).move(20, 0);
        invaders.get(3).move(30, 0);
        invaders.get(4).move(40, 0);
        invaders.get(5).move(-5, 6);
        invaders.get(6).move(5, 6);
        invaders.get(7).move(15, 6);
        invaders.get(8).move(25, 6);
        invaders.get(9).move(36, 6);

    }

    /**
     *
     */
    private void createWalls() {
        for (int i = 0; i < 3; i++) {
            walls.add(new Wall(new Color(140, 188, 93)));
        }
        walls.get(1).move(20);
        walls.get(2).move(40);
    }

    /**
     *
     * @param graphics
     */
    public void paintComponent(Graphics graphics) {
        spaceship.draw(graphics);
        spaceship.drawBullets(graphics);
        for (int i = 0; i < walls.size(); i++) {
            walls.get(i).draw(graphics);
        }
        for (int i = 0; i < invaders.size(); i++) {
            invaders.get(i).draw(graphics);
        }
    }

    /**
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) { }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                if (spaceship.getBlock(0).getX() >= 2) {
                    spaceship.move(-2);
                }
                break;
            case KeyEvent.VK_RIGHT:
                if (spaceship.getBlock(4).getX() <= 58) {
                    spaceship.move(2);
                }
                break;
            case KeyEvent.VK_SPACE:
                if (spaceship.getBulletsSize() < 5) {
                    spaceship.shoot();
                }
                break;
        }
        frame.repaint();
    }
    @Override
    public void keyReleased(KeyEvent e) { }

}