package hslu.fs17.mobpro.project.activitytracker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hslu.fs17.mobpro.project.activitytracker.model.BoxStorage;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity_;
import io.objectbox.Box;
import io.objectbox.query.Query;

public class FunActivityStatistic extends AppCompatActivity {

    private Box<FunActivity> myBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);

        Query<FunActivity> query = BoxStorage.getInstance(getApplication())
                .boxFor(FunActivity.class).query()
                .equal(FunActivity_.activityDone, true).build();
        System.out.println(query.find().size());
        System.out.println(BoxStorage.getInstance(getApplication())
                .boxFor(FunActivity.class).getAll().size());
        int procentage = (int)(((float)query.find().size() / (float)BoxStorage.getInstance(getApplication())
                .boxFor(FunActivity.class).getAll().size()) * 100f);
        TextView tv = (TextView) findViewById(R.id.procentage);
        tv.setText("You are to "+procentage+"% cool!");
    }
}