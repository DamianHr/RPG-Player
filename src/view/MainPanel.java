package view;

import view.tools.Position;
import view.tools.TextDrawer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Damian
 */
public class MainPanel extends JPanel {

    TextDrawer textDrawer;

    public MainPanel() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.WHITE);
        textDrawer = new TextDrawer();

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;
        //Image image = getToolkit().getImage(TextDrawer.class.getResource("/view/tools/letters.png"));
        //if(image != null) g.drawImage(image, 200, 20, this); // ... on la dessine
        textDrawer.draw(g2D, new Position(10,10), "Géniale! Cool Cool");
    }
}
