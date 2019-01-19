package project.navigation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import framework.tools.DateTimeUtils;
import framework.tools.DateUtils;
import framework.tools.PhoneUtils;
import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Event;
import project.donnees.extendedBo.Festival;
import project.donnees.extendedBo.InSigning;
import project.donnees.wrapper.insigning.ICell;
import project.donnees.wrapper.insigning.PeriodEditor;
import project.donnees.wrapper.insigning.PeriodsTable;
import project.donnees.wrapper.insigning.UsedPeriod;
import project.navigation.activities.PopupPeriodCommentsActivity;
import project.navigation.activities.PopupPossessionStatesActivity;
import project.navigation.adapters.InSigningPeriodsTableAdapter;
import project.navigation.sliding_menu.SlidingMenuItem;
import project.navigation.constants.ConstantesInSigning;
import project.navigation.constants.ParametersCodes;
import project.services.ServiceEditors;
import project.services.ServiceFestivals;
import project.services.ServiceInSignings;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 23/12/2018.
 *
 */
public class FragmentInSignings extends AbstractFragment {

    Parcelable gridViewState;

    private static final ServiceEditors svcEditors = ServicesFactory.get(ServiceEditors.class);
    private static final ServiceFestivals svcFestivals = ServicesFactory.get(ServiceFestivals.class);
    private static final ServiceInSignings svcInSignings = ServicesFactory.get(ServiceInSignings.class);

    private Festival festival = null;
    private Editor editor = null;
    private Date day = null;

    private int maxPeriodsWidth;
    private int firstColWidth;
    private int oneHourWidth;

    public FragmentInSignings() {
        super(R.layout.frg_insignings);
    }

    @Override
    protected void loadFragment() {

        maxPeriodsWidth = PhoneUtils.getScreenSizeInPixel(getParentActivity()).x - ConstantesInSigning.TABLE_FIRST_COL_WIDTH;

        final Long idFestival = (Long) getParameterIn(ParametersCodes.ID_FESTIVAL);
        festival = svcFestivals.getById(idFestival);

        final Long idEditor = (Long) getParameterIn(ParametersCodes.ID_EDITOR);
        editor = (idEditor == null ? null : svcEditors.getById(idEditor));
    }

    @Override
    protected void refreshFragment() {

        Date d = day;

        if (d == null) {
            d = DateUtils.getToday();
        }

        if (!DateUtils.isInclude(d, festival.getBeginDate().getDate(), festival.getEndDate().getDate())) {
            d = festival.getBeginDate().getDate();
        }

        refreshFragment(d);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_festival;
    }

    @Override
    public String getFragmentTitle() {
        return "Festival";
    }

