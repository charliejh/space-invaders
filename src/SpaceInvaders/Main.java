package SpaceInvaders;

public class Main {

    public static void main(String[] args) {
        Frame frame = new Frame();
        Gameplay gameplay = new Gameplay(frame);
        frame.add(gameplay);
        frame.setVisible(true);
        gameplay.start();
    }

}
