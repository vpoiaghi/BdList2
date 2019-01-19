package project.donnees.bo.botypes;

import android.support.annotation.NonNull;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created by VINCENT on 02/03/2017.
 *
 */
public class SqlDate implements Comparable<SqlDate> {

    private String value;
    private boolean valueChanged;
    private Date valueDate;

    public SqlDate() {
        this(new Date());
    }

    public SqlDate(final Date value) {
        setValue(value);
    }

    public SqlDate(final Date value, final int hours, final int minutes, final int seconds) {
        setValue(value, hours, minutes, seconds);
    }

    public SqlDate(final String value) {
        setValue(value);
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(final String value) {

        this.value = value;
        this.valueDate = null;
        this.valueChanged = true;
    }

    public void setValue(final Date value) {

        final String newValue;

        if (value == null) {
            newValue = null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRENCH);
            newValue = df.format(value);
        }

        setValue(newValue);
    }

    public void setValue(final Date value, final int hours, final int minutes, final int seconds) {

        final String newValue;

        if (value == null) {
            newValue = null;
        } else {
            DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.FRENCH);
            newValue = df.format(value) + "_"
                    + String.format(Locale.FRANCE, "%02d", hours)
                    + String.format(Locale.FRANCE, "%02d", minutes)
                    + String.format(Locale.FRANCE, "%02d", seconds);
        }

        setValue(newValue);
    }

    public Date getDate() {

        if (valueChanged) {

            this.valueDate = null;

            if (!((value == null) || (value.length() == 0))) {

                int year = Integer.parseInt(value.substring(0, 4));
                int month = Integer.parseInt(value.substring(4, 6));
                int day = Integer.parseInt(value.substring(6, 8));
                int hour = Integer.parseInt(value.substring(9, 11));
                int minute = Integer.parseInt(value.substring(11, 13));
                int second = Integer.parseInt(value.substring(13, 15));

                Calendar c = new GregorianCalendar(Locale.FRANCE);
                c.set(year, month-1, day, hour, minute, second);
                this.valueDate = c.getTime();

            }

            this.valueChanged = false;

        }

        return this.valueDate;
    }

    public Long getTime() {

        Long time = null;
        Date date = getDate();

        if (date != null) {
            time = date.getTime();
        }

        return time;
    }

    public String toString() {
        return toString(false);
    }

    public String toString(final boolean withHours) {

        final String value = getValue();
        String result = value;

        if (!((value == null) || (value.length() == 0))) {

            int year = Integer.parseInt(value.substring(0, 4));
            int month = Integer.parseInt(value.substring(4, 6));
            int day = Integer.parseInt(value.substring(6, 8));

            result = String.format(Locale.FRANCE, "%02d", day) + "/" + String.format(Locale.FRANCE, "%02d", month) + "/" + String.format(Locale.FRANCE, "%04d", year);

            if (withHours) {

                int hour = Integer.parseInt(value.substring(9, 11));
                int minute = Integer.parseInt(value.substring(11, 13));
                int second = Integer.parseInt(value.substring(13, 15));

                result += " " + String.format(Locale.FRANCE, "%02d", hour) + ":" + String.format(Locale.FRANCE, "%02d", minute) + ":" + String.format(Locale.FRANCE, "%02d", second);
            }

        }

        return result;

    }

    @Override
    public int compareTo(@NonNull final SqlDate another) {

        int result = 0;

        try {
            result = getValue().compareTo(another.getValue());
        }
        catch(NullPointerException e) {
        }

        return result;
    }
}
