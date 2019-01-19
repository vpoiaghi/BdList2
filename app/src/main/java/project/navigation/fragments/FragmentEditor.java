package project.navigation.fragments;

import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Festival;
import project.donnees.extendedBo.Goody;
import project.donnees.search.SearchOrder;
import project.donnees.search.SearchParameters;
import project.navigation.adapters.EditionsAdapter;
import project.navigation.adapters.FestivalsAdapter;
import project.navigation.adapters.GoodiesAdapter;
import project.navigation.adapters.StringAdapter;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.abstract_fragments.FragmentMultiList;
import project.services.ServiceEditions;
import project.services.ServiceEditors;
import project.services.ServiceFestivals;
import project.services.ServiceGoodies;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 10/12/2018.
 *
 */
public class FragmentEditor extends FragmentMultiList {

    private static final ServiceEditions svcEditions = ServicesFactory.get(ServiceEditions.class);
    private static final ServiceEditors svcEditors = ServicesFactory.get(ServiceEditors.class);
    private static final ServiceFestivals svcFestivals = ServicesFactory.get(ServiceFestivals.class);
    private static final ServiceGoodies svcGoodies = ServicesFactory.get(ServiceGoodies.class);

    private Editor editor;

    public FragmentEditor() {
        super();
        editor = null;
    }

    @Override
    protected void doLoadFragment() {

        final long idEditor = (long) getParameterIn(ParametersCodes.ID_EDITOR);
        editor = svcEditors.getById(idEditor);

    }

    @Override
    protected void doRefreshFragment() {
        TextView txtEditorName = (TextView)findViewById(R.id.header_editor_txt_name);
        txtEditorName.setText(editor.getName());
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_editors;
    }

    @Override
    public String getFragmentTitle() {
        return "Editeur";
    }

    @Override
    protected int getFragmentHeaderLayoutId() {
        return R.layout.frp_header_editor;
    }

    @Override
    protected final List<ListDescriptor> getListDescriptors() {

        List<ListDescriptor> listDescriptors = new ArrayList<>();

        listDescriptors.add(new ListDescriptor("Informations", getInformationsAdapter(), R.drawable.icn_information));
        listDescriptors.add(new ListDescriptor("Editions recherchées", getWantedEditionsAdapter(), R.drawable.icn_page_edition));
        listDescriptors.add(new ListDescriptor("Para-bds recherchées", getWantedGoodiesAdapter(), R.drawable.icn_page_goodies));
        listDescriptors.add(new ListDescriptor("Festivals", getFestivalsAdapter(), R.drawable.icn_page_festival));

        return listDescriptors;
    }

    private BaseAdapter getInformationsAdapter() {

        List<String> infosList = new ArrayList<>();
        infosList.add("Nom : " + editor.getName());
        return new StringAdapter(getParentActivity(), R.layout.itm_string_info, infosList);

    }

    private BaseAdapter getWantedEditionsAdapter() {

        BaseAdapter adapter = null;

        final SearchParameters editionsPrms = new SearchParameters();
        editionsPrms.setIdEditor(editor.getId());
        editionsPrms.setInPossession(false);
        editionsPrms.setWanted(true);
        editionsPrms.setNotWanted(false);
        editionsPrms.setOrder(SearchOrder.ParutionDateDesc);

        final List<Edition> editionsList = svcEditions.search(editionsPrms);

        if (editionsList.size() > 0) {
            adapter = new EditionsAdapter(getParentActivity(), R.layout.itm_edition, editionsList);
        }

        return adapter;

    }

    private BaseAdapter getWantedGoodiesAdapter() {

        BaseAdapter adapter = null;

        final SearchParameters goodiesPrms = new SearchParameters();
        goodiesPrms.setIdEditor(editor.getId());
        goodiesPrms.setInPossession(false);
        goodiesPrms.setWanted(true);
        goodiesPrms.setNotWanted(false);

        final List<Goody> goodiesList = svcGoodies.search(goodiesPrms);

        if (goodiesList.size() > 0) {
            adapter = new GoodiesAdapter(getParentActivity(), R.layout.itm_goody, goodiesList);
        }

        return adapter;

    }

    private BaseAdapter getFestivalsAdapter() {

        BaseAdapter adapter = null;

        final List<Festival> festivalsList = svcFestivals.getAll();

        if (festivalsList.size() > 0) {
            adapter = new FestivalsAdapter(getParentActivity(), R.layout.itm_festival, festivalsList, editor.getId());
        }

        return adapter;
    }

}
