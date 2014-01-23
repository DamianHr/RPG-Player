package model;

/**
 * Created by Damian
 */
public class Answer {
    public String text;
    public int value;
    public int goTo;

    public Answer(String text, int value, int goTo) {
        this.text = text;
        this.value = value;
        this.goTo = goTo;
    }
}