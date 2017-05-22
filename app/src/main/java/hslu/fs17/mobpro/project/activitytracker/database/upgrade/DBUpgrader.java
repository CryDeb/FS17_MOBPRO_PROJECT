package hslu.fs17.mobpro.project.activitytracker.database.upgrade;

import android.database.sqlite.SQLiteDatabase;

public interface DBUpgrader {
    void upgradeDB(final SQLiteDatabase db, final int oldVersion);
}
