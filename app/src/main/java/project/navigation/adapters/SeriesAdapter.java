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
import project.donnees.extendedBo.Serie;
import project.navigation.fragments.FragmentSerie;
import project.navigation.constants.ParametersCodes;
import project.services.ServiceSeries;
import project.services.factory.ServicesFactory;


/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class SeriesAdapter extends ArrayAdapter<Serie> {

    private static final ServiceSeries svcSeries = ServicesFactory.get(ServiceSeries.class);

    private FragmentManagerActivity parentActivity;
    private int resource;
    private List<Serie> seriesList;

    public SeriesAdapter(final FragmentManagerActivity activity, final int resource, final List<Serie> seriesList) {
        super(activity, resource, seriesList);

        this.parentActivity = activity;
        this.resource = resource;
        this.seriesList = seriesList;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        View row;

        if (convertView != null) {
            row = convertView;
        } else {
            final LayoutInflater inflater = parentActivity.getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
        }

        initRow(row, seriesList.get(position));

        return row;
    }

    private void initRow(final View row, final Serie serie) {

        TextView txtSerieName = (TextView) row.findViewById(R.id.searchresult_serie_txt_name);
        txtSerieName.setText(serie.getName());

        TextView txtOrigin = (TextView) row.findViewById(R.id.searchresult_serie_txt_origin);
        txtOrigin.setText(serie.getOrigin());

        TextView txtKind = (TextView) row.findViewById(R.id.searchresult_serie_txt_kind);
        txtKind.setText(serie.getKind());

        TextView txtTitlesStat = (TextView) row.findViewById(R.id.searchresult_serie_txt_titles_stats);
        txtTitlesStat.setText("");

        TextView txtEditionsStat = (TextView) row.findViewById(R.id.searchresult_serie_txt_editions_stats);
        txtEditionsStat.setText("");

        TextView txtRuning = (TextView) row.findViewById(R.id.searchresult_serie_txt_runing);
        txtRuning.setText(serie.isEnded() ? "Terminée" : "En cours");

        addPossessionPicture(row, serie);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_SERIE, serie.getId());
                parentActivity.showFragment(FragmentSerie.class.getName());
            }
        });

    }

    private void addPossessionPicture(final View row, final Serie serie) {

        ImageView imgPossession = (ImageView) row.findViewById(R.id.searchresult_serie_img_possession);

        if (svcSeries.isSerieCompleted(serie)) {
            imgPossession.setImageResource(R.drawable.icn_possession_in);
        } else {
            imgPossession.setImageResource(R.drawable.icn_possession_not_wanted);
        }

    }
}
