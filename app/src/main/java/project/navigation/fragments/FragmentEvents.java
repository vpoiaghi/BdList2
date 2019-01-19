package project.navigation.fragments;

import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.donnees.wrapper.GlobalEvent;
import project.navigation.adapters.GlobalEventsAdapter;
import project.navigation.components.ImageViewButton;
import project.navigation.constants.ConstantesEvents;
import project.services.ServiceGlobalEvents;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 18/05/2016.
 *
 */
public class FragmentEvents extends AbstractFragment {

    private static final ServiceGlobalEvents svcGlobalEvents = ServicesFactory.get(ServiceGlobalEvents.class);

    private List<GlobalEvent> globalEventsList = null;
    private GlobalEventsAdapter adapter = null;

    private boolean showChecked = true;
    private boolean showUnChecked = true;

    public FragmentEvents() {
        super(R.layout.frg_events);
    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_events;
    }

    @Override
    public String getFragmentTitle() {
        return "Evènements";
    }


    @Override
    protected void loadFragment() {

        showChecked = true;
        showUnChecked = true;

        globalEventsList = svcGlobalEvents.getAll();
        adapter = buildEventsAdapter();
    }

    @Override
    protected void refreshFragment() {

        ImageViewButton btnShowCheckedEvents = (ImageViewButton) findViewById(R.id.events_btn_show_checked_events);
        btnShowCheckedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideChecked();
            }
        });

        ImageViewButton btnShowUnCheckedEvents = (ImageViewButton) findViewById(R.id.events_btn_show_unchecked_events);
        btnShowUnCheckedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrHideUnChecked();
            }
        });

        showEvents();
    }

    private void showOrHideChecked() {

        ImageViewButton btnShowCheckedEvents = (ImageViewButton) findViewById(R.id.events_btn_show_checked_events);

        showChecked = !showChecked;

        if (showChecked) {
            btnShowCheckedEvents.setImageResource(R.drawable.btn_show_checked);
        } else {
            btnShowCheckedEvents.setImageResource(R.drawable.btn_show_checked_ne);
        }

        showEvents();
    }

    private void showOrHideUnChecked() {

        ImageViewButton btnShowUnCheckedEvents = (ImageViewButton) findViewById(R.id.events_btn_show_unchecked_events);

        showUnChecked = !showUnChecked;

        if (showUnChecked) {
            btnShowUnCheckedEvents.setImageResource(R.drawable.btn_show_unchecked);
        } else {
            btnShowUnCheckedEvents.setImageResource(R.drawable.btn_show_unchecked_ne);
        }

        showEvents();
    }

    private GlobalEventsAdapter buildEventsAdapter() {

        final List<GlobalEvent> filteredEventsList = new ArrayList<>();

        for (GlobalEvent event : globalEventsList) {

            if (event.getEventType() == GlobalEvent.EventTypes.evtEvent) {

                if ((event.getState() == ConstantesEvents.EVENT_CHECKED) && (showChecked)) {
                    filteredEventsList.add(event);
                } else if ((event.getState() == ConstantesEvents.EVENT_UNCHECKED) && (showUnChecked)) {
                    filteredEventsList.add(event);
                }

            } else {
                filteredEventsList.add(event);
            }
        }

        return new GlobalEventsAdapter(getParentActivity(), R.layout.itm_event, filteredEventsList);
    }

    private void showEvents() {

        final ListView listView = (ListView) findViewById(R.id.frg_list_list);
        listView.setAdapter(adapter);

    }
}
