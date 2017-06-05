package dmproject.moviebuff;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.Toast;

/**
 * Created by 1 on 29.05.2016.
 */
public class DBHelper extends SQLiteOpenHelper implements BaseColumns {

    public static final int DB_VERSION = 1;
    public static final String NAME_DB = "movieDB.db";
    public static final String NAME_TABLE = "data";


    public static final String NAME_ID = "_id";
    public static final String NAME_COLLUMN_LEVEL = "levels";
    public static final String NAME_COLLUMN_FINISHED = "finished";




    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + NAME_TABLE + " (" + NAME_ID
            + " integer primary key, " + NAME_COLLUMN_LEVEL
            + " integer, " + NAME_COLLUMN_FINISHED + " integer)";

    public DBHelper(Context context){
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
