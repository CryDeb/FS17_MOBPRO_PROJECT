package hslu.fs17.mobpro.project.activitytracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateFormation {
    private SimpleDateFormat sDateFormat;

    public DateFormation(final SimpleDateFormat sdf) {
        this.sDateFormat = sdf;
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
