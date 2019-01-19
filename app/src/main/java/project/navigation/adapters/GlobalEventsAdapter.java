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
import framework.tools.DateUtils;
import project.donnees.wrapper.GlobalEvent;
import project.navigation.constants.ConstantesEvents;
import project.navigation.constants.ParametersCodes;
import project.navigation.fragments.FragmentEvent;
import project.utils.ImageUtils;


/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class GlobalEventsAdapter extends ArrayAdapter<GlobalEvent> {

    private final FragmentManagerActivity parentActivity;
    private final int resource;
    private final List<GlobalEvent> eventsList;

    public GlobalEventsAdapter(final FragmentManagerActivity activity, final int resource, final List<GlobalEvent> eventsList) {
        super(activity, resource, eventsList);

        this.parentActivity = activity;
        this.resource = resource;
        this.eventsList = eventsList;
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

        initRow(row, eventsList.get(position));

        return row;
    }

    private void initRow(final View row, final GlobalEvent globalEvent) {

        String txt = DateUtils.dateToString(globalEvent.getStartDate()) + " - " + globalEvent.getName();
        final TextView txtEventDateAndName = (TextView) row.findViewById(R.id.itm_event_txt_date_and_name);
        txtEventDateAndName.setText(txt);

        txt = globalEvent.getPlace();
        final TextView txtEventPlace = (TextView) row.findViewById(R.id.itm_event_txt_place);
        txtEventPlace.setText(txt);

        txt = globalEvent.getComments();
        final TextView txtEventComments = (TextView) row.findViewById(R.id.itm_event_txt_comments);
        txtEventComments.setText(txt);

        final ImageView imgReminder = (ImageView) row.findViewById(R.id.itm_event_img_reminder);
        ImageUtils.loadEventReminderImage(imgReminder, globalEvent);

        final ImageView imgState = (ImageView) row.findViewById(R.id.itm_event_img_state);
        updateStateImage(imgState, globalEvent);

        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentActivity.addParameterOut(ParametersCodes.ID_GLOBAL_EVENT, globalEvent.getId());
                parentActivity.addParameterOut(ParametersCodes.EVENT_TYPE, globalEvent.getEventType());
                parentActivity.showFragment(FragmentEvent.class.getName());
            }
        });
    }

    private void updateStateImage(final ImageView imgState, final GlobalEvent globalEvent) {

        switch(globalEvent.getEventType()) {
            case evtEvent:
                if (globalEvent.getState() == ConstantesEvents.EVENT_CHECKED) {
                    imgState.setImageResource(R.drawable.icn_event_checked);
                } else {
                    imgState.setImageResource(R.drawable.icn_event_coming);
                }
                break;

            case evtEdition:
                ImageUtils.loadPossessionImage(imgState, globalEvent.getOriginalEdition());
                break;

            case evtGoody:
                ImageUtils.loadPossessionImage(imgState, globalEvent.getOriginalGoody());
                break;
        }

    }
}
