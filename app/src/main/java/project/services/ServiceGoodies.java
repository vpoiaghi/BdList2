package project.services;

import java.util.ArrayList;
import java.util.List;

import project.donnees.extendedBo.Goody;
import project.donnees.extendedBo.Serie;
import project.donnees.dao.DaoGoody;
import project.donnees.search.DaoSearchGoodiesParameters;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchGoodiesParameters;
import project.donnees.search.SearchOrder;
import project.donnees.search.SearchParameters;
import project.services.abs.Service;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceGoodies extends Service<Goody, DaoGoody> {

    protected ServiceGoodies() {
        super();
    }

    public long getCountBySerie(final Serie serie) {

        DaoSearchParameters daoSearchParameters = new DaoSearchParameters();
        daoSearchParameters.setIdSerie(serie.getId());

        return searchCount(daoSearchParameters);
    }

    public List<Goody> getBySerie(final Long idSerie) {

        DaoSearchParameters daoSearchParameters = new DaoSearchParameters();
        daoSearchParameters.setIdSerie(idSerie);
        daoSearchParameters.setOrder(SearchOrder.ParutionDateAsc);

        return search(daoSearchParameters);
    }

    public List<Goody> getPress() {
        return getDao().getPress();
    }

    public List<Goody> search(SearchParameters searchParameters) {
        return search(getSearchParameters(searchParameters));
    }

    public List<Goody> search(SearchGoodiesParameters searchParameters) {

        DaoSearchGoodiesParameters daoSearchParameters = new DaoSearchGoodiesParameters();
        daoSearchParameters.setKindsNames(searchParameters.getKindsNames());
        daoSearchParameters.setIdAuthor(searchParameters.getIdAuthor());

        return getDao().search(daoSearchParameters);
    }

    public long searchCount(final SearchParameters searchParameters) {
        return searchCount(getSearchParameters(searchParameters));
    }

    public List<Goody> getToUseAsEvent() {

        List<Goody> pressList = getPress();
        List<Goody> otherList = getDao().getToUseAsEvent();
        List<Goody> resultList = new ArrayList<>();

        for(Goody g : otherList) {
            if (! pressList.contains(g)) {
                resultList.add(g);
            }
        }

        return resultList;
    }

    private List<Goody> search(DaoSearchParameters daoSearchParameters) {
        return getDao().search(daoSearchParameters);
    }

    private long searchCount(final DaoSearchParameters  daoSearchParameters) {
        return getDao().searchCount(daoSearchParameters);
    }

    public List<String> getKinds() {
        return getDao().getKinds();
    }

    public static String getLongName(Goody goody) {
        return goody.getSerieName() + " - " + goody.getName();
    }

    public void setInPossession(final Goody goody, final Integer possessionState) {

        if (goody.getPossessionState().intValue() != possessionState.intValue()) {
            goody.setPossessionState(possessionState);
            save(goody);
        }
    }
}
