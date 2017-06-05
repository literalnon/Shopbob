package dmproject.moviebuff.Data;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import dmproject.moviebuff.Activity.TaskActivity;
import dmproject.moviebuff.Data.Level;
import dmproject.moviebuff.Game;

/**
 * Created by Dmitry on 01.05.2016.
 */
public class Levels {

    public ArrayList<Level> levels;

//-----

    public Levels(){
        levels = new ArrayList<>();
    }

    public int size(){return levels.size();}

    public Levels(int cnt){
        levels = new ArrayList<>();
        for (int i = 1; i < cnt; i++){
            levels.add(new Level(i));
        }

        Game.PointsForAllGame = getAllPoints();
    }

    public Levels(Map<Integer, Integer> m){
        levels = new ArrayList<>();
        for (int i = 0; i < m.size(); ++i){
            levels.add(new Level(i + 1, m.get(i + 1)));
        }
    }

    public void add(Level level){
        levels.add(level);
    }

    public int getAllPoints(){
        return 0;
    }

    public Level get(int level) {
        return levels.get(level - 1);
    }

    public void update(int ind, Level level) {
        levels.remove(ind);
        levels.add(ind, level);
    }


}
