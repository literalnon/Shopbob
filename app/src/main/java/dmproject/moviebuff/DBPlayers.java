package dmproject.moviebuff;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

public class DBPlayers extends SQLiteOpenHelper implements BaseColumns {

    public static final int DB_VERSION = 1;
    public static final String NAME_DB = "plDB.db";
    public static final String NAME_TABLE = "players";


    public static final String NAME_ID = "_id";
    public static final String NAME_COLLUMN_NAME = "player";
    public static final String NAME_COLLUMN_POINTS = "points";


    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + NAME_TABLE + " (" + NAME_ID
            + " integer primary key, " + NAME_COLLUMN_POINTS + " integer, " +
            NAME_COLLUMN_NAME + " string)";

    public DBPlayers(Context context){
        super(context, NAME_DB, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
