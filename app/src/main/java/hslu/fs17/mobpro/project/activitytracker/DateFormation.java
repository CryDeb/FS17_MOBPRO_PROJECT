package hslu.fs17.mobpro.project.activitytracker;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormation {
    private SimpleDateFormat sDateFormat;

    public DateFormation(Context applicationContext) {
        sDateFormat = (SimpleDateFormat) android.text.format.DateFormat.getDateFormat(applicationContext);
    }

    public String getString(Calendar calendar) {
        return sDateFormat.format(calendar.getTime());
    }

    public Calendar getCalendar(String string) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sDateFormat.parse(string));
        return calendar;
    }
}
