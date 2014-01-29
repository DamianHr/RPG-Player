package view;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Damian
 */
public interface Entity {
    public void init() throws IOException;
    public void paint(Graphics2D g);
    public void update(double delta);

}
