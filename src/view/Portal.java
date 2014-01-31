package view;

import view.tools.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Damian
 */
public class Portal implements Entity {
    private final float factor = 1f;
    public static int height = 28;
    public static int width = 24;
    public Position position;
    private BufferedImage bufferedImage;
    private Image scaledImage;
    private BufferedImage postBuffer;
    private Position subImagePosition;
    public PORTAL_TYPE currentType;

    public Portal(Position position, PORTAL_TYPE currentType) {
        this.position = position;
        this.currentType = currentType;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() throws IOException {
        postBuffer = ImageIO.read(Player.class.getResource("/view/libs/image/portals.png"));
        int scaleX = (int) (postBuffer.getWidth() * factor);
        int scaleY = (int) (postBuffer.getHeight() * factor);
        scaledImage = postBuffer.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
        bufferedImage = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
        subImagePosition = getSubImagePosition();
    }

    @Override
    public void paint(Graphics2D g) {
        g.drawImage(bufferedImage.getSubimage(subImagePosition.x, subImagePosition.y, width, height), null, position.x, position.y);
    }

    @Override
    public void update(double delta) {

    }

    private Position getSubImagePosition() {
        Position position = new Position();
        switch (currentType) {
            case IN:
                position.x = 1;
                position.y = 1;
                break;
            case OUT:
                position.x = 26;
                position.y = 1;
                break;
            case FINAL:
                position.x = 51;
                position.y = 1;
                break;
        }
        return position;
    }

    public enum PORTAL_TYPE {IN, OUT, FINAL}
}
