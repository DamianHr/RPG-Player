package view;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Damian
 */
public class MainPanel extends JPanel {
    public MainPanel() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setBackground(Color.BLACK);
        this.add(new Label());
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);


    }
}
