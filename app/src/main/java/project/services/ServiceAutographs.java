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

    protected ServiceAutographs() {
        super();
    }

    public List<Autograph> getByEdition(final long idEdition) {
        return getDao().getByEdition(idEdition);
    }
}
