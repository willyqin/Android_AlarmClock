package Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Han on 2016/5/14.
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context mcontext;
    public static final String CREATE_TABLE_SQL = "create table Alarm_Table ("
            + "id integer primary key autoincrement,"
            + "alarm_active integer,"
            + "alarm_time text,"
            + "alarm_days blob,"
            + "alarm_text text,"
            + "alarm_tone text)";
    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
