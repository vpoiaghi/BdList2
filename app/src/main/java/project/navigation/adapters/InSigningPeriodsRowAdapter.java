package project.navigation.adapters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.fragments.AbstractFragment;
import project.donnees.bo.BoEvent;
import project.donnees.extendedBo.Event;
import project.donnees.wrapper.insigning.ICell;
import project.donnees.wrapper.insigning.PeriodEditor;
import project.navigation.activities.PopupPeriodCommentsActivity;
import project.navigation.activities.PopupPossessionStatesActivity;
import project.navigation.components.CellView;
import project.navigation.constants.ConstantesEvents;
import project.navigation.constants.ConstantesInSigning;
import project.navigation.fragments.FragmentInSignings;


/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class InSigningPeriodsRowAdapter extends ArrayAdapter<CellView> {

    private final Activity parentActivity;
    private final AbstractFragment fragment;

    private final int resource;
    private final List<CellView> cellViewsList;

    public InSigningPeriodsRowAdapter(final AbstractFragment fragment, final int resource, List<CellView> cellViewsList) {
        super(fragment.getActivity(), resource, cellViewsList);

        this.parentActivity = fragment.getActivity();
        this.fragment = fragment;
        this.resource = resource;
        this.cellViewsList = cellViewsList;

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

        initRow(row, cellViewsList.get(position));

        return row;
    }

    private void initRow(final View row, final CellView cellView) {

        final ICell c = cellView.getCell();
        final int w = cellView.getWidth();

        RelativeLayout.LayoutParams rowParams = new RelativeLayout.LayoutParams(w, ConstantesInSigning.TABLE_ROW_HEIGHT);
        row.setLayoutParams(rowParams);

        RelativeLayout.LayoutParams txtParams = new RelativeLayout.LayoutParams(w-1, ConstantesInSigning.TABLE_ROW_HEIGHT - 1);
        TextView txt = (TextView) row.findViewById(R.id.itm_insigning_periods_cell_txt);
        txt.setBackgroundColor(c.getColor());
        txt.setText(c.toString());
        txt.setLayoutParams(txtParams);

        if (c instanceof PeriodEditor) {
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((FragmentInSignings) fragment).clickOnPeriod((PeriodEditor)c);
                }
            });
        }
    }

}