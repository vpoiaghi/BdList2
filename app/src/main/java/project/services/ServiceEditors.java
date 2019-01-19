package project.services;

import java.util.List;

import project.donnees.extendedBo.Editor;
import project.donnees.dao.DaoEditor;
import project.services.abs.Service;
import project.utils.ListUtils;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class ServiceEditors extends Service<Editor, DaoEditor> {

    private DaoEditor dao;

    public ServiceEditors() {
        super();
    }

    @Override
    protected DaoEditor getDao() {

        if (dao == null) {
            dao = new DaoEditor();
        }

        return dao;
    }

    public List<Editor> getByName(final String filter) {
        return getDao().getByName(filter);
    }

}
