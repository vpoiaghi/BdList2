package project.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Event;
import project.donnees.extendedBo.Goody;
import project.donnees.wrapper.GlobalEvent;
import project.services.abs.IService;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceGlobalEvents implements IService {

    private static final ServiceEditions svcEditions = FactoryServices.get(ServiceEditions.class);
    private static final ServiceGoodies svcGoodies = FactoryServices.get(ServiceGoodies.class);
    private static final ServiceEvents svcEvents = FactoryServices.get(ServiceEvents.class);

    public ServiceGlobalEvents() {
        super();
    }

    public void save(final GlobalEvent globalEvent) {

        switch(globalEvent.getEventType()) {
            case evtEvent:
                svcEvents.save(globalEvent.getOriginalEvent());
                break;
            case evtEdition:
                svcEditions.save(globalEvent.getOriginalEdition());
                break;
            case evtGoody:
                svcGoodies.save(globalEvent.getOriginalGoody());
                break;
        }

    }

    public long getCount() {
        return getAll().size();
    }

    public List<GlobalEvent> getAll() {

        List<GlobalEvent> globalEventsList = new ArrayList<>();

        List<Event> eventsList = svcEvents.getAll();
        for (Event event: eventsList) {
            globalEventsList.add(new GlobalEvent(event));
        }

        List<Edition> editionsList = svcEditions.getToUseAsEvent();
        for (Edition edition: editionsList) {
            globalEventsList.add(new GlobalEvent(edition));
        }

        List<Goody> goodiesList = svcGoodies.getToUseAsEvent();
        for (Goody goody: goodiesList) {
            globalEventsList.add(new GlobalEvent(goody));
        }


        Collections.sort(globalEventsList);

        return globalEventsList;
    }

    public void setState(final GlobalEvent globalEvent, final int state) {

        switch(globalEvent.getEventType()) {
            case evtEvent:
                globalEvent.setState(state);
                break;
            case evtEdition:
                svcEditions.setInPossession(globalEvent.getOriginalEdition(), state);
                break;
            case evtGoody:
                svcGoodies.setInPossession(globalEvent.getOriginalGoody(), state);
                break;
        }

    }

}
