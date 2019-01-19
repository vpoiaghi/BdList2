package project.services;

import project.donnees.dao.DaoEvent;
import project.services.abs.Service;
import project.donnees.extendedBo.Event;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceEvents extends Service<Event, DaoEvent> {

    protected ServiceEvents() {
        super();
    }
}
