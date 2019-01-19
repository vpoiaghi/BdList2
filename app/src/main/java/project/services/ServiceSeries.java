package project.services;

import java.util.List;

import project.donnees.extendedBo.Serie;
import project.donnees.extendedBo.Title;
import project.donnees.dao.DaoSerie;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchParameters;
import project.services.abs.Service;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceSeries extends Service<Serie, DaoSerie> {

    private static final ServiceTitles svcTitles = FactoryServices.get(ServiceTitles.class);

    protected ServiceSeries() {
        super();
    }

    public List<Serie> search(final SearchParameters searchParameters) {
        return search(getSearchParameters(searchParameters));
    }

    public long searchCount(final SearchParameters searchParameters) {
        return searchCount(getSearchParameters(searchParameters));
    }

    public boolean isSerieCompleted(final Serie serie) {

        final List<Title> titlesList = svcTitles.getBySerie(serie);

        boolean isSerieCompleted = true;

        for (Title title : titlesList) {
            try {
                if (!title.isInPossession()) {
                    isSerieCompleted = false;
                    break;
                }
            } catch(NullPointerException e) {
                throw e;
            }
        }

        return isSerieCompleted;

    }

    public int getTitlesInPossessionCount(final List<Title> titlesList ) {
        return getTitlesCountByPossessionState(titlesList, true);
    }

    private int getTitlesCountByPossessionState(final List<Title> titlesList, final boolean possessionStatus) {

        int inPossessionCount = 0;

        for (Title title : titlesList) {

            if (title.isInPossession() == possessionStatus) {
                inPossessionCount++;
            }
        }

        return inPossessionCount;

    }

    private List<Serie> search(final DaoSearchParameters daoSearchParameters) {
        return getDao().search(daoSearchParameters);
    }

    private long searchCount(final DaoSearchParameters daoSearchParameters) {
        return getDao().searchCount(daoSearchParameters);
    }

}
