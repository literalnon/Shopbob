package dmproject.moviebuff.Data;

import android.content.Context;

import java.util.ArrayList;

import dmproject.moviebuff.Data.Task;

/**
 * Created by Dmitry on 01.05.2016.
 */
public class TaskView extends Task {

    public ArrayList<Integer> resourses;
    public String solution;
    Context context;

    public TaskView(int NumbTsk, int lvl, Context contex) {
        super(NumbTsk, lvl);

        resourses = new ArrayList<>();

        for (int i = 1; i < 5; ++i){
            resourses.add(contex.getResources().getIdentifier("lvl" + LevelNum + "_" + NumbTsk + "_" + i, "drawable", "dmproject.moviebuff"));
        }
    }
}
