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
public class StringAdapter extends ArrayAdapter<String> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<String> dataStringList;

    public StringAdapter(final FragmentManagerActivity activity, final int resource, final List<String> dataStringList) {
        super(activity, resource, dataStringList);

        this.parentActivity = activity;
        this.resource = resource;
        this.dataStringList = dataStringList;
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

        initRow(row, dataStringList.get(position));

        return row;
    }

    private void initRow(final View row, final String dataString) {

        TextView txtInfo = (TextView) row.findViewById(R.id.itm_string_txt);
        txtInfo.setText(dataString);
    }
}
