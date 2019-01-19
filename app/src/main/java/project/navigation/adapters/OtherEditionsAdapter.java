package project.navigation.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import framework.activity.FragmentManagerActivity;
import framework.tools.DateUtils;
import project.donnees.extendedBo.Edition;
import project.donnees.bo.botypes.SqlDate;
import project.navigation.fragments.FragmentEdition;
import project.navigation.constants.ParametersCodes;
import project.utils.ImageUtils;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class OtherEditionsAdapter extends ArrayAdapter<Edition> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<Edition> editionsList;
    private final List<OnRowClickListener> onRowClickListenerList;

    public OtherEditionsAdapter(final FragmentManagerActivity activity, final int resource, final List<Edition> editionsList) {
        super(activity, resource, editionsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.editionsList = editionsList;
        this.onRowClickListenerList = new ArrayList<>();
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

        final TextView txtEditionName = (TextView) row.findViewById(R.id.searchresult_edition_txt_editionname);
        txtEditionName.setText(edition.getName());

        final TextView txtEditionNumber = (TextView) row.findViewById(R.id.searchresult_edition_txt_editionnumber);
        final String editionNumber = edition.getEditionNumber();
        final String specialEdition = edition.getSpecialEdition();
        String s = "Numéro de l'édition : ";
        if (specialEdition != null && editionNumber != null) {
            s += edition.getEditionNumber() + " (" + specialEdition  + ")";
        } else if (specialEdition != null) {
            s += "(" + specialEdition + ")";
        } else if (editionNumber != null) {
            s += edition.getEditionNumber();
        }
        txtEditionNumber.setText(s);

        final TextView txtEditor = (TextView) row.findViewById(R.id.searchresult_edition_txt_editor);
        txtEditor.setText(edition.getEditorName());

        final TextView txtParutionDate = (TextView) row.findViewById(R.id.searchresult_edition_txt_parutiondate);
        final SqlDate parutionDate = edition.getParutionDate();
        if (parutionDate == null) {
            s = "Parution : inconnue";
        } else {
            s = "Parution : " + parutionDate.toString();
        }
        txtParutionDate.setText(s);

        if (DateUtils.isAfter(parutionDate.getDate(), DateUtils.getToday())) {
            txtParutionDate.setBackgroundColor(Color.YELLOW);
        } else {
            txtParutionDate.setBackgroundColor(Color.TRANSPARENT);
        }

        final ImageView imgEdition = (ImageView) row.findViewById(R.id.searchresult_edition_img_item);
        ImageUtils.loadEditionFrontCoverImage(imgEdition, edition);

        final ImageView imgPossession = (ImageView) row.findViewById(R.id.searchresult_edition_img_possession);
        ImageUtils.loadPossessionImage(imgPossession, edition);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_EDITION, edition.getId());
                parentActivity.showFragment(FragmentEdition.class.getName());

                for (OnRowClickListener listener : onRowClickListenerList) {
                    listener.onRowClick(row, edition);
                }
            }
        });
    }

    public void setOnRowClickListener(final OnRowClickListener onRowClickListener) {
        this.onRowClickListenerList.add(onRowClickListener);
    }

    public interface OnRowClickListener {

        void onRowClick(final View v, final Edition edition);

    }
}
