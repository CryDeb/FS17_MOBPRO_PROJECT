package hslu.fs17.mobpro.project.activitytracker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import hslu.fs17.mobpro.project.activitytracker.model.Author;
import hslu.fs17.mobpro.project.activitytracker.model.BoxStorage;
import hslu.fs17.mobpro.project.activitytracker.model.FunActivity;
import io.objectbox.Box;

public class AddFunActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FunActivity funActivity;
    private DateFormation dateFormator;
    Box<FunActivity> myBox;

    public AddFunActivity() {
        super();
        myBox = BoxStorage.getInstance(getApplication()).boxFor(FunActivity.class);
        dateFormator = new DateFormation((SimpleDateFormat) android.text.format.DateFormat.getDateFormat(this));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_);
        Bundle i = this.getIntent().getExtras();
        try {
            if(i != null) {
                Long edit = i.getLong("edit");
                System.out.println(i.getLong("edit"));
                this.funActivity = this.myBox.get(edit);
                System.out.println(this.funActivity.getTitle());
                EditText title = (EditText) findViewById(R.id.titleinput_txt);
                EditText description = (EditText) findViewById(R.id.descriptioninput_txt);
                TextView date = (TextView) findViewById(R.id.dateinput_txt);

                title.setText(this.funActivity.getTitle(),TextView.BufferType.EDITABLE);
                description.setText(this.funActivity.getActivityDescription(),TextView.BufferType.EDITABLE);
                date.setText(this.funActivity.getFinalDate());
            }else {
                funActivity = null;
            }

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("ERROR SHeisse " + e.getMessage());
        }
    }

    public void saveNewActivity(View v) {
        EditText title = (EditText) findViewById(R.id.titleinput_txt);
        EditText description = (EditText) findViewById(R.id.descriptioninput_txt);
        TextView date = (TextView) findViewById(R.id.dateinput_txt);
        if (this.funActivity == null) {
            this.funActivity = new FunActivity(new Author("wicki", "dane"), title.getText().toString(),
                    description.getText().toString(), date.getText().toString());
        } else {
            this.funActivity.setActivityDescription(description.getText().toString());
            this.funActivity.setFinalDate(date.getText().toString());
            this.funActivity.setTitle(title.getText().toString());
        }
        System.out.println(funActivity.getTitle());
        myBox.put(funActivity);
        onBackPressed();
    }
    public void asdf(View v) {
        TextView tv = (TextView) findViewById(R.id.dateinput_txt);
        Calendar calendar;
        try {
            calendar = dateFormator.getCalendar(tv.getText().toString());
        } catch (ParseException e) {
            calendar = Calendar.getInstance();
            System.out.println(e.getMessage());
            System.out.println("SHIT");
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this, AddFunActivity.this, year, month, dayOfMonth);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        TextView tv = (TextView) findViewById(R.id.dateinput_txt);
        tv.setText(dateFormator.getString(calendar));
    }

}
