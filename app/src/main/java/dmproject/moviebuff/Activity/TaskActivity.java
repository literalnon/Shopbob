package dmproject.moviebuff.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import dmproject.moviebuff.Adapters.AdapterForAnswer;
import dmproject.moviebuff.DBHelper;
import dmproject.moviebuff.DBPlayers;
import dmproject.moviebuff.Game;
import dmproject.moviebuff.R;
import dmproject.moviebuff.Data.TaskView;

public class TaskActivity extends AppCompatActivity implements View.OnTouchListener{

    TextView textViewAllPoints;
    //ImageView imageView1, imageView2, imageView3, imageView4;
    String answerTasks, answerText;
    ArrayList<Integer> btnArr;
    ArrayList<ImageView> imgList;
    Stack<Integer> selectButton;
    LinearLayout mainLayout;
    float fromPosition;
    static public ArrayList<View> answerButtons;
    int curButtonForAnswer;
    boolean canPauseMusic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        mainLayout = (LinearLayout) findViewById(R.id.mainLayout);
        mainLayout.setOnTouchListener(this);

        selectButton = new Stack<>();

        imgList = new ArrayList<>(4);

        imgList.add((ImageView) findViewById(R.id.image2));
        imgList.add((ImageView) findViewById(R.id.image3));
        imgList.add((ImageView)findViewById(R.id.image4));
        imgList.add((ImageView)findViewById(R.id.image1));

        btnArr = new ArrayList<>(15);

        btnArr.add(R.id.btn1);
        btnArr.add(R.id.btn2);
        btnArr.add(R.id.btn3);
        btnArr.add(R.id.btn4);
        btnArr.add(R.id.btn5);
        btnArr.add(R.id.btn6);
        btnArr.add(R.id.btn7);
        btnArr.add(R.id.btn8);
        btnArr.add(R.id.btn9);
        btnArr.add(R.id.btn10);
        btnArr.add(R.id.btn11);
        btnArr.add(R.id.btn13);
        btnArr.add(R.id.btn14);
        btnArr.add(R.id.btn15);
        btnArr.add(R.id.btn16);

        answerText = new String();

