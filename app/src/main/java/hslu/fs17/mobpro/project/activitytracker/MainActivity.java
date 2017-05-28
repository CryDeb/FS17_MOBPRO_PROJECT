package hslu.fs17.mobpro.project.activitytracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hslu.fs17.mobpro.project.activitytracker.model.Author;
import hslu.fs17.mobpro.project.activitytracker.model.BoxStorage;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity_;
import io.objectbox.Box;
import io.objectbox.query.Query;


public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener  {

    private DateFormation dateFormator;
    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private Box<FunActivity> boxStoreFunActivity;
    private Query<FunActivity> queryFunActivity;
    private Box<Author> boxStoreAuthor;


    public MainActivity(){
        super();
        dateFormator = new DateFormation((SimpleDateFormat) android.text.format.DateFormat.getDateFormat(this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dateFormator = new DateFormation((SimpleDateFormat) android.text.format.DateFormat.getDateFormat(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.boxStoreFunActivity = BoxStorage.getInstance(getApplication()).boxFor(FunActivity.class);
        this.queryFunActivity = this.boxStoreFunActivity.query().order(FunActivity_.finalDate).build();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        FunActivity funActivity = null;
        boxStoreAuthor = BoxStorage.getInstance(getApplication()).boxFor(Author.class);
        Author a = new Author("wicki", "dane");
        funActivity = new FunActivity(a, "TestTitle", "Description", this.dateFormator.getString(Calendar.getInstance()));

        //this.boxStoreAuthor.put(a);
        //this.boxStoreFunActivity.put(funActivity);

        new HttpHandler().execute("http://localhost:123456");

        List<FunActivity> myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset, this, this.boxStoreFunActivity);

        this.updateGUI(Calendar.getInstance());

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemIndex = mRecyclerView.indexOfChild(v);
                FunActivity fa = mAdapter.getItemList().get(itemIndex);
                System.out.println("Hello");
                if (v instanceof CheckBox) {
                    CheckBox chbx = (CheckBox) v;
                    fa.setActivityDone(chbx.isChecked());
                    boxStoreFunActivity.put(fa);
                }
            }
        });

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
        this.updateGUI(calendar);

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

    public void updateGUI(Calendar equalDate) {
        this.updateGUI(this.boxStoreFunActivity.query().equal(FunActivity_.finalDate, this.dateFormator.getString(equalDate)).build());
    }
}
