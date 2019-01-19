package project.navigation.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.tools.DateUtils;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.wrapper.PressItem;
import project.donnees.wrapper.PressItemsList;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class PressAdapter extends ArrayAdapter<PressItemsList> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<PressItemsList> pressItemsList;

    public PressAdapter(final FragmentManagerActivity activity, final int resource, final List<PressItemsList> pressItemsList) {
        super(activity, resource, pressItemsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.pressItemsList = pressItemsList;
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

        initRow(row, pressItemsList.get(position));

        return row;
    }

    private void initRow(final View row, final PressItemsList pressItemsList) {

        final boolean isOld = DateUtils.isBefore(pressItemsList.getParutionDate().getDate(), DateUtils.getToday());

        SqlDate parutionDate = pressItemsList.getParutionDate();
        final TextView txtParutionDate = (TextView) row.findViewById(R.id.pressByDay_txt_day);

        if (parutionDate == null) {
            txtParutionDate.setText("date inconnue");
        } else {
            txtParutionDate.setText(parutionDate.toString());
        }

        PressAdapterByDay adapter = new PressAdapterByDay(this.parentActivity, R.layout.itm_press, pressItemsList.getPressItemsList());

        final GridView pressItemsListView = (GridView) row.findViewById(R.id.pressByDay_grd_items);
        pressItemsListView.setVisibility(View.VISIBLE);
        pressItemsListView.setAdapter(adapter);

        final int itemsCount = pressItemsList.getPressItemsList().size();
        final int rowCount = (itemsCount == 0) ? 1 : (int) Math.ceil((double)itemsCount / 3);
        final int rowHeight = 200;  //this is in pixels
        ViewGroup.LayoutParams layoutParams = pressItemsListView.getLayoutParams();
        layoutParams.height = convertDpToPixels(rowHeight, this.getContext()) * rowCount; //this is in pixels
        pressItemsListView.setMinimumHeight(rowHeight);
        pressItemsListView.setLayoutParams(layoutParams);

        if (isOld) {
            pressItemsListView.setBackgroundColor(Color.LTGRAY);
        } else {
            pressItemsListView.setBackgroundColor(Color.WHITE);
        }

    }

    private int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                resources.getDisplayMetrics()
        );
    }

}