        createScreen();
    }

    public void btnWordsListener(View v){

        if (curButtonForAnswer < answerButtons.size()){

            if (selectButton.empty()){
                answerText = "";
            }

            selectButton.push(v.getId());

            Button button = (Button) findViewById(v.getId());

            answerText += button.getText().toString();

            button.setAlpha(0);
            button.setClickable(false);

            ((Button) answerButtons.get(curButtonForAnswer++)).setText(button.getText().toString());

            if (TextUtils.equals(answerText, answerTasks.toString())) {
                updateVariable();//change variables
                if (Game.isEnd()){
                    ContentValues values = new ContentValues();

                    values.put(DBPlayers.NAME_COLLUMN_NAME, Game.name);
                    values.put(DBPlayers.NAME_COLLUMN_POINTS, Game.PointsForAllGame);

                    DBPlayers db = new DBPlayers(this);
                    SQLiteDatabase database = db.getWritableDatabase();
                    Log.d("db players update : ", database.insert(DBPlayers.NAME_TABLE, null, values) + "");
                    showDialog(1);

                }else {
                    //go next
                    nextTask();
                    Game.printDB(Game.createDB(this));
                }
            }}
    }


    public void nextTask() {
        Game.incTask();
        createScreen();
    }

    public void previousTask() {
        Game.decTask();
        createScreen();
    }

    public void btnDelListener(View view) {
        int length = answerText.length();
        String answ = "";

        if (length > 0)
            for (int i = 0; i < length - 1; ++i)
                answ += answerText.charAt(i);

        answerText =answ;
        if (curButtonForAnswer > 0)
            ((Button) answerButtons.get(--curButtonForAnswer)).setText("");

        if (!selectButton.empty()){
            Button button = (Button) findViewById(selectButton.pop());
            button.setAlpha(1);
            button.setClickable(true);
        }
    }

    public void createPictures(){
        TaskView taskView = new TaskView(Game.getTask(), Game.level, TaskActivity.this);

        for (int i = 0; i < 4; ++i){
            AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
            alphaAnimation.setDuration(1000);
            imgList.get(i).startAnimation(alphaAnimation);
            imgList.get(i).setImageResource(taskView.resourses.get(i));
            alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(2000);
            imgList.get(i).startAnimation(alphaAnimation);
        }
    }

    public void createKeyboard(){
        createTiredKeyBoard();

        answerText = "";

        for (int i = 0; i < answerTasks.length(); ++i){
            boolean flag = true;
            while(flag){
                int ind = (new Random().nextInt())%(btnArr.size() - 1);
                if (ind < 0) ind = -ind;
                Button button = ((Button)findViewById(btnArr.get(ind)));
                button.setClickable(true);
                if (TextUtils.equals(button.getText().toString(), "")){
                    button.setText((answerTasks.charAt(i)+"").toString());
                    flag = false;
                }
            }
        }

        for (int i = 0; i < btnArr.size() - answerTasks.length();++i){
            boolean flag = true;
            while(flag){
                int ind = new Random().nextInt()%15;
                if (ind < 0)
                    ind = -ind;
                Button button = ((Button)findViewById(btnArr.get(ind)));
                button.setClickable(true);
                if (TextUtils.equals(button.getText().toString(), "")){
                    int ind2 = new Random().nextInt() % 30;
                    if (ind2 < 0) ind2 =-ind2;
                    button.setText(getResources().getStringArray(R.array.alphabet)[ind2].toString());
                    flag = false;}
            }
        }
  /**/  }

    public void createAnswer(){

        int curIndex = (Game.level - 1) * 6 + Game.getTask() - 1;

        answerTasks = getResources().getStringArray(R.array.answers)[curIndex];


        GridView gridView = (GridView) findViewById(R.id.AnswerLayout);
        //GridView gridView1 = (GridView)findViewById(R.id.AnswerLayout1);

        //LinearLayout linearLayout = (LinearLayout) findViewById(R.id.AnswerLinearLayout);


        answerButtons = new ArrayList<>();
        curButtonForAnswer = 0;

        String[] arrAnswer = answerTasks.split(" ");

        if (arrAnswer.length > 1) {
            answerTasks = arrAnswer[0];
            ((TextView) findViewById(R.id.help_text)).setText(" " + arrAnswer[1].toString());
        }

        gridView.setNumColumns(arrAnswer[0].length());
        gridView.setAdapter(new AdapterForAnswer(this, arrAnswer[0].toString()));

    }

    public void createTiredKeyBoard(){
        for(int i =0;i < btnArr.size(); ++i){
            Button button = (Button)findViewById(btnArr.get(i));
            button.setText("");
            button.setAlpha(1);
        }
        selectButton = new Stack<>();
    }

    public boolean onTouch(View view, MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                float toPosition = event.getX();
                if (fromPosition - toPosition > getResources().getConfiguration().screenWidthDp/10)
                    nextTask();
                    //Toast.makeText(TaskActivity.this, "" + (fromPosition - toPosition), Toast.LENGTH_SHORT).show();}
                else if (toPosition - fromPosition > getResources().getConfiguration().screenWidthDp/10)
                    previousTask();
            default:
                break;
        }
        return true;
    }

    public void createScreen(){
        createToolBar();
        createPictures();
        createAnswer();
        createKeyboard();
    }

    public void btnBackListener(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, LevelsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        this.finish();
    }

    public void fillDB(){
        ContentValues values = new ContentValues();

        values.put(DBHelper.NAME_COLLUMN_LEVEL, Game.level);
        values.put(DBHelper.NAME_COLLUMN_FINISHED, Game.getIntFromArray(Game.levels.get(Game.level).FinishedTasks));

        SQLiteDatabase database = Game.createDB(this);
        if(database.update(DBHelper.NAME_TABLE, values,
                DBHelper.NAME_COLLUMN_LEVEL + "= ?", new String[]{(Game.level) + ""}) == 0){
            database.insert(DBHelper.NAME_TABLE, null, values);
        }

    }

    public void updateVariable(){
        Game.levels.get(Game.level).FinishedTasks.add(Game.levels.get(Game.level).FreeTasks.get(Game.CurInd));
        Game.levels.get(Game.level).FreeTasks.remove(Game.CurInd);
        Game.PointsForAllGame++;
        Game.levels.get(Game.level).PointForLevel++;
       // textViewAllPoints.setText("" + Game.PointsForAllGame);

        fillDB();

        if (Game.levels.get(Game.level).FreeTasks.size() == 0) {
            Game.setlevel(Game.level + 1);
        }
       // Toast.makeText(this, Game.level + "", Toast.LENGTH_SHORT).show();
    }

    public void btnHelpListener(View view) {
        String curHelp = ((TextView)findViewById(R.id.help_text)).getText().toString();
        if (curHelp.length() != answerTasks.length()){
            ((TextView)findViewById(R.id.help_text)).setText(curHelp + answerTasks.charAt(curHelp.length()));
        }
    }

    public void createToolBar(){
        ((TextView)findViewById(R.id.help_text)).setText("");

        textViewAllPoints = (TextView) findViewById(R.id.tvPoints);
        textViewAllPoints.setText("" + Game.PointsForAllGame / 2);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Конец Игры");
        adb.setMessage("Начать Заново?");
        adb.setPositiveButton("Канешна)", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Game.createDB(getApplicationContext()).delete(DBHelper.NAME_TABLE, null, null);
                Game.fillVariablesFromDataBase(Game.createDB(getApplicationContext()));
                createScreen();
            }
        });

        adb.setNegativeButton("Не сегодня..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });

        return adb.create();
    }
}
