package project.navigation.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.tools.DateUtils;
import project.donnees.extendedBo.Goody;
import project.donnees.bo.botypes.SqlDate;
import project.navigation.fragments.FragmentGoody;
import project.navigation.constants.ParametersCodes;
import project.utils.ImageUtils;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class SerieGoodiesAdapter extends ArrayAdapter<Goody> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Goody> goodiesList;

    public SerieGoodiesAdapter(final FragmentManagerActivity activity, final int resource, final List<Goody> goodiesList) {
        super(activity, resource, goodiesList);

        this.parentActivity = activity;
        this.resource = resource;
        this.goodiesList = goodiesList;
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

        initRow(row, goodiesList.get(position));

        return row;
    }

    private void initRow(final View row, final Goody goody) {

        final TextView txtName = (TextView) row.findViewById(R.id.serie_goody_txt_name);
        txtName.setText(goody.getName());

        final TextView txtKind = (TextView) row.findViewById(R.id.serie_goody_txt_kind);
        txtKind.setText(goody.getKindName());

        final TextView txtEditor = (TextView) row.findViewById(R.id.serie_goody_txt_editor);
        txtEditor.setText(goody.getEditorName());

        final TextView txtParutionDate = (TextView) row.findViewById(R.id.serie_goody_txt_parutiondate);
        txtParutionDate.setText(getParutionDate(goody));

        if (DateUtils.isAfter(goody.getParutionDate().getDate(), DateUtils.getToday())) {
            txtParutionDate.setBackgroundColor(Color.YELLOW);
        } else {
            txtParutionDate.setBackgroundColor(Color.TRANSPARENT);
        }

        final ImageView imgGoody = (ImageView) row.findViewById(R.id.serie_goody_img_item);
        ImageUtils.loadGoodyImage(imgGoody, goody);

        final ImageView imgPossession = (ImageView) row.findViewById(R.id.serie_goody_img_possession);
        ImageUtils.loadPossessionImage(imgPossession, goody);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_GOODY, goody.getId());
                parentActivity.showFragment(FragmentGoody.class.getName());
            }
        });

    }

    private String getParutionDate(final Goody goody) {

        final SqlDate parutionDate = goody.getParutionDate();
        final String parutionText;
        if (parutionDate == null) {
            parutionText = "Parution : inconnue";
        } else {
            parutionText = "Parution : " + parutionDate.toString();
        }

        return parutionText;
    }

}
