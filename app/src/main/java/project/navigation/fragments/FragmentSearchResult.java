package project.navigation.fragments;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bdlist.bdlist.R;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.extendedBo.Serie;
import project.navigation.adapters.EditionsAdapter;
import project.navigation.adapters.GoodiesAdapter;
import project.navigation.adapters.SeriesAdapter;
import project.navigation.constants.ParametersCodes;
import project.donnees.search.SearchParameters;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.ServiceEditions;
import project.services.ServiceGoodies;
import project.services.ServiceSeries;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 27/02/2016.
 *
 */
public class FragmentSearchResult extends FragmentMultiList {

    private SearchParameters searchParameters;

    private static final ServiceEditions svcEditions = ServicesFactory.get(ServiceEditions.class);
    private static final ServiceGoodies svcGoodies = ServicesFactory.get(ServiceGoodies.class);
    private static final ServiceSeries svcSeries = ServicesFactory.get(ServiceSeries.class);

    private List<Serie> seriesList;
    private List<Edition> editionsList;
    private List<Goody> goodiesList;

    public FragmentSearchResult() {
        super();
        searchParameters = null;
    }

    @Override
    protected void doLoadFragment() {
        searchParameters = getSearchParameters();
    }

    @Override
    protected void doRefreshFragment() {

        editionsList = svcEditions.search(searchParameters);
        goodiesList = svcGoodies.search(searchParameters);
        seriesList = GetSeriesList(editionsList, goodiesList);

        int n = seriesList.size();
        String txt = n + (n > 1 ? " séries" : " série");
        ((TextView) findViewById(R.id.header_search_result_txt_series_count)).setText(txt);

        n = editionsList.size();
        txt = n + (n > 1 ? " éditions" : " édition");
        ((TextView) findViewById(R.id.header_search_result_txt_editions_count)).setText(txt);

        n = goodiesList.size();
        txt = n + (n > 1 ? " para-bds" : " para-bd");
        ((TextView) findViewById(R.id.header_search_result_txt_goodies_count)).setText(txt);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_search;
    }

    @Override
    public String getFragmentTitle() {
        return "Recherche";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_search_result;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        listDescriptors.add(new ListDescriptor("Séries", getSeriesAdapter(), R.drawable.icn_serie));
        listDescriptors.add(new ListDescriptor("Editions", getEditionsAdapter(), R.drawable.icn_page_edition));
        listDescriptors.add(new ListDescriptor("Para-bds", getGoodiesAdapter(), R.drawable.icn_page_goodies));

        return listDescriptors;
    }

    private SeriesAdapter getSeriesAdapter() {
        return new SeriesAdapter(getParentActivity(), R.layout.itm_serie, seriesList);
    }

    private EditionsAdapter getEditionsAdapter() {
        return new EditionsAdapter(getParentActivity(), R.layout.itm_edition, editionsList);
    }

    private GoodiesAdapter getGoodiesAdapter() {
        return new GoodiesAdapter(getParentActivity(), R.layout.itm_goody, goodiesList);
    }

    private List<Serie> GetSeriesList(final List<Edition> editionsList, final List<Goody> goodiesList) {

        final List<Long> idSeriesList = new ArrayList<>();

        for (Edition edition : editionsList) {
            idSeriesList.addAll(edition.getIdSeriesList());
        }

        for (Goody goody : goodiesList) {
            idSeriesList.addAll(goody.getIdSeriesList());
        }

        final List<Serie> seriesList = svcSeries.getById(idSeriesList);
        Collections.sort(seriesList);

        return seriesList;

    }

    /**
     * Fonction nécessaire pour pouvoir être redéfinie dans les classes filles
     * @return SearchParameters
     */
    protected SearchParameters getSearchParameters() {
        return (SearchParameters) getParameterIn(ParametersCodes.SEARCH_PARAMETERS);
    }

}