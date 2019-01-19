package project.services;

import java.util.ArrayList;
import java.util.List;

import project.donnees.extendedBo.Author;
import project.donnees.extendedBo.Goody;
import project.donnees.dao.DaoAuthor;
import project.donnees.search.DaoSearchAuthorsParameters;
import project.donnees.search.SearchAuthorsParameters;
import project.services.abs.Service;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceAuthors extends Service<Author, DaoAuthor> {

    //private static final DaoAuthor daoAuthor = new DaoAuthor();
    private DaoAuthor dao;

    public ServiceAuthors() {
        super();
    }

    @Override
    protected DaoAuthor getDao() {

        if (dao == null) {
            dao = new DaoAuthor();
        }

        return dao;
    }

    public List<Author> search(final SearchAuthorsParameters searchParameters) {

        DaoSearchAuthorsParameters daoSearchParameters = new DaoSearchAuthorsParameters();
        daoSearchParameters.setName(searchParameters.getName());

        return getDao().search(daoSearchParameters);
    }

    public List<Author> getByGoody(final Goody goody) {

        List<Author> authorsList = null;

        if (goody != null) {
            final List<Long> authorsIdList = goody.getIdAuthorsList();
            if ((authorsIdList != null) && (authorsIdList.size() > 0)) {
                authorsList = getById(authorsIdList);
            }
        }

        if (authorsList == null) {
            authorsList = new ArrayList<>();
        }

        return authorsList;
    }

}
