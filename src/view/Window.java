package view;

import javax.swing.*;
import java.awt.*;

/**
 * User: Damian
 * Date: 23/11/13
 * Time: 23:48
 */
public class Window extends JFrame{

    public Window() {
        init();
    }

    private void init() {
        this.setTitle("RGP GAME");
        this.setSize(800, 600);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) ((screenDimension.getWidth()-this.getWidth())/2),
                (int) ((screenDimension.getHeight() - this.getHeight()) /2));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setJMenuBar(new MenuBar());
        this.setContentPane(new MainPanel());
        this.setVisible(true);
    }
}
