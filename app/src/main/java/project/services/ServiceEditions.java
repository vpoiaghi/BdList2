package project.services;

import java.util.ArrayList;
import java.util.List;

import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Serie;
import project.donnees.extendedBo.Title;
import project.donnees.dao.DaoEdition;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchOrder;
import project.donnees.search.SearchParameters;
import project.services.abs.Service;
import project.utils.PossessionStates;

/**
 * Created by VINCENT on 05/03/2016.
 *
 */
public class ServiceEditions extends Service<Edition, DaoEdition> {

    private static final ServiceTitles svcTitles = FactoryServices.get(ServiceTitles.class);

    protected ServiceEditions() {
        super();
    }

    public List<Edition> search(final SearchParameters searchParameters) {
        return search(getSearchParameters(searchParameters));
    }

    public long searchCount(final SearchParameters searchParameters) {
        return searchCount(getSearchParameters(searchParameters));
    }

    public long getComingCount() {
        return searchCount(SearchParameters.getComingsParameters());
    }

    public long getNewsCount() {
        return searchCount(SearchParameters.getNewsParameters());
    }

    public List<Edition> getBySerie(final Serie serie) {
        return getBySerie(serie.getId());
    }

    public List<Edition> getBySerie(final Long idSerie) {

        DaoSearchParameters daoSearchParameters = new DaoSearchParameters();
        daoSearchParameters.setIdSerie(idSerie);
        daoSearchParameters.setOrder(SearchOrder.SerieDesc);

        return search(daoSearchParameters);

    }

    public List<Edition> getByIdTitle(Long idTitle) {
        return getDao().getByIdTitle(idTitle);
    }

    public List<Edition> getByIdTitles(List<Long> idTitlesList) {
        return getDao().getByIdTitles(idTitlesList);
    }

    public List<Edition> getByAuthor(Long idAuthor) {
        return getDao().getByAuthor(idAuthor);
    }

    public List<Edition> getSimpleEditionsByIdTitles(List<Long> idTitlesList) {
        return getDao().getSimpleEditionsByIdTitles(idTitlesList);
    }

    public List<Edition> getPress() {
        return getDao().getPress();
    }

    private List<Edition> search(final DaoSearchParameters daoSearchParameters) {
        return getDao().search(daoSearchParameters);
    }

    public List<Edition> getToUseAsEvent() {

        List<Edition> pressList = getPress();
        List<Edition> otherList = getDao().getToUseAsEvent();
        List<Edition> resultList = new ArrayList<>();

        for(Edition e : otherList) {
            if (! pressList.contains(e)) {
                resultList.add(e);
            }
        }

        return resultList;
    }

    private long searchCount(final DaoSearchParameters daoSearchParameters) {
        return getDao().searchCount(daoSearchParameters);
    }

    public void setInPossession(final Edition edition, final Integer possessionState) {

        if (edition.getPossessionState().intValue() != possessionState.intValue()) {

            edition.setPossessionState(possessionState);
            save(edition);

            final List<Long> idTitlesList = edition.getIdTitlesList();
            final List<Title> titlesList = svcTitles.getById(idTitlesList);

            if (possessionState == PossessionStates.IN_POSSESSION) {

                for (Title title : titlesList) {
                    svcTitles.setInPossession(title, true);
                }

            } else {

                List<Edition> editionsList;
                boolean titleInPossession;

                for (Title title : titlesList) {

                    editionsList = getByIdTitle(title.getId());
                    titleInPossession = false;

                    for (Edition e : editionsList) {
                        titleInPossession |= (e.getPossessionState() == PossessionStates.IN_POSSESSION);
                    }

                    svcTitles.setInPossession(title, titleInPossession);
                }
            }
        }
    }

    public static String getNumber(Edition edition) {

        final String itg = (edition.isIntegral() ? "[INT] " : "");
        final String msc = (edition.isSet() ? "[REC] " : "");

        String num = edition.getEditionNumber();
        if (num != null) {
            num += "- ";
        } else {
            num = "";
        }

        String spe = edition.getSpecialEdition();
        if (spe != null) {
            spe = "(" + spe + ") ";
        } else {
            spe = "";
        }

        return (itg + msc + num + spe).trim();

    }

    public static String getLongName(Edition edition) {
        return edition.getSerieName() + " - " + getNumber(edition) + " " + edition.getName();
    }

    public static String getName(Edition edition) {
        return (getNumber(edition) + " " + edition.getName()).trim();
    }
}
