package view;

import java.util.Timer;

/**
 * Created by Damian
 */
public class GameDrawer {
    private java.util.Timer timer;
    public boolean isRunning;

    public GameDrawer() {
        isRunning = true;
        gameLoop();
    }

    public void gameLoop() {
        timer = new Timer();
        timer.schedule(new Loopy(), 0, 1000 / 60); //60 FPS
    }

    private class Loopy extends java.util.TimerTask
    {
        public void run()
        {
            //update objects

            if (!isRunning)
            {
                timer.cancel();
            }
        }
    }
}
