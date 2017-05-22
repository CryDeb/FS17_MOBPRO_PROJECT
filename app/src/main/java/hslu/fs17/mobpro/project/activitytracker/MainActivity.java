package hslu.fs17.mobpro.project.activitytracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener  {
    private final DateFormation dateFormator;

    public MainActivity(){
        super();
        dateFormator = new DateFormation(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainv2);
/*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
*/

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
