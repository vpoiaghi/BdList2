package project.donnees.wrapper.insigning;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by VINCENT on 25/12/2018.
 *
 */
public class PeriodsTable {

    private final Date day;
    private final Hashtable<String, PeriodsRow> periodsRows;

    private int firstHour = 24;
    private int lastHour = 0;
    private boolean firstLastHoursIsUpToDate = false;

    public PeriodsTable(@NonNull final Date day) {
        this.day = day;
        this.periodsRows = new Hashtable<>();
    }

    public Date getDay() {
        return day;
    }

    public int getFirstHour() {
        refreshFirstLastHours();
        return firstHour;
    }

    public int getLastHour() {
        refreshFirstLastHours();
        return lastHour;
    }

    public List<PeriodsRow> getPeriodsRows() {

        List<PeriodsRow> list = new ArrayList<>();
        list.addAll(periodsRows.values());

        return Collections.unmodifiableList(list);
    }

    public void addPeriod(@NonNull final String rowName, @NonNull final IPeriod period) {

        firstLastHoursIsUpToDate = false;

        PeriodsRow periodsRow = periodsRows.get(rowName);

        if (periodsRow == null) {
            periodsRow = new PeriodsRow(rowName);
            periodsRows.put(rowName, periodsRow);
        }

        periodsRow.add(period);
    }

    private void refreshFirstLastHours() {

        if (! firstLastHoursIsUpToDate) {

            for (PeriodsRow pRow : periodsRows.values()) {
                changeFirstHour(pRow);
                changeLastHour(pRow);
            }

            firstLastHoursIsUpToDate = true;
        }
    }

    private void changeFirstHour(@NonNull PeriodsRow periodsRow) {

        for (IPeriod period : periodsRow.getPeriods()) {
            changeFirstHour(period);
        }
    }

    private void changeFirstHour(@NonNull IPeriod period) {

        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(period.getBeginning());
        int h = gCalendar.get(Calendar.HOUR_OF_DAY);

        if (h < firstHour) {
            firstHour = h;
        }
    }

    private void changeLastHour(@NonNull PeriodsRow periodsRow) {

        for (IPeriod period : periodsRow.getPeriods()) {
            changeLastHour(period);
        }
    }

    private void changeLastHour(@NonNull IPeriod period) {

        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(period.getEnd());
        int h = gCalendar.get(Calendar.HOUR_OF_DAY);

        if (h > lastHour) {
            lastHour = h;
        } else if ((h == lastHour) && (gCalendar.get(Calendar.MINUTE) > 0)) {
            lastHour = h + 1;
        }
    }


}
