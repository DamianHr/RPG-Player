package view;

import view.tools.LoopedActions;
import view.tools.Position;
import view.tools.TextDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Damian
 */
public class MainPanel extends JPanel {

    TextDrawer textDrawer;
    private java.util.Timer timer;
    public static boolean isRunning;

    public Player player;
    public static java.util.List<Floor> floors = new CopyOnWriteArrayList<Floor>();

    public static java.util.List<Entity> entities = new CopyOnWriteArrayList<Entity>();//Collections.synchronizedList(new ArrayList<Entity>());

    public String contextualString = null;
    public String questionString = null;
    public String situationString = null;

    public java.util.List<LoopedActions> actions = new CopyOnWriteArrayList<LoopedActions>();//Collections.synchronizedList(new ArrayList<LoopedActions>());

    public MainPanel() {
        init();
    }

    private void init() {
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new KeyboardListerner());

        //floor = new Floor(Floor.FLOOR_TYPE.LIGHT_STONE);




        textDrawer = new TextDrawer();
//        entities.add(new Portal(new Position(300,300), Portal.PORTAL_TYPE.IN));
//        entities.add(new Portal(new Position(400,300), Portal.PORTAL_TYPE.OUT));
//        entities.add(new Portal(new Position(500,300), Portal.PORTAL_TYPE.FINAL));

        player = new Player(new Position(400,400));
        entities.add(player);
        isRunning = true;
        gameLoop();
    }

    private void gameLoop() {
        timer = new java.util.Timer();
        timer.schedule(new Loopy(), 0, 1000 / 60); //60 FPS
    }

    private class Loopy extends java.util.TimerTask {
        public void run() {
            update();
            repaint();
            if (!isRunning) {
//                timer.cancel();
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2D = (Graphics2D) g;

        int marginHorizontal = Floor.width*2;
        int marginVertical = Floor.height*2;
//        for(int i = marginHorizontal; i < (this.getWidth()-marginHorizontal); i+=floor.width) {
//            for(int y = marginVertical; y < (this.getHeight()-marginVertical); y+=floor.height) {
//                floor.paint(g2D, new Position(i, y));
//            }
//        }

        synchronized (floors){
            Iterator i = floors.iterator();
            while (i.hasNext()){
                Floor floor = (Floor)i.next();
                floor.paint(g2D);

            }
        }


        synchronized (entities){
//        for(Entity entity : entities) {
//            entity.paint(g2D);
//        }

            Iterator i = entities.iterator();
            while (i.hasNext()){
                ((Entity)i.next()).paint(g2D);

            }
        }
//        if(isRunning) {
//            textDrawer.drawExposition(g2D, "PLAYYYYY come on !!");
//        }
//        else {
//            textDrawer.drawExposition(g2D, "game paused, c'on, continue !");
//        }


            textDrawer.drawPortalDescription(g2D, null == contextualString ? "" : contextualString);
            textDrawer.drawQuestion(g2D, null == questionString ? "" : questionString);
            textDrawer.drawExposition(g2D, null == situationString ? "" : situationString);
    }

    public void update() {
        if(!isRunning) return;
        for (Entity entity : entities) {
            entity.update(0);
        }

        if(null != actions ){
            synchronized (actions){
                Iterator i = actions.iterator();
                while (i.hasNext()){
                    ((LoopedActions)i.next()).execute();

                }
            }
        }
//        for(LoopedActions a : actions)
//            a.execute();
    }

    public static boolean isPossibleMove(Player player, Position nextPosition) {

//        boolean retVal = false;
//        for (Floor floor : floors) {
//
//            int xFP = floor.position.x + Floor.width,
//                yFP = floor.position.y + Floor.height,
//                xPP = nextPosition.x + player.width,
//                yPP = nextPosition.y + player.height;
//
//            if(nextPosition.x >= floor.position.x && nextPosition.y >= floor.position.y //
//                    && xPP <= xFP && yPP  >= yFP){
//                floor.currentType = Floor.FLOOR_TYPE.GREEN_STONE;
//                retVal = true;
////                return true;
//
//            }else{
//                floor.currentType = Floor.FLOOR_TYPE.LIGHT_STONE;
//            }
//
//
//        }
//
//        return retVal;

        boolean topCornerFound = false, bottomCornerFound = false;



        for (Floor floor : floors) {

            floor.currentType = Floor.FLOOR_TYPE.LIGHT_STONE;

            int xFP = floor.position.x + Floor.width,
                    yFP = floor.position.y + Floor.height,
                    xPP = nextPosition.x + player.width,
                    yPP = nextPosition.y + player.height;
//            if (floor.position.x <= nextPosition.x && floor.position.y <= nextPosition.y)
            if (nextPosition.x >= floor.position.x && nextPosition.y >= floor.position.y //
                    && nextPosition.x <= xFP && nextPosition.y  <= yFP){
                topCornerFound = true;
                floor.currentType = Floor.FLOOR_TYPE.GREEN_STONE;
            }
//            if ((floor.position.x + floor.width) <= (nextPosition.x + player.width) && (floor.position.y + floor.height) <= (nextPosition.y + player.height))
            if (xPP >= floor.position.x && yPP >= floor.position.y //
                    && xPP <= xFP && yPP  <= yFP){
                bottomCornerFound = true;
                floor.currentType = Floor.FLOOR_TYPE.GREEN_STONE;
            }
        }

        return topCornerFound && bottomCornerFound;
    }

    class KeyboardListerner extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            int key = event.getKeyCode();
            if (isRunning) {
                if (key == KeyEvent.VK_UP || key == KeyEvent.VK_RIGHT || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_LEFT)
                    player.move(key);
            }

            if (key == KeyEvent.VK_P) isRunning = !isRunning;
        }
    }
}