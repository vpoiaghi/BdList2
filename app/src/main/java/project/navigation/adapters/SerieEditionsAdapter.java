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
import project.donnees.extendedBo.Edition;
import project.navigation.fragments.FragmentEdition;
import project.navigation.constants.ParametersCodes;
import project.utils.ImageUtils;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class SerieEditionsAdapter extends ArrayAdapter<Edition> {

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Edition> editionsList;

    public SerieEditionsAdapter(final FragmentManagerActivity activity, final int resource, final List<Edition> editionsList) {
        super(activity, resource, editionsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.editionsList = editionsList;
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

        initRow(row, editionsList.get(position));

        return row;
    }

    private void initRow(final View row, final Edition edition) {

        TextView txtName = (TextView) row.findViewById(R.id.serie_edition_txt_name);
        txtName.setText(edition.getName());

        TextView txtEditor = (TextView) row.findViewById(R.id.serie_edition_txt_editor);
        txtEditor.setText(edition.getEditorName());

        TextView txtNumber = (TextView) row.findViewById(R.id.serie_edition_txt_number);
        txtNumber.setText(getEditionNumber(edition));

        TextView txtParutionDate = (TextView) row.findViewById(R.id.serie_edition_txt_parutiondate);
        txtParutionDate.setText(edition.getParutionDate().toString());

        if (DateUtils.isAfter(edition.getParutionDate().getDate(), DateUtils.getToday())) {
            txtParutionDate.setBackgroundColor(Color.YELLOW);
        } else {
            txtParutionDate.setBackgroundColor(Color.TRANSPARENT);
        }

        ImageView imgEdition = (ImageView) row.findViewById(R.id.serie_edition_img_cover);
        ImageUtils.loadEditionFrontCoverImage(imgEdition, edition);

        ImageView imgPossession = (ImageView) row.findViewById(R.id.serie_edition_img_possession);
        ImageUtils.loadPossessionImage(imgPossession, edition);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_EDITION, edition.getId());
                parentActivity.showFragment(FragmentEdition.class.getName());
            }
        });

    }

    private String getEditionNumber(final Edition edition) {

        final String editionNumber = edition.getEditionNumber();
        final String specialEdition = edition.getSpecialEdition();
        String s = "";

        if (specialEdition != null && editionNumber != null) {
            s = edition.getEditionNumber() + " (" + specialEdition  + ")";

        } else if (specialEdition != null) {
            s = "(" + specialEdition + ")";

        } else if (editionNumber != null) {
            s = edition.getEditionNumber();
        }

        return s;
    }

}
