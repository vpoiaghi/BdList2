package project.services.abs;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import framework.donnees.bo.Bo;
import framework.donnees.dao.TypedDao;
import project.donnees.dao.FactoryDao;
import project.donnees.extendedBo.Parameters;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchParameters;
import project.services.FactoryServices;
import project.services.ServiceParameters;
import framework.tools.DateUtils;
import project.utils.ListUtils;

/**
 * Created by VINCENT on 17/04/2016.
 *
 */
public abstract class Service<BoType extends Bo, DaoType extends TypedDao<BoType>> implements IService {

    protected DaoType dao;

    protected Service() {
    }

    protected DaoType getDao() {

        if (dao == null) {

            // récupération des types générique de l'instance courante de Service
            // On obtien dans le ParameterizedType le type du Bo et le Type du Dao courant
            ParameterizedType parameterizedType = (ParameterizedType) getClass().getGenericSuperclass();

            // Récupération du type du Dao
            @SuppressWarnings("unchecked")
            Class<DaoType> daoType = (Class<DaoType>) parameterizedType.getActualTypeArguments()[1];

            // Récupération d'instance unique du dao
            dao = FactoryDao.get(daoType);
        }

        return dao;
    }


    protected DaoSearchParameters getSearchParameters(final SearchParameters searchParameters) {

        final ServiceParameters svcParameters = FactoryServices.get(ServiceParameters.class);
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
