package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.extendedBo.Author;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentAuthor;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class SocietiesAdapter extends ArrayAdapter<Author> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<Author> societiesList;

    public SocietiesAdapter(final FragmentManagerActivity activity, final int resource, final List<Author> societiesList) {
        super(activity, resource, societiesList);

        this.parentActivity = activity;
        this.resource = resource;
        this.societiesList = societiesList;
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

        initRow(row, societiesList.get(position));

        return row;
    }

    private void initRow(final View row, final Author society) {

        String txt = society.getName();
        final TextView txtSocietyName = (TextView) row.findViewById(R.id.itm_society_txt_name);
        txtSocietyName.setText(txt);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_SOCIETY, society.getId());
                parentActivity.showFragment(FragmentAuthor.class.getName());
            }
        });
    }

}
