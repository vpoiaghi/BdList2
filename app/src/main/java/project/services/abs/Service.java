package project.services.abs;

import java.util.List;

import framework.donnees.bo.Bo;
import framework.donnees.dao.TypedDao;
import project.donnees.extendedBo.Parameters;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchParameters;
import project.services.ServiceParameters;
import framework.tools.DateUtils;
import project.services.factory.ServicesFactory;
import project.utils.ListUtils;

/**
 * Created by VINCENT on 17/04/2016.
 *
 */
public abstract class Service<BoType extends Bo, DaoType extends TypedDao<BoType>> implements IService {

    protected abstract DaoType getDao();

    protected Service() {
    }

    protected DaoSearchParameters getSearchParameters(final SearchParameters searchParameters) {

        final ServiceParameters svcParameters = ServicesFactory.get(ServiceParameters.class);
        final Parameters appParameters = svcParameters.getParameters();

        final SqlDate firstComingDate = appParameters.getFirstComingDate();
        final SqlDate today = new SqlDate(DateUtils.getDayBeforeADay(firstComingDate.getDate(), 1));

        final DaoSearchParameters daoSearchParameters = new DaoSearchParameters(searchParameters);
        daoSearchParameters.setToday(today);
        daoSearchParameters.setNewsDaysCount(appParameters.getNewsDaysCount());

        return daoSearchParameters;
    }

    public List<BoType> getAll() {
        return getDao().getAll();
    }

    public BoType getById(final long id) {
        return getDao().getById(id);
    }

    public long getLastId() {
        return getDao().getLastId();
    }

    public List<BoType> getById(final List<Long> idList) {
        ListUtils.SortAndRemoveDoubleItems(idList);
        return getDao().getById(idList);
    }

    public long getCount() {
        return getDao().getAllCount();
    }

    public void save(final BoType bo) {
        getDao().save(bo);
    }
}
