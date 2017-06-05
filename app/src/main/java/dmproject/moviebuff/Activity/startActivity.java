package dmproject.moviebuff.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import dmproject.moviebuff.DBHelper;
import dmproject.moviebuff.Game;
import dmproject.moviebuff.R;

public class startActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        context = this;

        Game.GamePlayer = MediaPlayer.create(this, R.raw.nighttime_stroll);
        Game.GamePlayer.setLooping(true);
        Game.GamePlayer.start();

    }

    public void clickToStart(View v){
        Game.setGameLength(getResources().getIntArray(R.array.answers).length);
        Game.fillVariablesFromDataBase(Game.createDB(this));
        if(Game.isEnd())
            showDialog(1);
        else{

        Intent intent = new Intent(this, LevelsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);}

    }

    public void clickToSetting(View view) {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed(){

        Game.GamePlayer.release();
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
                Game.setGameLength(getResources().getIntArray(R.array.answers).length);
                Intent intent = new Intent(context, LevelsActivity.class);
                //Game.saveName(((EditText)findViewById(R.id.edName)).getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Game.fillVariablesFromDataBase(Game.createDB(context));
                startActivity(intent);
                //createScreen();
            }
        });

        adb.setNegativeButton("Не сегодня..", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //onBackPressed();
            }
        });

        return adb.create();
    }
}
