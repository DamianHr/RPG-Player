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

    private int padding = Floor.width * 2;
    private int displayAnswerDistance = 50;

    private void setupLevelsEntities(){
        resetPlayerPosition();
        entities.clear();
        entities.add(view.player);

        view.actions.clear();

        view.questionString = currentDepth.question;
        view.situationString = currentDepth.exposition;

        if(0 == currentDepth.entrances.size()){
            //TODO end game
            view.contextualString = "THE END";
            return;
        }


        int middle = 400;
        int bellowPortal = 150 + Portal.height ;

        view.floors.clear();

        for(int i = 800; i > bellowPortal; i-=Floor.height){
            view.floors.add(new Floor(Floor.FLOOR_TYPE.LIGHT_STONE, new Position(middle,i)));
        }

        for(int  i = padding; i < 800 - padding; i += Floor.width){
            view.floors.add(new Floor(Floor.FLOOR_TYPE.LIGHT_STONE, new Position(i,bellowPortal)));
        }



        //TODO dynamic witdh
        int spacer = ((800 - padding * 2) +Portal.width) /*(Portal.width * currentDepth.entrances.size()))*/ / currentDepth.entrances.size();


        //TODO there are other ways to do that but I was to lazy to change the whole setup, thus the ugly workaround
        class ContextToken {
            int index = 0;
            int count = currentDepth.entrances.size();
            boolean textWanted = false;
        }

        final ContextToken token = new ContextToken();

        for(int i = 0; i < token.count; ++i){

            final Entrance entrance = currentDepth.entrances.get(i);
            final int xPortal = padding+i* spacer;
            final int yPortal = 150;
            Position portalPos = new Position(xPortal, yPortal);
            entities.add(new Portal(portalPos, Portal.PORTAL_TYPE.IN));
            view.floors.add(new Floor(Floor.FLOOR_TYPE.GREY_STONE, portalPos));

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
                        token.textWanted = true;
                   }

                    if((Portal.width/2) >= distanceToPortal){
                        currentDepth = entrance.target;
                        playerScore += entrance.value;
                        setupLevelsEntities();
                    }

                    if(token.count == ++token.index){
                        if(!token.textWanted)
                            view.contextualString = null;
                        token.textWanted = false;
                        token.index = 0;
                    }
                }
            });
        }
    }
}
