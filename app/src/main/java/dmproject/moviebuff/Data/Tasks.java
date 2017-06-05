package dmproject.moviebuff.Data;

import java.util.ArrayList;

import dmproject.moviebuff.Data.Task;

/**
 * Created by Dmitry on 01.05.2016.
 */
public class Tasks {

    public ArrayList<Task> tasks;

    public Tasks(int level){
        tasks = new ArrayList<>();
        tasks.add(new Task(1, level));
        tasks.add(new Task(2, level));
        tasks.add(new Task(3, level));
        tasks.add(new Task(4, level));
        tasks.add(new Task(5, level));
        tasks.add(new Task(6, level));
        tasks.add(new Task(7, level));
        tasks.add(new Task(8, level));
        tasks.add(new Task(9, level));
        tasks.add(new Task(10, level));
        tasks.add(new Task(11, level));
        tasks.add(new Task(12, level));
    }
}
