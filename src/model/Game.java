package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Damian
 */
public class Game {
    public String title;
    public List<Situation> situations = new ArrayList<Situation>();

    public Game(String title, List<Situation> situations) {
        this.title = title;
        this.situations = situations;
    }
}
