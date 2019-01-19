package project.navigation.fragments;

import android.widget.BaseAdapter;

import java.util.List;

import bdlist.bdlist.R;
import project.donnees.wrapper.GlobalEvent;
import project.navigation.adapters.GlobalEventsAdapter;
import project.navigation.fragments.abstract_fragments.FragmentList;
import project.services.FactoryServices;
import project.services.ServiceGlobalEvents;

/**
 * Created by VINCENT on 18/05/2016.
 *
 */
public class FragmentEvents extends FragmentList {

    private static final ServiceGlobalEvents svcGlobalEvents = FactoryServices.get(ServiceGlobalEvents.class);

    public FragmentEvents() {
        super();
    }

    @Override
    protected BaseAdapter getAdapter() {
        List<GlobalEvent> globalEventsList = svcGlobalEvents.getAll();
        return new GlobalEventsAdapter(getParentActivity(), R.layout.itm_event, globalEventsList);
    }

    @Override
    protected BaseAdapter getAdapter(final String filter) {
        List<GlobalEvent> globalEventsList = svcGlobalEvents.getByName(filter);
        return new GlobalEventsAdapter(getParentActivity(), R.layout.itm_event, globalEventsList);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_events;
    }

    @Override
    public String getFragmentTitle() {
        return "Evènements";
    }

}
