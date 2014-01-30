package controller;

import view.*;
import view.tools.Position;

import javax.swing.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 */
public class MainControler {

    private MainPanel view = new MainPanel();
    private Level world;
    private Level currentDepth;
    private List<Entity> entities = MainPanel.entities;

    public MainControler(String xmlStr) {
        world = new Level(xmlStr);
        currentDepth = world;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window(view);
            }
        });



    }

    private void resetPlayerPosition(){
        view.player.position = new Position(400,400);
    }

    private void setupLevelsEntities(){
        entities.clear();
        entities.add(view.player);

        for(Entrance entrance : currentDepth.entrances){

        }
    }
}
