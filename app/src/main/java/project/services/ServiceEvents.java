package project.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import project.donnees.extendedBo.Edition;
import project.donnees.bo.BoEvent;
import project.donnees.extendedBo.Goody;
import project.donnees.dao.DaoEvent;
import project.services.abs.Service;
import project.donnees.extendedBo.Event;
import project.services.factory.ServicesFactory;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class ServiceEvents extends Service<Event, DaoEvent> {

    private DaoEvent dao;

    public ServiceEvents() {
        super();
    }

    @Override
    protected DaoEvent getDao() {

        if (dao == null) {
            dao = new DaoEvent();
        }

        return dao;
    }
}