    @Override
    public void getSlidingMenuItems(List<SlidingMenuItem> slidingMenuItemsList) {
        slidingMenuItemsList.add(new SlidingMenuItem(R.drawable.btn_right) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFragment(FragmentEditors.class.getName());
            }
        });

        slidingMenuItemsList.add(new SlidingMenuItem(R.drawable.btn_right) {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showFragment(FragmentMenu.class.getName());
            }

        });
    }

    private PeriodsTable getEditorsPeriodsTable(List<InSigning> inSigningsList, final Date day) {

        final PeriodsTable periodsTable = new PeriodsTable(day);

        for (InSigning is : inSigningsList) {
            periodsTable.addPeriod(is.getAuthor().toString(), new PeriodEditor(is));
        }

        return periodsTable;
    }

    @Override
    public void onPause() {

        ListView gridView = (ListView) findViewById(R.id.insigning_table_content);
        gridViewState = gridView.onSaveInstanceState();

        super.onPause();
    }

    @Override
    protected void saveFragmentState(final Bundle dataToSave) {
        dataToSave.putLong("day", this.day.getTime());
    }

    @Override
    public void restoreFragmentState(final @NonNull Bundle savedData) {
        day = new Date();
        day.setTime(savedData.getLong("day"));
    }

    private void refreshFragment(final Date day) {

        TextView txtDay = (TextView) findViewById(R.id.insignings_txt_day);
        DateFormat df = new SimpleDateFormat("EEEE dd MMMM yyyy", Locale.getDefault());
        txtDay.setText(df.format(day));

        final List<InSigning> inSigningsList = svcInSignings.get(festival, editor, day);
        final PeriodsTable periodsTable = getEditorsPeriodsTable(inSigningsList, day);

        buildTableHeader(periodsTable);

        final ListView gridView = (ListView) findViewById(R.id.insigning_table_content);
        final InSigningPeriodsTableAdapter adapter = new InSigningPeriodsTableAdapter(this, R.layout.itm_insignings_periods_row, periodsTable);

        gridView.setAdapter(adapter);

        if (gridViewState != null) {
            gridView.onRestoreInstanceState(gridViewState);
        }

        if (editor == null) {
            findViewById(R.id.insignings_layout_filter).setVisibility(View.GONE);
            ((TextView) findViewById(R.id.insignings_txt_editor_filter)).setText(null);
        } else {
            findViewById(R.id.insignings_layout_filter).setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.insignings_txt_editor_filter)).setText(editor.toString());
        }

        initPrevButton(day);
        initNextButton(day);

        this.day = day;
    }

    private void initPrevButton(final Date day) {

        LinearLayout llPrev = (LinearLayout) findViewById(R.id.insigning_lnl_prev);
        ImageView imgPrev = (ImageView) findViewById(R.id.insigning_img_prev);

        if (DateUtils.isAfter(day, festival.getBeginDate().getDate())) {
            llPrev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridViewState = null;
                    refreshFragment(DateUtils.getDayBeforeADay(day, 1));
                }
            });
            imgPrev.setVisibility(View.VISIBLE);
        } else {
            llPrev.setOnClickListener(null);
            imgPrev.setVisibility(View.INVISIBLE);
        }

    }

    private void initNextButton(final Date day) {

        LinearLayout llNext = (LinearLayout) findViewById(R.id.insigning_lnl_next);
        ImageView imgNext = (ImageView) findViewById(R.id.insigning_img_next);

        if (DateUtils.isBefore(day, festival.getEndDate().getDate())) {
            llNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    gridViewState = null;
                    refreshFragment(DateUtils.getDayAfterADay(day, 1));
                }
            });
            imgNext.setVisibility(View.VISIBLE);
        } else {
            llNext.setOnClickListener(null);
            imgNext.setVisibility(View.INVISIBLE);
        }

    }

    private void buildTableHeader(final PeriodsTable periodsTable) {

        LinearLayout tableHeaderLayout = (LinearLayout) findViewById(R.id.insigning_table_header);

        final int firstHour = periodsTable.getFirstHour();
        final int lastHour = periodsTable.getLastHour();

        oneHourWidth = (int) Math.floor(maxPeriodsWidth / (lastHour - firstHour + 1));
        firstColWidth = ConstantesInSigning.TABLE_FIRST_COL_WIDTH + (int) Math.floor(oneHourWidth / 2);

        tableHeaderLayout.removeAllViews();
        addTableHeader(tableHeaderLayout, firstHour, lastHour);
        addTableTicks(tableHeaderLayout, firstHour, lastHour);
    }

    private void addTableHeader(final LinearLayout tableHeaderLayout, final int firstHour, final int lastHour) {

        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout headerRow = new LinearLayout(getFragmentContext());
        headerRow.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams firstColParams = new LinearLayout.LayoutParams(ConstantesInSigning.TABLE_FIRST_COL_WIDTH, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView txtEmptyCol = new TextView(getFragmentContext());
        headerRow.addView(txtEmptyCol, firstColParams);

        LinearLayout.LayoutParams hourColParams = new LinearLayout.LayoutParams(oneHourWidth, RelativeLayout.LayoutParams.WRAP_CONTENT);
        TextView txtHourCol;
        for (int i = firstHour; i <= lastHour; i++) {

            txtHourCol = new TextView(getFragmentContext());
            txtHourCol.setText(String.format(Locale.FRANCE, "%d", i));
            txtHourCol.setGravity(Gravity.CENTER_HORIZONTAL);
            txtHourCol.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            txtHourCol.setTextColor(Color.BLACK);
            headerRow.addView(txtHourCol, hourColParams);
        }

        tableHeaderLayout.addView(headerRow, rowParams);
    }

    private void addTableTicks(final LinearLayout tableHeaderLayout, final int firstHour, final int lastHour) {

        LinearLayout.LayoutParams rowTicksParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ticksRow = new LinearLayout(getFragmentContext());
        ticksRow.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams emptyTickColParams = new LinearLayout.LayoutParams(firstColWidth, 10);
        TextView txtEmptyTickCol = new TextView(getFragmentContext());
        txtEmptyTickCol.setBackgroundResource(R.drawable.style_table_cell_tick);
        ticksRow.addView(txtEmptyTickCol, emptyTickColParams);

        LinearLayout.LayoutParams tickColParams = new LinearLayout.LayoutParams(oneHourWidth, 10);
        TextView txtTickCol;
        for (int i = firstHour; i < lastHour; i++) {

            txtTickCol = new TextView(getFragmentContext());
            txtTickCol.setText("");
            txtTickCol.setBackgroundResource(R.drawable.style_table_cell_tick);
            ticksRow.addView(txtTickCol, tickColParams);

        }

        tableHeaderLayout.addView(ticksRow, rowTicksParams);

    }

    public void onPeriodClick(ICell cell) {

        if (cell instanceof UsedPeriod) {
        }

    }

    public void clickOnPeriod(PeriodEditor p) {

        String data
                = "title:=" + p.getInSigning().toString() + "|"
                + "begin:=" + DateTimeUtils.dateTimeToString(p.getInSigning().getBegin().getDate()) + "|"
                + "end:=" + DateTimeUtils.dateTimeToString(p.getInSigning().getEnd().getDate()) + "|"
                + "comments:=" + p.getComments();

        final Intent itt = new Intent(getParentActivity(), PopupPeriodCommentsActivity.class);
        Uri u = Uri.parse(data);
        itt.setData(u);
        startActivityForResult(itt, PopupPeriodCommentsActivity.POPUP_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // rien à faire
    }

}