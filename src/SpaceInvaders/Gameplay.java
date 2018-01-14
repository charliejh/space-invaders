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
    private ArrayList<Spaceship> lifeRemaining = new ArrayList<>();
    private SpaceshipInvader spaceshipInvader;
    private ArrayList<Block> spaceshipInvaderBullets = new ArrayList<>();
    private ArrayList<Invader> invaders = new ArrayList<>();
    private ArrayList<Block> invaderBullets = new ArrayList<>();
    private ArrayList<Wall> walls = new ArrayList<>();
    private boolean gameOver = false;
    private boolean gamePaused = false;
    private int score = 0;
    private Timer repaintTimer = new Timer(10, e-> repaintFrame());
    private Timer spaceshipInvaderMove = new Timer(40, event -> moveSpaceshipInvader());
    private Timer spaceshipInvaderShootTimer = new Timer(200, event -> spaceshipInvaderShoot());
    private Timer spaceshipInvadersBulletsTimer = new Timer(40, event -> spaceshipInvaderBulletsUpdate());
    
    /**
     * Constructor
     */
    public Gameplay(Frame frame) {
        this.frame = frame;
        spaceship = new Spaceship(new Color(140, 255, 93));
        for (int i = 0; i < spaceship.getLife(); i++) {
            lifeRemaining.add(new Spaceship(new Color(140, 255, 93)));
            if (i == 0) {
                lifeRemaining.get(i).move(36);
                lifeRemaining.get(i).moveY(-55);
            }
            else if (i == 1) {
                lifeRemaining.get(i).move(42);
                lifeRemaining.get(i).moveY(-55);
            }
            else if (i == 2) {
                lifeRemaining.get(i).move(48);
                lifeRemaining.get(i).moveY(-55);
            }
        }
        spaceshipInvader = new SpaceshipInvader(Color.red);
        createWalls();
        createInvaders();
        frame.addKeyListener(this);
    }

    /**
     * Starts the game by starting all timers
     */
    public void start() {
        repaintTimer.start();
        Timer invaderTimer = new Timer(1000, e -> moveInvaders());
        invaderTimer.start();
        Timer invaderShootTimer = new Timer(2000, e -> invadersShoot());
        invaderShootTimer.start();
        Timer bulletTimer = new Timer(40, e -> spaceshipBulletUpdate());
        bulletTimer.start();
        Timer invaderBulletTimer = new Timer(40, e -> invadersBulletUpdate());
        invaderBulletTimer.start();
        Timer spaceshipInvaderTimer = new Timer(10000, e -> {
            spaceshipInvaderMove.start();
            spaceshipInvaderShootTimer.start();
            spaceshipInvadersBulletsTimer.start();
        });
        spaceshipInvaderTimer.start();
    }

    /**
     * Creates and places the walls
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
     * Creates and places the invaders
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
     * Determines which direction the invaders should move and moves them
     */
    private void moveInvaders() {
        if (!gameOver && !gamePaused) {
            if (invaders.get(0).getDirection().equals("LEFT")) moveInvadersLeft();
            else if (invaders.get(0).getDirection().equals("DOWN")) moveInvadersDown();
            else if (invaders.get(0).getDirection().equals("RIGHT")) moveInvadersRight();
        }
    }

    /**
     * Handles moving all invaders one blocksize to the right
     */
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

    /**
     * Handles moving all invaders one blocksize down
     * If the invaders reach the walls, game over!
     */
    private void moveInvadersDown() {
        outerloop:
        for (int i = 0; i < invaders.size(); i++) {
            invaders.get(i).move(0, 1);
            for (int j = 0; j < invaders.get(i).getBlockSize(); j++) {
                if (invaders.get(i).getBlock(j).getY() > 48) {
                    gameOver = true;
                    break outerloop;
                }
            }
            if (invaders.get(i).getPreviousDirection().equals("RIGHT")) invaders.get(i).setDirection("LEFT");
            else if (invaders.get(i).getPreviousDirection().equals("LEFT")) invaders.get(i).setDirection("RIGHT");
        }
    }

    /**
     * Handles moving all invaders one blocksize to the left
     */
    private void moveInvadersLeft() {
        int x = 80;
        for (int i = 0; i < invaders.size(); i++) {
            for (int j = 0; j < invaders.get(i).getBlockSize(); j++) {
                if (invaders.get(i).getBlock(j).getX() < x) x = invaders.get(i).getBlock(j).getX();
            }
        }
        for (int i = 0; i < invaders.size(); i++) {
            if (x > 1) invaders.get(i).move(-1, 0);
            if (x <= 1) {
                invaders.get(i).setPreviousDirection(invaders.get(i).getDirection());
                invaders.get(i).setDirection("DOWN");
            }
        }
    }

    /**
     * Determines which invaders are to shoot and adds a bullet to the invaderBullets ArrayList in the appropriate position
     */
    private void invadersShoot() {
        if (!gameOver && !gamePaused) {
            Random random = new Random();
            if (invaders.size() == 1) {
                if (random.nextInt(2) < 0) invaderBullets.add(new Block(10, invaders.get(0).getBlock(3).getX(), invaders.get(0).getBlock(3).getY() + 1, Color.white));
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
     * Handles moving the invaders bullets
     * Determines if the the bullet hits a wall, hits the spaceship of goes off screen and acts accordingly
     */
    private void invadersBulletUpdate() {
        if (!gameOver && !gamePaused) {
            outerloop:
            for (int i = 0; i < invaderBullets.size(); i++) {
                invaderBullets.get(i).move(invaderBullets.get(i).getX(), invaderBullets.get(i).getY() + 1);
                if (invaderBullets.get(i).getY() > 81) {
                    invaderBullets.remove(i);
                    continue;
                }
                for (int j = 0; j < spaceship.getNumberOfBlocks(); j++) {
                    if (invaderBullets.get(i).getX() == spaceship.getBlock(j).getX() && invaderBullets.get(i).getY() == spaceship.getBlock(j).getY()) {
                        lifeRemaining.remove(lifeRemaining.size() - 1);
                        spaceship.deductLife();
                        if (spaceship.getLife() == 0) gameOver = true;
                        invaderBullets.remove(i);
                        continue outerloop;
                    }
                }
                for (int j = 0; j < walls.size(); j++) {
                    for (int wb = 0; wb < walls.get(j).getBlocksSize(); wb++) {
                        if (invaderBullets.get(i).getX() == walls.get(j).getBlock(wb).getX() && invaderBullets.get(i).getY() == walls.get(j).getBlock(wb).getY()) {
                            walls.get(j).removeBlock(wb);
                            invaderBullets.remove(i);
                            continue outerloop;
                        }
                    }
                }
            }
        }
    }

    /**
     * Handles moving the spaceships bullet
     * Determines if the the bullet hits a wall, hits an invader of goes off screen and acts accordingly
     */
    private void spaceshipBulletUpdate() {
        if (!gameOver && !gamePaused) {
            outerloop:
            for (int i = 0; i < spaceship.getBulletsSize(); i++) {
                spaceship.getBullet(i).move(spaceship.getBullet(i).getX(), spaceship.getBullet(i).getY() - 1);
                if (spaceship.getBullet(i).getY() < 0) {
                    spaceship.removeBullet(i);
                    continue;
                }
                for (int w = 0; w < walls.size(); w++) {
                    for (int wb = 0; wb < walls.get(w).getBlocksSize(); wb++) {
                        if (spaceship.getBullet(i).getX() == walls.get(w).getBlock(wb).getX() && spaceship.getBullet(i).getY() == walls.get(w).getBlock(wb).getY()) {
                            walls.get(w).removeBlock(wb);
                            spaceship.removeBullet(i);
                            continue outerloop;
                        }
                    }
                }
                for (int j = 0; j < invaders.size(); j++) {
                    for (int k = 0; k < invaders.get(j).getBlockSize(); k++) {
                        if (spaceship.getBullet(i).getX() == invaders.get(j).getBlock(k).getX() && spaceship.getBullet(i).getY() == invaders.get(j).getBlock(k).getY()) {
                            if (invaders.get(j).getLife() - 1 < 1) {
                                invaders.remove(j);
                                spaceship.removeBullet(i);
                                score++;
                                if (invaders.isEmpty()) {
                                    gameOver = true;
                                    break outerloop;
                                }
                                continue outerloop;
                            }
                            else {
                                invaders.get(j).deductLife();
                                spaceship.removeBullet(i);
                                continue outerloop;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Moves the spaceship invader to the left
     * The timer stops when the spaceship invader is off screen and the spaceship invader is repositioned
     */
    private void moveSpaceshipInvader() {
        if (!gamePaused && !gameOver) {
            if (spaceshipInvader.getBlock(4).getX() < -100) {
                spaceshipInvaderMove.stop();
                spaceshipInvaderShootTimer.stop();
                spaceshipInvadersBulletsTimer.stop();
                spaceshipInvader.moveOffScreen();
            }
            spaceshipInvader.move(-1);
        }
    }

    /**
     * Spaceship invader shoots
     * A block is added to the spaceshipInvadersBullets ArrayList ready to move
     */
    private void spaceshipInvaderShoot() {
        if (!gameOver && !gamePaused) {
            spaceshipInvaderBullets.add(new Block(10, spaceshipInvader.getBlock(10).getX(),  spaceshipInvader.getBlock(10).getY() + 1, Color.red));
        }
    }

    /**
     * Move the spaceship invaders bullets
     * Determines if the the bullet hits a wall, hits the spaceship of goes off screen and acts accordingly
     */
    private void spaceshipInvaderBulletsUpdate() {
        if (!gameOver && !gamePaused) {
            outerloop:
            for (int i = 0; i < spaceshipInvaderBullets.size(); i++) {
                spaceshipInvaderBullets.get(i).move(spaceshipInvaderBullets.get(i).getX(), spaceshipInvaderBullets.get(i).getY() + 1);
                if (spaceshipInvaderBullets.get(i).getY() > 81) {
                    spaceshipInvaderBullets.remove(i);
                    continue;
                }
                for (int j = 0; j < walls.size(); j++) {
                    for (int k = 0; k < walls.get(j).getBlocksSize(); k++) {
                        if (spaceshipInvaderBullets.get(i).getX() == walls.get(j).getBlock(k).getX() && spaceshipInvaderBullets.get(i).getY() == walls.get(j).getBlock(k).getY()) {
                            walls.get(j).removeBlock(k);
                            spaceshipInvaderBullets.remove(i);
                            continue outerloop;
                        }
                    }
                }
                for (int j = 0; j < spaceship.getNumberOfBlocks(); j++) {
                    if (spaceshipInvaderBullets.get(i).getX() == spaceship.getBlock(j).getX() && spaceshipInvaderBullets.get(i).getY() == spaceship.getBlock(j).getY()) {
                        lifeRemaining.remove(lifeRemaining.size() - 1);
                        spaceship.deductLife();
                        if (spaceship.getLife() == 0) gameOver = true;
                        spaceshipInvaderBullets.remove(i);
                        continue outerloop;
                    }
                }
            }
        }
    }

    /**
     * Calls all draw() methods of all objects that need to be painted to the JComponent
     */
    protected void paintComponent(Graphics graphics) {
        drawScoreboard(graphics);
        spaceship.draw(graphics);
        for (int i = 0; i < lifeRemaining.size(); i++) {
            lifeRemaining.get(i).draw(graphics);
        }
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
        spaceshipInvader.draw(graphics);
        for (int i = 0; i < spaceshipInvaderBullets.size(); i++) {
            spaceshipInvaderBullets.get(i).draw(graphics);
        }
        if (gameOver) drawPauseScreen(graphics, "GAME OVER!", 250);
        if (gamePaused) drawPauseScreen(graphics, "PAUSED...", 280);
    }

    /**
     * Draws the scoreboard at the top of the JComponent
     */
    private void drawScoreboard(Graphics graphics) {
        graphics.setColor(Color.white);
        graphics.setFont(new Font("New Times Roman", Font.PLAIN, 20));
        graphics.drawString("SPACE INVADERS CLONE", 30, 38);
        graphics.drawString("LIFE: ", 430, 38);
        graphics.drawString("SCORE: " + score, 680, 38);
    }

    /**
     * Draws the pause screen
     */
    private void drawPauseScreen(Graphics graphics, String text, int x) {
        graphics.setColor(new Color(0,0,0, 120));
        graphics.fillRect(0,0,80 * 10, 80 * 10 + 25);
        graphics.setColor(Color.white);
        graphics.setFont(new Font("New Times Roman", Font.PLAIN, 40));
        graphics.drawString(text, x, 300);
        if (text.equals("GAME OVER!")) repaintTimer.stop();
    }

    /**
     * Repaints the frame
     */
    private void repaintFrame() { frame.repaint(); }

    /**
     * KeyListener methods
     */
    @Override
    public void keyTyped(KeyEvent e) { }
    /**
     * Handles moving the spaceship and shooting
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && !gamePaused) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (spaceship.getBlock(0).getX() >= 2) spaceship.move(-2);
                    break;
                case KeyEvent.VK_RIGHT:
                    if (spaceship.getBlock(4).getX() <= 78) spaceship.move(2);
                    break;
                case KeyEvent.VK_SPACE:
                    if (spaceship.getBulletsSize() < 5) spaceship.shoot();
                    break;
                case KeyEvent.VK_P:
                    gamePaused = true;
            }
        }
        if (gamePaused) {
            if (e.getKeyCode() == KeyEvent.VK_R) gamePaused = false;
        }
    }
    @Override
    public void keyReleased(KeyEvent e) { }

}
