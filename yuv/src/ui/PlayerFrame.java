package ui;

import javax.swing.*;

public class PlayerFrame extends JFrame {
    public PlayerFrame(String title, int width, int height) {
        super.setTitle(title);
        super.setBounds(400,200, width, height);
        super.setResizable(false);
        super.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
