package dmproject.moviebuff.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import dmproject.moviebuff.DBHelper;
import dmproject.moviebuff.DBPlayers;
import dmproject.moviebuff.Game;
import dmproject.moviebuff.R;

public class SettingActivity extends AppCompatActivity {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        context = this;

        ((Switch)findViewById(R.id.swch_music)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Game.GamePlayer.start();
                } else
                    Game.GamePlayer.pause();
            }
        });
        ((Switch)findViewById(R.id.swch_music)).setChecked(Game.GamePlayer.isPlaying());

        TextView tvPlayersResults = (TextView)findViewById(R.id.tvPlayersResults);

        DBPlayers db = new DBPlayers(this);
        SQLiteDatabase database = db.getReadableDatabase();

        Cursor cursor = database.query(DBPlayers.NAME_TABLE,
                null,
                null, null, null, null, null);

        int nameIndex = cursor.getColumnIndex(DBPlayers.NAME_COLLUMN_NAME);
        int pointsIndex = cursor.getColumnIndex(DBPlayers.NAME_COLLUMN_POINTS);

        while (cursor.moveToNext()){
            tvPlayersResults.setText(tvPlayersResults.getText().toString() + cursor.getString(nameIndex) + " : " + cursor.getInt(pointsIndex) + "\n");
        }


        cursor.close();
    }
    public void clickToStart(View v){
        Game.setGameLength(getResources().getIntArray(R.array.answers).length);
        Game.fillVariablesFromDataBase(Game.createDB(this));
        Game.name = ((EditText)findViewById(R.id.etName)).getText().toString();
        if(Game.isEnd())
            showDialog(1);
        else{

            Intent intent = new Intent(this, LevelsActivity.class);
            //Game.saveName(((EditText)findViewById(R.id.edName)).getText().toString());
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);}
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
