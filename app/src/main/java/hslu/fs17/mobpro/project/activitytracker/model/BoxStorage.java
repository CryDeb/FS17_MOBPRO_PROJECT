package hslu.fs17.mobpro.project.activitytracker.model;

import android.app.Application;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.BoxStoreBuilder;

public class BoxStorage {
    private static BoxStorage ourInstance;
    private BoxStore boxStore;


    public static BoxStore getInstance(Application app) {
        if (ourInstance == null) {
            ourInstance = new BoxStorage(app);
        }
        return ourInstance.boxStore;
    }

    private BoxStorage(Application app) {
        this.boxStore = MyObjectBox.builder().androidContext(app).build();
    }
}
