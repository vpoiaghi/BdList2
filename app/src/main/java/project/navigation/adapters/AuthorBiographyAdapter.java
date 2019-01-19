package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class AuthorBiographyAdapter extends ArrayAdapter<String> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<String> biographyList;

    public AuthorBiographyAdapter(final FragmentManagerActivity activity, final int resource, final List<String> biographyList) {
        super(activity, resource, biographyList);

        this.parentActivity = activity;
        this.resource = resource;
        this.biographyList = biographyList;
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

        initRow(row, biographyList.get(position));

        return row;
    }

    private void initRow(final View row, final String biography) {

        TextView txtInfo = (TextView) row.findViewById(R.id.itm_string_txt);
        txtInfo.setText(biography);
    }
}
