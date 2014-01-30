package view;

import view.tools.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by Damian
 */
public class Player implements Entity {
    private final float factor = 1f;
    public int height = 21;
    public int width = 14;
    public Position position;
    PLAYER_ORIENTATION currentOrientation;
    BufferedImage bufferedImage;
    Image scaledImage;
    BufferedImage postBuffer;
    int subImageSubPositionX = 0;
    private int speed = 3;

    public Player(Position position) {
        this.position = position;
        currentOrientation = PLAYER_ORIENTATION.UP;
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        postBuffer = ImageIO.read(Player.class.getResource("/view/libs/image/player1.png"));
        int scaleX = (int) (postBuffer.getWidth() * factor);
        int scaleY = (int) (postBuffer.getHeight() * factor);
        scaledImage = postBuffer.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
        bufferedImage = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
    }

    public void paint(Graphics2D g) {
        Position subImagePosition = getSubImagePosition();
        g.drawImage(bufferedImage.getSubimage(subImagePosition.x + subImageSubPositionX, subImagePosition.y, width, height), null, position.x, position.y);
    }

    public void update(double deltaTime) {
        if (deltaTime > 10) {
            subImageSubPositionX = subImageSubPositionX >= 72 ? subImageSubPositionX = 0 : subImageSubPositionX + 24;
        }
    }

    private Position getSubImagePosition() {
        Position position = new Position();
        switch (currentOrientation) {
            case DOWN:
                position.x = 1;
                position.y = 1;
                break;
            case UP:
                position.x = 208;
                position.y = 1;
                break;
            case RIGHT:
                position.x = 103;
                position.y = 1;
                break;
            case LEFT:
                position.x = 312;
                position.y = 1;
                break;
        }
        return position;
    }

    public void move(int key) {
        switch(key) {
            case KeyEvent.VK_UP:
                currentOrientation = PLAYER_ORIENTATION.UP;
                position.y -= speed;
                break;
            case KeyEvent.VK_RIGHT:
                currentOrientation = PLAYER_ORIENTATION.RIGHT;
                position.x += speed;
                break;
            case KeyEvent.VK_DOWN:
                currentOrientation = PLAYER_ORIENTATION.DOWN;
                position.y += speed;
                break;
            case KeyEvent.VK_LEFT:
                currentOrientation = PLAYER_ORIENTATION.LEFT;
                position.x -= speed;
                break;
        }
    }

    enum PLAYER_ORIENTATION {UP, DOWN, LEFT, RIGHT}
}
