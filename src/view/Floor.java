package view;

import view.tools.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Damian
 */
public class Floor {
    private final float factor = 1f;
    public int height = 30;
    public int width = 92;
    public Position position;
    BufferedImage bufferedImage;
    Image scaledImage;
    BufferedImage postBuffer;
    int subImageSubPositionX = 0;
    FLOOR_TYPE currentType;

    public Floor(FLOOR_TYPE currentType) {
        this.currentType = currentType;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init() throws IOException {
        postBuffer = ImageIO.read(Player.class.getResource("/view/libs/image/floors.png"));
        int scaleX = (int) (postBuffer.getWidth() * factor);
        int scaleY = (int) (postBuffer.getHeight() * factor);
        scaledImage = postBuffer.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
        bufferedImage = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
    }

    public void paint(Graphics2D g, Position position) {
        Position subImagePosition = getSubImagePosition();
        g.drawImage(bufferedImage.getSubimage(subImagePosition.x + subImageSubPositionX, subImagePosition.y, width, height), null, position.x, position.y);
    }

    private Position getSubImagePosition() {
        Position position = new Position();
        switch (currentType) {
            case LIGHT_STONE:
                position.x = 0;
                break;
            case GREEN_STONE:
                position.x = 93;
                break;
            case GREY_STONE:
                position.x = 185;
                break;
            case BLACK_STONE:
                position.x = 277;
                break;
        }
        position.y = 0;
        return position;
    }

    enum FLOOR_TYPE {LIGHT_STONE, GREEN_STONE, GREY_STONE, BLACK_STONE}
}
