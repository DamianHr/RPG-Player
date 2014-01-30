package view;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Voodoo
 * Date: 30/01/14
 * Time: 23:03
 * To change this template use File | Settings | File Templates.
 */
public class EntranceLevelManager{
    private HashMap<Integer, ArrayList<Entrance>> awaitingEntrances = new HashMap<Integer, ArrayList<Entrance>>();
    private HashMap<Integer, Level> foundLevels = new HashMap<Integer, Level>();

    private void queueEntrance(Entrance entrance, int targetLevel){
        ArrayList<Entrance> awaiting = awaitingEntrances.get(targetLevel);
        if(null == awaiting){
            awaiting = new ArrayList<Entrance>();
            awaitingEntrances.put(targetLevel,awaiting);
        }
        awaiting.add(entrance);
    }

    public void linkToLevel(Entrance entrance, int targetLevel){
        Level level = foundLevels.get(targetLevel);

        if(null == level){
            queueEntrance(entrance,targetLevel);
        }else{
            entrance.target = level;
        }
    }

    public void addLevel(Level level){
        foundLevels.put(level.id, level);

        ArrayList<Entrance> awaiting =awaitingEntrances.get(level.id);
        if(null != awaiting){
            for(Entrance e : awaiting){
                e.target = level;
            }
            awaitingEntrances.put(level.id, null);
        }
    }
}