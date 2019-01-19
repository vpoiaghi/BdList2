package project.services;

import java.util.List;

import project.donnees.extendedBo.Autograph;
import project.donnees.dao.DaoAutograph;
import project.services.abs.Service;

/**
 * Created by VINCENT on 24/04/2016.
 *
 */
public class ServiceAutographs extends Service<Autograph, DaoAutograph> {

    private DaoAutograph dao;

    public ServiceAutographs() {
        super();
    }

    @Override
    protected DaoAutograph getDao() {

        if (dao == null) {
            dao = new DaoAutograph();
        }

        return dao;
    }


    public List<Autograph> getByEdition(final long idEdition) {

        return getDao().getByEdition(idEdition);

    }
}
