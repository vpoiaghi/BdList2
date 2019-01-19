package project.navigation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import project.donnees.extendedBo.Title;
import project.utils.ImageUtils;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class TitlesAdapter extends ArrayAdapter<Title> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Title> titlesList;

    public TitlesAdapter(final FragmentManagerActivity activity, final int resource, final List<Title> titlesList) {
        super(activity, resource, titlesList);

        this.parentActivity = activity;
        this.resource = resource;
        this.titlesList = titlesList;
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

        initRow(row, titlesList.get(position));

        return row;
    }

    private void initRow(final View row, final Title title) {

        String txt = title.getOrderNumber() + " - " + title.getName();

        final TextView textName = (TextView) row.findViewById(R.id.title_txt_name);
        textName.setText(txt);

        final ImageView imgPossession = (ImageView) row.findViewById(R.id.title_img_possession);
        ImageUtils.loadPossessionImage(imgPossession, title.getPossessionState());
    }

}
