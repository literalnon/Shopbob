package dmproject.moviebuff.Data;

import android.content.Context;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dmitry on 30.04.2016.
 */
public class Level {

    public int Number;//номер уровня
    final public int PointsToPass;//очки нужные для открытия этого уровня
    public int PointForLevel;//очки заработанные на этом уровне
    protected int ImageNumber;//номер уровня в картинке
    public ArrayList<Integer> FinishedTasks;//массив завершенных задач
    public ArrayList<Integer> FreeTasks;//массив незавершенных задач

    public Level(int Numb){
        Number = Numb;
        PointsToPass = (Numb - 1) * 5;
        PointForLevel = 0;
        FinishedTasks = new ArrayList<>();
        FreeTasks= new ArrayList<>();

    }

    public Level(int Numb, int pnt){
        Number = Numb;
        PointsToPass = Numb * 5;
        PointForLevel = pnt;
    }

    public Level(int Numb, ArrayList<Integer> finishedTasks){
        Number = Numb;
        PointsToPass = (Numb - 1) * 5;
        PointForLevel = finishedTasks.size();
        FinishedTasks = finishedTasks;
        FreeTasks = createFreeTasks();
    }

    private ArrayList<Integer> createFreeTasks() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 1; i < 7; ++i){
            if (!FinishedTasks.contains(i))
                arrayList.add(i);
        }
        return arrayList;
    }

    public int getNumber(){
        return Number;
    }
}
