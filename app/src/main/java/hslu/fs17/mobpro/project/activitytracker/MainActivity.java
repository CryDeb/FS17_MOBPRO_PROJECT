package hslu.fs17.mobpro.project.activitytracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hslu.fs17.mobpro.project.activitytracker.model.Author;
import hslu.fs17.mobpro.project.activitytracker.model.BoxStorage;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity_;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;


public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener  {

    private final DateFormation dateFormator;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Box<FunActivity> boxStoreFunActivity;
    private Query<FunActivity> queryFunActivity;


    public MainActivity(){
        super();
        dateFormator = new DateFormation(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.boxStoreFunActivity = BoxStorage.getInstance(getApplication()).boxFor(FunActivity.class);
        this.queryFunActivity = this.boxStoreFunActivity.query().order(FunActivity_.finalDate).build();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FunActivity funActivity = new FunActivity(new Author("wicki", "dane"), "TestTitle", "Description");
        this.boxStoreFunActivity.put(funActivity);

        List<FunActivity> myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);

        this.updateGUI(Calendar.getInstance().getTime());

        mRecyclerView.setAdapter(mAdapter);

        TextView myTextView = (TextView) findViewById(R.id.DateShower);
        try {
            myTextView.setText(dateFormator.getString(Calendar.getInstance()));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void showDatePickerDialog(View view) {
        TextView tv = (TextView) findViewById(R.id.DateShower);
        Calendar calendar;
        try {
            calendar = dateFormator.getCalendar((String) tv.getText());
        } catch (ParseException e) {
            calendar = Calendar.getInstance();
            System.out.println(e.getMessage());
            System.out.println("SHIT");
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, MainActivity.this, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);


        this.updateGUI(calendar.getTime());

        TextView tv = (TextView) findViewById(R.id.DateShower);
        tv.setText(dateFormator.getString(calendar));
    }

    public void startCreateActivity(View view) {
        Intent startIntentCreateActivity = new Intent(this, AddFunActivity.class);
        this.startActivity(startIntentCreateActivity);
    }

    public void startStatisticActivity(View view) {
        Intent startIntentStatisticActivity = new Intent(this, FunActivityStatistic.class);
        this.startActivity(startIntentStatisticActivity);
    }

    public void updateGUI(Query<FunActivity> pQueryFunActivity){
        List<FunActivity> listFunActivity = pQueryFunActivity.find();
        this.mAdapter.updateFunActivityList(listFunActivity);
    }

    public void updateGUI(Date equalDate) {
        this.updateGUI(this.boxStoreFunActivity.query().order(FunActivity_.finalDate).build());
    }
}
