package dmproject.moviebuff.Data;

/**
 * Created by Dmitry on 01.05.2016.
 */
public class Task {

    protected int NumberTask;//номер задачи
    protected int LevelNum;//уровень к которому относится задача
    protected boolean CompleteTask;//проверка завершенности задачи

    public Task(int NumbTsk, int lvl){
        NumberTask = NumbTsk;
        LevelNum = lvl;
        CompleteTask = false;
    }
}
