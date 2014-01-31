package view.tools;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Created by Damian
 */
public class TextDrawer {

    private final float factor = 1f;
    private final int letterSpacing = 1;
    private final int wordSpacing = 4;
    private final int characterHeigth = 10;
    private final int letterWidth = 6;
    private final int numericWidth = 5;
    BufferedImage bufferedImage;
    Image scaledImage;
    BufferedImage postBuffer;

    public TextDrawer() {
        try {
            init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void init() throws IOException {
        postBuffer = ImageIO.read(TextDrawer.class.getResource("/view/libs/image/letters.png"));
        int scaleX = (int) (postBuffer.getWidth() * factor);
        int scaleY = (int) (postBuffer.getHeight() * factor);
        scaledImage = postBuffer.getScaledInstance(scaleX, scaleY, Image.SCALE_SMOOTH);
        bufferedImage = new BufferedImage(scaleX, scaleY, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(scaledImage, 0, 0, null);
    }

    private void draw(Graphics2D g, Position positionStart, String text) {
        text = deAccent(text).trim();
        int currentIndexX = positionStart.x;
        for (char letter : text.toCharArray()) {
            if (letter == ' ') {
                currentIndexX += wordSpacing;
                continue;
            }
            currentIndexX += letterSpacing;
            drawLetter(g, currentIndexX, positionStart.y, letter);
            currentIndexX += (Character.isLetter(letter) ? letterWidth : numericWidth);
        }
    }

    public void drawExposition(Graphics2D g, String text) {
        Position position = new Position(250, 15);
        draw(g ,position, "Situation : " + text);
    }

    public void drawQuestion(Graphics2D g, String text) {
        Position position = new Position(250, 30);
        draw(g ,position, "Question : " + text);
    }

    public void drawPortalDescription(Graphics2D g, String text) {
        Position position = new Position(250, 45);
        draw(g ,position, "Portal : " + text);
    }

    private void drawLetter(Graphics2D g, int x, int y, char charToDraw) {
        Position subImagePosition = getSubImagePosition(getCharType(charToDraw), charToDraw);
        int width = (getCharType(charToDraw) == CHAR_TYPES.LETTER ? letterWidth : numericWidth);
        int height = characterHeigth;
        g.drawImage(bufferedImage.getSubimage(subImagePosition.x, subImagePosition.y, width, height), null, x, y);
    }

    public String deAccent(String str) {
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }

    private CHAR_TYPES getCharType(char charToFind) {
        if (Character.isLetter(charToFind)) return CHAR_TYPES.LETTER;
        else if (Character.isDigit(charToFind)) return CHAR_TYPES.NUMERIC;
        else return CHAR_TYPES.SYMBOL;
    }

    /**
     * //ABCDEFGHIJKLMNOPQRSTUVWXYZ
     * //0123456789
     * //,.;?!-_
     *
     * @param types      CHAR_TYPES of the character
     * @param charToFind character to identify
     * @return Position, coordonates of the image
     */

    private Position getSubImagePosition(CHAR_TYPES types, char charToFind) {
        Position position = new Position();
        switch (types) {
            case LETTER:
                position.y = Character.isUpperCase(charToFind) ? 0 : 11;
                charToFind = Character.toUpperCase(charToFind);
                position.x = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".indexOf(charToFind) * (letterWidth + 1);
                break;
            case NUMERIC:
                position.x = "1234567890".indexOf(charToFind) * (numericWidth + 1);
                position.y = 22;
                break;
            case SYMBOL:
                position.x = ",.:?!-_$€@%+=/\\><#()[]|*'".indexOf(charToFind) * (numericWidth + 1);
                position.y = 33;
                break;
            default:
                position.x = 0;
                position.y = 0;
        }
        if (position.x < 0 || position.y < 0) {
            position.x = 0;
            position.y = 0;
        }
        return position;
    }

    enum CHAR_TYPES {LETTER, NUMERIC, SYMBOL}
}