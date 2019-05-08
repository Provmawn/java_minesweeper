import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        Assets assets = new Assets();
        MineSweeper frame = new MineSweeper();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 408);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
