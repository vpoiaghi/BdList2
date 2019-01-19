package project.navigation.adapters;

import android.app.Activity;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.fragments.AbstractFragment;
import framework.tools.PhoneUtils;
import project.donnees.wrapper.insigning.Cell;
import project.donnees.wrapper.insigning.IPeriod;
import project.donnees.wrapper.insigning.PeriodsRow;
import project.donnees.wrapper.insigning.Period;
import project.donnees.wrapper.insigning.PeriodsTable;
import framework.tools.DateTimeUtils;
import project.navigation.components.HorizontalListView;
import project.navigation.components.CellView;
import project.navigation.constants.ConstantesInSigning;


/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class InSigningPeriodsTableAdapter extends ArrayAdapter<PeriodsRow> {

    private final Activity parentActivity;
    private final AbstractFragment fragment;

    private final int resource;
    private final PeriodsTable periodsTable;

    private final int firstHour;
    private final int lastHour;

    private final int maxPeriodsWidth;
    private final int oneHourWidth;
    private final int firstColWidth;
    private int periodsWidth;

    public InSigningPeriodsTableAdapter(final AbstractFragment fragment, final int resource, PeriodsTable periodsTable) {
        super(fragment.getActivity(), resource, periodsTable.getPeriodsRows());

        this.parentActivity = fragment.getActivity();
        this.fragment = fragment;
        this.resource = resource;
        this.periodsTable = periodsTable;
        this.firstHour = periodsTable.getFirstHour();
        this.lastHour = periodsTable.getLastHour();

        maxPeriodsWidth =  PhoneUtils.getScreenSizeInPixel(this.parentActivity).x - ConstantesInSigning.TABLE_FIRST_COL_WIDTH;
        oneHourWidth = (int) Math.floor(maxPeriodsWidth / (lastHour - firstHour +  1));
        firstColWidth = ConstantesInSigning.TABLE_FIRST_COL_WIDTH + (int) Math.floor(oneHourWidth / 2);


    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        final View row;

        if (convertView != null) {
            row = convertView;
        } else {
            final LayoutInflater inflater = parentActivity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
        }

        initRow(row, periodsTable.getPeriodsRows().get(position));

        return row;
    }

    /*
    private void initRow(final View row, final PeriodsRow periodsRow) {

        final LinearLayout layout = (LinearLayout) row.findViewById(R.id.itm_insigning_periods_row);
        layout.removeAllViews();

        addFirstCol(layout, periodsRow);
        addPeriods(layout, periodsRow);
    }
    */

    private void initRow(final View row, final PeriodsRow periodsRow) {

        List<IPeriod> periods = getPeriods(periodsRow);
        List<CellView> cellViewsList = new ArrayList<>(periods.size());

        int w;
        periodsWidth = 0;

        cellViewsList.add(new CellView(new Cell(periodsRow.getName()), firstColWidth));

        for (IPeriod p : periods) {

            w = getPeriodWidth(p);
            periodsWidth += w;

            cellViewsList.add(new CellView(p, w));
        }

        InSigningPeriodsRowAdapter adapter = new InSigningPeriodsRowAdapter(this.fragment, R.layout.itm_insignings_periods_cell, cellViewsList);
        ((HorizontalListView) row).setAdapter(adapter);

    }

    private void addFirstCol(final LinearLayout row, final PeriodsRow periodsRow) {

        final LinearLayout.LayoutParams firstColParams = new LinearLayout.LayoutParams(firstColWidth, ConstantesInSigning.TABLE_ROW_HEIGHT);
        final TextView firstCol = new TextView(parentActivity);

        firstCol.setBackgroundResource(R.drawable.style_table_cell_tick);
        firstCol.setText(periodsRow.getName());
        firstCol.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

        row.addView(firstCol, firstColParams);

    }

    private void addPeriods(final LinearLayout row, final PeriodsRow periodsRow) {

        List<IPeriod> periods = getPeriods(periodsRow);
        periodsWidth = 0;

        for (IPeriod p : periods) {
            addPeriodCell(row, p);
        }

    }

    private List<IPeriod> getPeriods(final PeriodsRow periodsRow) {

        List<IPeriod> emptyPeriods = getEmptyPeriods();
        List<IPeriod> usedPeriods = periodsRow.getPeriods();
        List<IPeriod> periods = new ArrayList<>();

        if ((usedPeriods == null) || (usedPeriods.size() == 0)) {
            periods = emptyPeriods;

        } else {

            int i = 0;
            Date is, ie, js, je;

            while (i < emptyPeriods.size()) {

                int j = 0;
                is = emptyPeriods.get(i).getBeginning();
                ie = emptyPeriods.get(i).getEnd();

                while (j < usedPeriods.size()) {

                    js = usedPeriods.get(j).getBeginning();
                    je = usedPeriods.get(j).getEnd();

                    if ( ! (DateTimeUtils.ste(ie, js) || DateTimeUtils.gte(is, je))) {
                        // Présence de chevauchement ou inclusion

                        if (DateTimeUtils.st(is, js) && DateTimeUtils.gt(ie, js) && DateTimeUtils.ste(ie, je)) {
                            // chavauchement de i et j par la gauche
                            // j     |---------|
                            // i1  |-----|
                            // i2  |-----------|
                            emptyPeriods.get(i).setEnd(js);
                            ie = js;
                        } else if (DateTimeUtils.gte(is, js) && DateTimeUtils.st(is, je) && DateTimeUtils.gt(ie, je)) {
                            // chavauchement de i et j par la droite
                            // j     |---------|
                            // i1          |-----|
                            // i2    |-----------|
                            emptyPeriods.get(i).setBeginning(je);
                            is = je;
                        } else if (DateTimeUtils.gte(is, js) && DateTimeUtils.ste(ie, je)) {
                            // inclusion de i dans j
                            // j     |---------|
                            // i1      |-----|
                            // i2    |-----|
                            // i3        |-----|
                            // i4    |---------|
                            emptyPeriods.get(i).setEnd(is);
                            ie = is;
                        } else if (DateTimeUtils.st(is, js) && DateTimeUtils.gt(ie, je)) {
                            // inclusion de j dans i
                            // j     |---------|
                            // i1  |-------------|
                            emptyPeriods.get(i).setEnd(js);
                            ie = js;
                            emptyPeriods.add(i + 1, new Period(je, ie));
                        }
                    }

                    j++;
                }

                if (DateTimeUtils.eq(ie, is)) {
                    emptyPeriods.remove(i);
                } else {
                    i++;
                }
            }

            periods.addAll(emptyPeriods);
            periods.addAll(usedPeriods);

            Collections.sort(periods, new Comparator<IPeriod>() {
                @Override
                public int compare(IPeriod period1, IPeriod period2) {
                    return period1.getBeginning().compareTo(period2.getBeginning());
                }
            });

        }

        return periods;
    }

    private List<IPeriod> getEmptyPeriods() {

        List<IPeriod> periods = new ArrayList<>();

        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(periodsTable.getDay());
        final int y = gCalendar.get(Calendar.YEAR);
        final int M = gCalendar.get(Calendar.MONTH);
        final int d = gCalendar.get(Calendar.DAY_OF_MONTH);

        for (int i = firstHour; i < lastHour; i++) {
            periods.add(new Period(
                    (new GregorianCalendar(y, M, d, i, 0, 0)).getTime(),
                    (new GregorianCalendar(y, M, d, i + 1, 0, 0)).getTime()));
        }

        return periods;
    }

    private void addPeriodCell(final LinearLayout row, final IPeriod period) {

        final int w = getPeriodWidth(period);
        periodsWidth += w;

        final TextView txtView = new TextView(parentActivity);
        txtView.setBackgroundColor(period.getColor());
        txtView.setText(period.toString());
        txtView.setMaxWidth(w-1);
        txtView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        txtView.setGravity(Gravity.CENTER_HORIZONTAL);
        txtView.setTextColor(Color.BLACK);

        RelativeLayout rl = new RelativeLayout(parentActivity);
        rl.setBackgroundColor(Color.BLACK);
        rl.setPadding(0,1,1,1);
        rl.addView(txtView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        row.addView(rl, new LinearLayout.LayoutParams(w, ConstantesInSigning.TABLE_ROW_HEIGHT));
    }

    private int getPeriodWidth(IPeriod period) {

        final Date d = period.getEnd();
        final int h = DateTimeUtils.getHours(d);
        final int m = DateTimeUtils.getMinutes(d);

        int endPosition;

        if (m == 0) {
            endPosition = oneHourWidth * (h - firstHour);
        } else {
            endPosition = Math.min(
                    oneHourWidth * (h + 1 - firstHour),
                    oneHourWidth * ((h - firstHour) * 60 + m) / 60);
        }

        return endPosition - periodsWidth;
    }

}