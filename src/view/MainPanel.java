package view;

import view.tools.LoopedActions;
import view.tools.Position;
import view.tools.TextDrawer;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Damian
 */
public class MainPanel extends JPanel {

    TextDrawer textDrawer;
    private java.util.Timer timer;
    public static boolean isRunning;

    public Player player;
    Floor floor;

    public static java.util.List<Entity> entities = new CopyOnWriteArrayList<Entity>();//Collections.synchronizedList(new ArrayList<Entity>());

    public String contextualString = null;

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

        floor = new Floor(Floor.FLOOR_TYPE.LIGHT_STONE);

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

        int marginHorizontal = floor.width*2;
        int marginVertical = floor.height*2;
        for(int i = marginHorizontal; i < (this.getWidth()-marginHorizontal); i+=floor.width) {
            for(int y = marginVertical; y < (this.getHeight()-marginVertical); y+=floor.height) {
                floor.paint(g2D, new Position(i, y));
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
        if(isRunning) {
            textDrawer.draw(g2D, new Position((this.getWidth()/2)-70, 30), "PLAYYYYY come on !!");
        }
        else {
            textDrawer.draw(g2D, new Position((this.getWidth()/2)-70, 30), "game paused, c'on, continue !");
        }

        if(null != contextualString){
            textDrawer.draw(g2D, new Position((this.getWidth()/2)-70, 200), contextualString);
        }
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

    public static boolean isPossibleMove(Player player, java.util.List<Floor> floors, Position nextPosition) {
        boolean topCornerFound = false, bottomCornerFound = false;
        for (Floor floor : floors) {
            if (floor.position.x <= nextPosition.x && floor.position.y <= nextPosition.y)
                topCornerFound = true;
            if ((floor.position.x + floor.width) <= (nextPosition.x + player.width) && (floor.position.y + floor.height) <= (nextPosition.y + player.height))
                bottomCornerFound = true;
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