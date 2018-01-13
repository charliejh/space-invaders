package SpaceInvaders;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{

    /**
     * Constructs a non-resizable frame
     */
    public Frame() {
        setTitle("Space Invaders - Charlie Harris");
        setSize(80 * 10, 60 * 10 + 25);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0,0,0));
        setResizable(false);
    }

}
