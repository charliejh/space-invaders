package SpaceInvaders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Gameplay extends JComponent implements KeyListener {

    private Frame frame;
    private Spaceship spaceship;
    private ArrayList<Invader> invaders = new ArrayList<>();
    private ArrayList<Block> invaderBullets = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private boolean gameOver = false;
    
    /**
     *
     */
    public Gameplay(Frame frame) {
        this.frame = frame;
        spaceship = new Spaceship(new Color(140, 255, 93));
        createWalls();
        createInvaders();
        frame.addKeyListener(this);
    }

    /**
     * Starts the game
     */
    public void start() {
        Timer invaderTimer = new Timer(1000, e -> invaderUpdate());
        invaderTimer.start();
        Timer invaderShootTimer = new Timer(2000, e -> invaderShoot());
        invaderShootTimer.start();
        Timer bulletTimer = new Timer(50, e -> bulletUpdate());
        bulletTimer.start();
        Timer invaderBulletTimer = new Timer(50, e -> invaderBulletUpdate());
        invaderBulletTimer.start();
    }

    /**
     *
     */
    private void invaderShoot() {
        if (!gameOver) {
            Random random = new Random();
            if (invaders.size() == 1) {
                if (random.nextInt(2) < 0) {
                    invaderBullets.add(new Block(10, invaders.get(0).getBlock(3).getX(), invaders.get(0).getBlock(3).getY() + 1, Color.white));
                }
            }
            else {
                int numberOfShots = random.nextInt(invaders.size() / 2);
                for (int i = 0; i < numberOfShots; i++) {
                    int invaderToShoot = random.nextInt(invaders.size());
                    invaderBullets.add(new Block(10, invaders.get(invaderToShoot).getBlock(3).getX(), invaders.get(invaderToShoot).getBlock(3).getY() + 1, Color.white));
                }
            }
        }
    }

    /**
     *
     */
    private void invaderUpdate() {
        if (!gameOver) {
            if (invaders.get(0).getDirection().equals("LEFT")) {
                moveInvadersLeft();
            }
            else if (invaders.get(0).getDirection().equals("DOWN")) {
                moveInvadersDown();
            }
            else if (invaders.get(0).getDirection().equals("RIGHT")) {
                moveInvadersRight();
            }
            frame.repaint();
        }
    }

    private void moveInvadersRight() {
        int x = 0;
        for (int i = 0; i < invaders.size(); i++) {
            for (int j = 0; j < invaders.get(i).getBlockSize(); j++) {
                if (invaders.get(i).getBlock(j).getX() > x) {
                    x = invaders.get(i).getBlock(j).getX();
                }
            }
        }
        for (int i = 0; i < invaders.size(); i++) {
            if (x < 78) {
                invaders.get(i).move(1, 0);
            }
            if (x >= 78) {
                invaders.get(i).setPreviousDirection(invaders.get(i).getDirection());
                invaders.get(i).setDirection("DOWN");
            }
        }
    }

    private void moveInvadersDown() {
            for (int i = 0; i < invaders.size(); i++) {
                invaders.get(i).move(0, 1);
                if (invaders.get(i).getPreviousDirection().equals("RIGHT")) {
                    invaders.get(i).setDirection("LEFT");
                }
                else if (invaders.get(i).getPreviousDirection().equals("LEFT")) {
                    invaders.get(i).setDirection("RIGHT");
                }
            }

    }

    private void moveInvadersLeft() {
            int x = 80;
            for (int i = 0; i < invaders.size(); i++) {
                for (int j = 0; j < invaders.get(i).getBlockSize(); j++) {
                    if (invaders.get(i).getBlock(j).getX() < x) {
                        x = invaders.get(i).getBlock(j).getX();
                    }
                }
            }
        for (int i = 0; i < invaders.size(); i++) {
            if (x > 1) {
                invaders.get(i).move(-1, 0);
            }
            if (x <= 1) {
                invaders.get(i).setPreviousDirection(invaders.get(i).getDirection());
                invaders.get(i).setDirection("DOWN");
            }
        }
    }

    /**
     *
     */
    private void invaderBulletUpdate() {
        if (!gameOver && invaderBullets.size() > 0) {
            for (int i = 0; i < invaderBullets.size(); i++) {
                invaderBullets.get(i).move(invaderBullets.get(i).getX(), invaderBullets.get(i).getY() + 1);
                if (invaderBullets.get(i).getY() > 81) {
                    invaderBullets.remove(i);
                    break;
                }
                for (int j = 0; j < spaceship.getNumberOfBlocks(); j++) {
                    if (invaderBullets.get(i).getX() == spaceship.getBlock(j).getX() && invaderBullets.get(i).getY() == spaceship.getBlock(j).getY()) {
                        spaceship.deductLife();
                        if (spaceship.getLife() == 0) {
                            gameOver = true;
                        }
                        invaderBullets.remove(i);
                        break;
                    }
                }
                wallsloop:
                for (int j = 0; j < walls.size(); j++) {
                    for (int wb = 0; wb < walls.get(j).getBlocksSize(); wb++) {
                        if (invaderBullets.get(i).getX() == walls.get(j).getBlock(wb).getX() && invaderBullets.get(i).getY() == walls.get(j).getBlock(wb).getY()) {
                            walls.get(j).removeBlock(wb);
                            invaderBullets.remove(i);
                            break wallsloop;
                        }
                    }
                }
            }
            frame.repaint();
        }
    }

    /**
     *
     */
    private void bulletUpdate() {
        if (!gameOver && spaceship.getBulletsSize() > 0) {
            for (int i = 0; i < spaceship.getBulletsSize(); i++) {
                spaceship.getBullet(i).move(spaceship.getBullet(i).getX(), spaceship.getBullet(i).getY() - 1);
                if (spaceship.getBullet(i).getY() < 0) {
                    spaceship.removeBullet(i);
                    break;
                }
                wallsloop:
                for (int w = 0; w < walls.size(); w++) {
                    for (int wb = 0; wb < walls.get(w).getBlocksSize(); wb++) {
                        if (spaceship.getBullet(i).getX() == walls.get(w).getBlock(wb).getX() && spaceship.getBullet(i).getY() == walls.get(w).getBlock(wb).getY()) {
                            walls.get(w).removeBlock(wb);
                            spaceship.removeBullet(i);
                            break wallsloop;
                        }
                    }

                }
                invadersloop:
                for (int j = 0; j < invaders.size(); j++) {
                    for (int k = 0; k < invaders.get(j).getBlockSize(); k++) {
                        if (spaceship.getBullet(i).getX() == invaders.get(j).getBlock(k).getX() && spaceship.getBullet(i).getY() == invaders.get(j).getBlock(k).getY()) {
                            if (invaders.get(j).getLife() - 1 < 1) {
                                invaders.remove(j);
                                spaceship.removeBullet(i);
                                break invadersloop;
                            } else {
                                invaders.get(j).deductLife();
                                spaceship.removeBullet(i);
                                break invadersloop;
                            }
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
        int x = -8;
        int y = 0;
        for (int i = 0; i < 22; i++) {
            invaders.add(new Invader(Color.white));
            x += 6;
            if (x == 64) {
                x = -2;
                y = 6;
            }
            invaders.get(i).move(x, y);
        }
    }

    /**
     *
     */
    private void createWalls() {
        for (int i = 0; i < 4; i++) {
            walls.add(new Wall(new Color(140, 255, 93)));
        }
        walls.get(1).move(20);
        walls.get(2).move(40);
        walls.get(3).move(60);
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
        for (int i = 0; i < invaderBullets.size(); i++) {
            invaderBullets.get(i).draw(graphics);
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
        if (!gameOver) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (spaceship.getBlock(0).getX() >= 2) {
                        spaceship.move(-2);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (spaceship.getBlock(4).getX() <= 78) {
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
    }
    @Override
    public void keyReleased(KeyEvent e) { }

}
