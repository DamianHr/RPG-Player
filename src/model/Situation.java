package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damian
 */
public class Situation {
    public int value;
    public String exposition;
    public String question;
    public List<Answer> answers = new ArrayList<Answer>();

    public Situation(int value, String exposition, String question, List<Answer> answers) {
        this.value = value;
        this.exposition = exposition;
        this.question = question;
        this.answers = answers;
    }
}
