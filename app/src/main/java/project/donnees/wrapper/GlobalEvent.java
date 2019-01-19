package project.donnees.wrapper;

import android.support.annotation.NonNull;

import java.util.Date;

import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Event;
import project.donnees.extendedBo.Goody;
import project.services.ServiceEditions;
import project.services.ServiceEvents;
import project.services.ServiceGoodies;
import project.services.factory.ServicesFactory;

/**
 * Created by VINCENT on 19/01/2019.
 *
 */
public class GlobalEvent implements Comparable<GlobalEvent> {

    public enum EventTypes {
        evtEvent,
        evtEdition,
        evtGoody
    }

    private static final int DEFAULT_REMINDER_DAYS = 5;
    private static final int DEFAULT_PERSISTENCE_DAYS = 5;
    private static final int DEFAULT_STATE = 0;

    private static final ServiceEvents svcEvents = ServicesFactory.get(ServiceEvents.class);
    private static final ServiceEditions svcEdition = ServicesFactory.get(ServiceEditions.class);
    private static final ServiceGoodies svcGoody = ServicesFactory.get(ServiceGoodies.class);

    private EventTypes eventType;
    private Event originalEvent = null;
    private Edition originalEdition = null;
    private Goody originalGoody = null;

    public GlobalEvent(Event event) {
        super();
        setEvent(event);
    }

    public GlobalEvent(Edition boEdition) {
        super();
        setEdition(boEdition);
    }

    public GlobalEvent(Goody boGoody) {
        super();
        setGoody(boGoody);
    }

    public GlobalEvent(Long id, GlobalEvent.EventTypes eventType) {
        super();

        switch (eventType) {
            case evtEvent:
                setEvent(svcEvents.getById(id));
                break;
            case evtEdition:
                setEdition(svcEdition.getById(id));
                break;
            case evtGoody:
                setGoody(svcGoody.getById(id));
                break;
        }
    }

    private void setEvent(Event event) {
        originalEvent = event;
        eventType = EventTypes.evtEvent;
    }

    private void setEdition(Edition edition) {
        originalEdition = edition;
        eventType = EventTypes.evtEdition;
    }

    private void setGoody(Goody goody) {
        originalGoody = goody;
        eventType = EventTypes.evtGoody;
    }

    @Override
    public int compareTo(@NonNull GlobalEvent another) {

        int result = 0;

        try {
            result = this.getStartDate().compareTo(another.getStartDate());
            if (result == 0) {
                result = this.getName().compareTo(another.getName());
            }
        } catch (NullPointerException e) {
            // Rien à faire
        }

        return result;

    }

    public Long getId() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getId();
            case evtEdition: return getOriginalEdition().getId();
            case evtGoody: return getOriginalGoody().getId();
            default : return null;
        }
    }

    public String getName() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getName();
            case evtEdition: return getOriginalEdition().getName();
            case evtGoody: return getOriginalGoody().getName();
            default : return null;
        }
    }

    public Date getStartDate() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getStartDate().getDate();
            case evtEdition: return getOriginalEdition().getParutionDate().getDate();
            case evtGoody: return getOriginalGoody().getParutionDate().getDate();
            default : return null;
        }
    }

    public Date getEndDate() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getEndDate().getDate();
            case evtEdition: return getOriginalEdition().getParutionDate().getDate();
            case evtGoody: return getOriginalGoody().getParutionDate().getDate();
            default : return null;
        }
    }

    public int getReminderDays() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getReminderDays();
            default : return DEFAULT_REMINDER_DAYS;
        }
    }

    public int getPersistenceDays() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getPersistenceDays();
            default : return DEFAULT_PERSISTENCE_DAYS;
        }
    }

    public String getPlace() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getPlace();
            default : return null;
        }
    }

    public String getComments() {

        switch (eventType) {
            case evtEvent: return getOriginalEvent().getComments();
            default : return null;
        }
    }

    public EventTypes getEventType() {
        return eventType;
    }

    public Event getOriginalEvent() {
        return originalEvent;
    }

    public Edition getOriginalEdition() {
        return originalEdition;
    }

    public Goody getOriginalGoody() {
        return originalGoody;
    }

    public int getState() {

        switch (getEventType()) {
            case evtEvent: return getOriginalEvent().getState();
            case evtEdition: return getOriginalEdition().getPossessionState();
            case evtGoody: return getOriginalGoody().getPossessionState();
            default: return DEFAULT_STATE;
        }
    }

    public void setState(final int state) {

        if (getEventType() == EventTypes.evtEvent) {
            getOriginalEvent().setState(state);
        } else if (getEventType() == EventTypes.evtEdition) {
            System.err.println("L'application a tenté de modifier directement l'édition d'un évènement global");
        } else if (getEventType() == EventTypes.evtGoody) {
            System.err.println("L'application a tenté de modifier directement le para-bd d'un évènement global");
        }

    }


}
