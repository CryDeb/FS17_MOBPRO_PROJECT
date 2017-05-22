package hslu.fs17.mobpro.project.activitytracker.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hslu.fs17.mobpro.project.activitytracker.database.upgrade.DBUpgrader;


public class DBHelper extends SQLiteOpenHelper {
    final private String dbName;
    final private DBUpgrader dbUpgrader;
    public DBHelper(final Context context, final String dbName, final int dbVersion, final DBUpgrader dbUpgrader) {
        super(context, dbName, null, dbVersion);
        this.dbName = dbName;
        this.dbUpgrader = dbUpgrader;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL ("CREATE TABLE " + this.dbName + " " +
                "(_id INTEGER PRIMARY KEY ASC, " +
                "date DATE NOT NULL, " +
                "object TEXT NOT NULL");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.dbUpgrader.upgradeDB(db, oldVersion);
    }
}
