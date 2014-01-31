package controller;

import view.*;
import view.tools.LoopedActions;
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

    private int playerScore = 0;

    public MainControler(String xmlStr) {
        world = new Level(xmlStr);
        currentDepth = world;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Window(view);
            }
        });

        setupLevelsEntities();

    }

    private void resetPlayerPosition(){
        view.player.position = new Position(400,400);
    }

    private int padding = 600;
    private int displayAnswerDistance = 50;

    private void setupLevelsEntities(){
        resetPlayerPosition();
        entities.clear();
        entities.add(view.player);

        view.actions.clear();

        if(0 == currentDepth.entrances.size()){
            //TODO end game
            view.contextualString = "THE END";
            return;
        }


        //TODO dynamic witdh
        int spacer = ((800 - padding * 2) +Portal.width) /*(Portal.width * currentDepth.entrances.size()))*/ / currentDepth.entrances.size();

        for(int i = 0; i < currentDepth.entrances.size(); ++i){

            final Entrance entrance = currentDepth.entrances.get(i);
            final int xPortal = padding+i* spacer;
            final int yPortal = 300;
            entities.add(new Portal(new Position(xPortal, yPortal), Portal.PORTAL_TYPE.IN));


            view.actions.add(new LoopedActions() {
                @Override
                public void execute() {
                    int playerX = view.player.position.x;
                    int playerY = view.player.position.y;

                    int xPow = playerX * playerX - 2 * playerX * xPortal + xPortal * xPortal;
                    int yPow = playerY * playerY - 2 * playerY * yPortal + yPortal * yPortal;
                    double distanceToPortal = Math.sqrt(xPow + yPow);

                    //System.out.println("Portal at "+xPortal+","+yPortal+" player at : "+playerX+","+playerY+" distance is : "+distanceToPortal);

                    if( displayAnswerDistance >= distanceToPortal ){
                       view.contextualString = entrance.text;
                   }

                    if((Portal.width/2) >= distanceToPortal){
                        currentDepth = entrance.target;
                        playerScore += entrance.value;
                        setupLevelsEntities();
                    }
                }
            });
        }
    }
}
