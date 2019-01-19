package project.navigation.fragments;

import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.extendedBo.Serie;
import project.donnees.extendedBo.Title;
import project.navigation.adapters.SerieEditionsAdapter;
import project.navigation.adapters.SerieGoodiesAdapter;
import project.navigation.adapters.StringAdapter;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.FactoryServices;
import project.services.ServiceEditions;
import project.services.ServiceGoodies;
import project.services.ServiceSeries;
import project.services.ServiceTitles;
import project.utils.PossessionStates;

/**
 *
 * Created by VINCENT on 27/03/2016.
 */
public class FragmentSerie extends FragmentMultiList {

    private static final ServiceEditions svcEditions = FactoryServices.get(ServiceEditions.class);
    private static final ServiceGoodies svcGoodies = FactoryServices.get(ServiceGoodies.class);
    private static final ServiceSeries svcSeries = FactoryServices.get(ServiceSeries.class);
    private static final ServiceTitles svcTitles = FactoryServices.get(ServiceTitles.class);

    private Serie serie = null;
    private List<Edition> editionsList = null;

    public FragmentSerie() {
        super();
    }

    @Override
    protected void doLoadFragment() {

        final long idSerie = (long) getParameterIn(ParametersCodes.ID_SERIE);
        serie = svcSeries.getById(idSerie);

        editionsList = svcEditions.getBySerie(serie);

    }

    @Override
    protected void doRefreshFragment() {

        addSerieTitle(serie);

        addStatus(serie);
        addKind(serie);
        addOrigin(serie);

        final boolean titlesCompleted = addTitlesStats(serie);
        addEditionsStats(editionsList);

        addCompletedPicture(titlesCompleted);

    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_edition;
    }

    @Override
    public String getFragmentTitle() {
        return "Serie";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_serie;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        listDescriptors.add(new ListDescriptor("Editions", getEditionsAdapter(), R.drawable.icn_page_edition));
        listDescriptors.add(new ListDescriptor("Para-bds", getGoodiesAdapter(), R.drawable.icn_page_goodies));
        listDescriptors.add(new ListDescriptor("Résumé", getStoryAdapter(), R.drawable.icn_story));

        return listDescriptors;
    }

    private BaseAdapter getEditionsAdapter() {

        BaseAdapter adapter = null;

        List<Edition> editionsList = svcEditions.getBySerie(serie);

        if (editionsList.size() > 0) {
            adapter = new SerieEditionsAdapter(getParentActivity(), R.layout.itm_serie_edition, editionsList);
        }

        return adapter;

    }

    private BaseAdapter getGoodiesAdapter() {

        BaseAdapter adapter = null;

        final List<Goody> goodiesList = svcGoodies.getBySerie(serie.getId());

        if (goodiesList.size() > 0) {
            adapter = new SerieGoodiesAdapter(getParentActivity(), R.layout.itm_serie_goody, goodiesList);
        }

        return adapter;

    }

    private BaseAdapter getStoryAdapter() {

        BaseAdapter adapter = null;

        if (serie.getStory() != null) {

            List<String> stringList = new ArrayList<>();
            stringList.add(serie.getStory());

            adapter = new StringAdapter(getParentActivity(), R.layout.itm_string_info, stringList);
        }

        return adapter;
    }

    private void addSerieTitle(final Serie serie) {

        TextView txtSerieName = (TextView) findViewById(R.id.serie_txt_serie_name);
        txtSerieName.setText(serie.getName());

    }

    private void addStatus(final Serie serie) {

        TextView txtStatus = (TextView) findViewById(R.id.serie_txt_ended_state);

        final String txt;

        if (serie.isEnded()) {
            txt = "Terminée";
        } else {
            txt = "En cours";
        }

        txtStatus.setText(txt);
    }

    private void addKind(final Serie serie) {
        addTextValue(R.id.serie_txt_kind, serie.getKind(), "Genre", null);
    }

    private void addOrigin(final Serie serie) {
        addTextValue(R.id.serie_txt_origin, serie.getOrigin(), "Origine", null);
    }

    private boolean addTitlesStats(final Serie serie) {

        final List<Title> titlesList = svcTitles.getBySerie(serie);

        final int inPossessionCount = svcSeries.getTitlesInPossessionCount(titlesList);

        final String s = inPossessionCount + " / " + titlesList.size();

        addTextValue(R.id.serie_txt_titles_stat, s, "Titres", null);

        return (inPossessionCount == titlesList.size());

    }

    private boolean addEditionsStats(final List<Edition> editionsList) {

        int inPossessionCount = 0;

        for (Edition edition : editionsList) {
            if (edition.getPossessionState() == PossessionStates.IN_POSSESSION) {
                inPossessionCount++;
            }
        }

        final String s = inPossessionCount + " / " + editionsList.size();

        addTextValue(R.id.serie_txt_editions_stat, s, "Editions", null);

        return (inPossessionCount == editionsList.size());
    }

    private void addCompletedPicture(final boolean titlesCompleted) {

        ImageView imgPossession = (ImageView) findViewById(R.id.serie_img_completed);

        if (titlesCompleted) {
            imgPossession.setImageResource(R.drawable.icn_possession_in);
        } else {
            imgPossession.setImageResource(R.drawable.icn_possession_not_wanted);
        }

    }

    private void addTextValue(final int idView, final String value, final String beginCaption, final String endCaption) {

        final TextView txtView = (TextView) findViewById(idView);
        final String text;
        final int visibility;

        if ((value == null) || (value.length() == 0)) {
            visibility = View.GONE;
            text = "";
        } else {
            visibility = View.VISIBLE;
            text = (beginCaption == null ? "" : beginCaption + " : ") + value + (endCaption == null ? "" : " " + endCaption);
        }

        txtView.setText(text);
        txtView.setVisibility(visibility);
    }


}
