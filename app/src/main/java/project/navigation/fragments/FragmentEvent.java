package project.navigation.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import bdlist.bdlist.R;
import framework.fragments.AbstractFragment;
import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Goody;
import project.donnees.wrapper.GlobalEvent;
import project.navigation.activities.PopupPossessionStatesActivity;
import project.navigation.constants.ConstantesEvents;
import project.navigation.constants.ParametersCodes;
import project.services.FactoryServices;
import project.services.ServiceEditions;
import project.services.ServiceGlobalEvents;
import project.services.ServiceGoodies;
import project.donnees.extendedBo.Event;
import project.utils.ImageUtils;

/**
 *
 * Created by VINCENT on 19/05/2016.
 */
public class FragmentEvent extends AbstractFragment {

    private static final ServiceEditions svcEditions = FactoryServices.get(ServiceEditions.class);
    private static final ServiceGoodies svcGoodies = FactoryServices.get(ServiceGoodies.class);

    private static final ServiceGlobalEvents svcGlobalEvents = FactoryServices.get(ServiceGlobalEvents.class);

    private GlobalEvent globalEvent = null;

    public FragmentEvent() {
        super(R.layout.frg_event);
    }

    @Override
    protected void loadFragment() {

        final Long eventId = (Long) getParameterIn(ParametersCodes.ID_GLOBAL_EVENT);
        final GlobalEvent.EventTypes eventType = (GlobalEvent.EventTypes) getParameterIn(ParametersCodes.EVENT_TYPE);
        globalEvent = new GlobalEvent(eventId, eventType);

    }

    @Override
    protected void refreshFragment() {

        String txt = globalEvent.getStartDate().toString();
        final TextView txtEventDate = (TextView) findViewById(R.id.event_txt_date);
        txtEventDate.setText(txt);

        txt = globalEvent.getName();
        final TextView txtEventName = (TextView) findViewById(R.id.event_txt_name);
        txtEventName.setText(txt);

        txt = globalEvent.getPlace();
        final TextView txtEventPlace = (TextView) findViewById(R.id.event_txt_place);
        txtEventPlace.setText(txt);

        txt = globalEvent.getComments();
        final TextView txtEventComments = (TextView) findViewById(R.id.event_txt_comments);
        txtEventComments.setText(txt);

        loadImage(globalEvent);
        initPossessionPicture();
        updateReminderImage(globalEvent);

    }

    @Override
    public Integer getFragmentIcon() {
        return R.drawable.icn_page_events;
    }

    @Override
    public String getFragmentTitle() {
        return "Evènement";
    }


    private void loadImage(GlobalEvent event) {

        final ImageView imgEvent = (ImageView) findViewById(R.id.event_image);

        final GlobalEvent.EventTypes eventType = event.getEventType();

        if (eventType == GlobalEvent.EventTypes.evtEvent) {
            ImageUtils.loadEventImage(imgEvent, event);
            initLink(R.id.event_image, FragmentImageEvent.class.getName());
        } else if (eventType == GlobalEvent.EventTypes.evtEdition) {
            Edition edition = event.getOriginalEdition();
            ImageUtils.loadEditionFrontCoverImage(imgEvent, edition);
            initLink(R.id.event_image, FragmentImageEdition.class.getName(), ParametersCodes.ID_EDITION, edition.getId());
        } else if (eventType == GlobalEvent.EventTypes.evtGoody) {
            Goody goody = event.getOriginalGoody();
            ImageUtils.loadGoodyImage(imgEvent, goody);
            initLink(R.id.event_image, FragmentImageGoody.class.getName(), ParametersCodes.ID_GOODY, goody.getId());
        }

    }

    private void initPossessionPicture() {

        final ImageView imgPossession = (ImageView) findViewById(R.id.event_img_in_possession);
        imgPossession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePossessionState();
            }
        });

        refreshPossessionPicture(globalEvent);
    }

    private void changePossessionState() {

        Integer eventState = globalEvent.getState();

        if (globalEvent.getEventType() == GlobalEvent.EventTypes.evtEvent) {

            if (eventState == ConstantesEvents.EVENT_UNCHECKED) {
                globalEvent.setState(ConstantesEvents.EVENT_CHECKED);
            } else {
                globalEvent.setState(ConstantesEvents.EVENT_UNCHECKED);
            }

            svcGlobalEvents.save(globalEvent);
            refreshPossessionPicture(globalEvent);

        } else {
            final Intent itt = new Intent(getParentActivity(), PopupPossessionStatesActivity.class);
            Uri u = Uri.parse(eventState.toString());
            itt.setData(u);
            startActivityForResult(itt, PopupPossessionStatesActivity.POPUP_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PopupPossessionStatesActivity.POPUP_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                Uri u = data.getData();
                String strPossessionState = u.toString();
                Integer possessionState = Integer.parseInt(strPossessionState);

                svcGlobalEvents.setState(globalEvent, possessionState);
                refreshPossessionPicture(globalEvent);
            }
        }
    }

    private void refreshPossessionPicture(final GlobalEvent globalEvent) {

        switch (globalEvent.getEventType()) {
            case evtEvent:
                refreshRealEventPossessionPicture(globalEvent.getOriginalEvent());
                break;
            case evtEdition:
                refreshEditionPossessionPicture(globalEvent.getOriginalEdition());
                break;
            case evtGoody:
                refreshGoodyPossessionPicture(globalEvent.getOriginalGoody());
                break;
        }

    }

    private void refreshEditionPossessionPicture(final Edition edition) {
        final ImageView imgPossession = (ImageView) findViewById(R.id.event_img_in_possession);
        ImageUtils.loadPossessionImage(imgPossession, edition);
    }

    private void refreshGoodyPossessionPicture(final Goody goody) {
        final ImageView imgPossession = (ImageView) findViewById(R.id.event_img_in_possession);
        ImageUtils.loadPossessionImage(imgPossession, goody);
    }

    private void refreshRealEventPossessionPicture(final Event event) {

        final ImageView imgPossession = (ImageView) findViewById(R.id.event_img_in_possession);

        if (event.getState() == ConstantesEvents.EVENT_CHECKED) {
            imgPossession.setImageResource(R.drawable.icn_event_checked);
        } else {
            imgPossession.setImageResource(R.drawable.icn_event_coming);
        }

    }

    private void updateReminderImage(final GlobalEvent globalEvent) {

        final ImageView imgReminder = (ImageView) findViewById(R.id.event_img_reminder);
        ImageUtils.loadEventReminderImage(imgReminder, globalEvent);

    }
}
