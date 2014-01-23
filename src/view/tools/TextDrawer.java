package view.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Damian
 */
public class TextDrawer {

    private final int letterSpacing = 2;
    private final int wordSpacing = 6;
    private final int letterWidth = 6;
    private final int numericWidth = 3;

    Image lettersImage;

    public void init() throws IOException {
        lettersImage = ImageIO.read(this.getClass().getResource("./view/libs/img/letters.png"));
    }

//http://java-buddy.blogspot.fr/2012/11/draw-partial-scaled-image-using.html
    public void draw(Graphics g, Position positionStart, String text) {
        int currentIndexX = positionStart.x;
        for(char letter : text.toCharArray()) {
            currentIndexX += (letter == ' ' ? 6 : 2 );
            g.drawImage(lettersImage, currentIndexX, positionStart.y, 0, 0, 0, 0, 0, 0, null);
            currentIndexX += (Character.isLetter(letter) ? 6 : 3 );
        }

        g2D.dispose();
    }
}
