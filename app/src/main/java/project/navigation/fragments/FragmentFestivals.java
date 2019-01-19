package project.navigation.fragments;

import android.widget.BaseAdapter;

import java.util.List;

import bdlist.bdlist.R;
import project.navigation.fragments.abstract_fragments.FragmentList;
import project.donnees.extendedBo.Festival;
import project.navigation.adapters.FestivalsAdapter;
import project.services.ServiceFestivals;
import project.services.FactoryServices;

/**
 *
 * Created by VINCENT on 27/02/2016.
 */
public class FragmentFestivals extends FragmentList {

    private static final ServiceFestivals svcFestivals = FactoryServices.get(ServiceFestivals.class);

    public FragmentFestivals() {
        super();
    }

    @Override
    protected BaseAdapter getAdapter() {
        List<Festival> festivalsList = svcFestivals.getAll();
        return new FestivalsAdapter(getParentActivity(), R.layout.itm_festival, festivalsList);
    }

    @Override
    protected BaseAdapter getAdapter(final String filter) {
        List<Festival> festivalsList = svcFestivals.getByName(filter);
        return new FestivalsAdapter(getParentActivity(), R.layout.itm_festival, festivalsList);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_festival;
    }

    @Override
    public String getFragmentTitle() {
        return "Festivals";
    }

}