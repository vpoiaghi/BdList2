package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.extendedBo.Festival;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentFestival;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class FestivalsAdapter extends ArrayAdapter<Festival> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<Festival> festivalsList;
    private final Long idEditor;

    public FestivalsAdapter(final FragmentManagerActivity activity, final int resource, final List<Festival> festivalsList) {
        this(activity, resource, festivalsList, null);
    }

    public FestivalsAdapter(final FragmentManagerActivity activity, final int resource, final List<Festival> festivalsList, final Long idEditor) {
        super(activity, resource, festivalsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.festivalsList = festivalsList;
        this.idEditor = idEditor;
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

        initRow(row, festivalsList.get(position));

        return row;
    }

    private void initRow(final View row, final Festival festival) {

        final TextView txtFestivalName = (TextView) row.findViewById(R.id.itm_festival_name);
        txtFestivalName.setText(festival.getName());

        final String datesTxt = "Du " + festival.getBeginDate().toString() + " au " + festival.getEndDate().toString();
        final TextView txtFestivalDates = (TextView) row.findViewById(R.id.itm_festival_dates);
        txtFestivalDates.setText(datesTxt);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (idEditor != null) {
                    parentActivity.addParameterOut(ParametersCodes.ID_EDITOR, idEditor);
                }

                parentActivity.addParameterOut(ParametersCodes.ID_FESTIVAL, festival.getId());
                parentActivity.showFragment(FragmentFestival.class.getName());
            }
        });
    }

}