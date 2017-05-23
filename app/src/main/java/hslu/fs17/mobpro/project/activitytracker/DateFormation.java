package hslu.fs17.mobpro.project.activitytracker;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormation {
    private SimpleDateFormat sDateFormat;

    public DateFormation(Context applicationContext) {
        this.sDateFormat = (SimpleDateFormat) android.text.format.DateFormat.getDateFormat(applicationContext);
    }

    public String getString(Calendar calendar) {
        return sDateFormat.format(calendar.getTime());
    }

    public Calendar getCalendar(String string) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sDateFormat.parse(string));
        return calendar;
    }

    public Calendar removeHour(Calendar calendar) throws ParseException {
        final Calendar cal = Calendar.getInstance();
        final String parsed = sDateFormat.format(cal.getTime());
        cal.setTime(sDateFormat.parse(parsed));
        return cal;
    }
}
