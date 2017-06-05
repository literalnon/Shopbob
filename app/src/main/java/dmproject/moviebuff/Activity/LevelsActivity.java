package dmproject.moviebuff.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsStatus;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import dmproject.moviebuff.Adapters.AdapterForLevels;
import dmproject.moviebuff.DBHelper;
import dmproject.moviebuff.Game;
import dmproject.moviebuff.R;

public class LevelsActivity extends AppCompatActivity{

    GridView gridView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        context = this;

        gridView = (GridView) findViewById(R.id.gvLevels);

        gridView.setBackgroundColor(getResources().getColor(R.color.colorBackStart));
       //Game.fillVariablesFromDataBase();

        AdapterForLevels adapter = new AdapterForLevels(this, Game.levels.levels);

        gridView.setAdapter(adapter);

        gridView.setNumColumns(1);

        gridView.setHorizontalSpacing(20);

        gridView.setVerticalSpacing(10);

        TextView textViewAllPoints = (TextView) findViewById(R.id.tvPoints);
        textViewAllPoints.setText("" + Game.PointsForAllGame / 2);
    }

    public void listenerBtnLvl(View v){
        Game.printDB(Game.createDB(this));
        Intent intent = new Intent(this, TaskActivity.class);
        Game.setlevel((int) v.getTag());
        this.startActivity(intent);
        this.finish();
    }

    @Override
    public void onBackPressed(){
        //Intent intent = new Intent(this, startActivity.class);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        //Game.printDB(Game.createDB(this));
        //startActivity(intent);
        this.finish();
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
                //createScreen();
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

    public View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Game.printDB(Game.createDB(context));
            Intent intent = new Intent(context, TaskActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            if(Game.isEnd()){
                showDialog(1);
                Toast.makeText(context, "no more pictures", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(context, "load", Toast.LENGTH_SHORT).show();
                Game.setlevel((int) v.getTag());
                context.startActivity(intent);
            }
        }
    };
}
