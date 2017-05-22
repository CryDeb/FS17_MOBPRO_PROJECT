package hslu.fs17.mobpro.project.activitytracker;

import android.app.DatePickerDialog;
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
import java.util.List;

import hslu.fs17.mobpro.project.activitytracker.model.Author;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;


public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener  {

    private final DateFormation dateFormator;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public MainActivity(){
        super();
        dateFormator = new DateFormation(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainv2);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        List<FunActivity> myDataset = new ArrayList<>();
        FunActivity fa = new FunActivity(new Author("Dane", "Wicki2"), "Title written", "Description");
        myDataset.add(fa);
        myDataset.add(fa);
        myDataset.add(fa);
        myDataset.add(fa);
        myDataset.add(fa);
        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(myDataset);
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
        TextView tv = (TextView) findViewById(R.id.DateShower);
        tv.setText(dateFormator.getString(calendar));
    }

}
