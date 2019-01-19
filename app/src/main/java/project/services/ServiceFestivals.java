package project.services;

import java.util.List;

import project.donnees.extendedBo.Festival;
import project.donnees.dao.DaoFestival;
import project.services.abs.Service;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceFestivals extends Service<Festival, DaoFestival> {

    protected ServiceFestivals() {
        super();
    }

    public List<Festival> getByName(final String filter) {
        return getDao().getByName(filter);
    }
}
