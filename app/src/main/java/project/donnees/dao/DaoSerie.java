package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import project.donnees.extendedBo.Edition;
import project.donnees.extendedBo.Serie;
import framework.donnees.dao.DaoFilters;
import framework.donnees.dao.TypedDao;
import project.donnees.search.DaoSearchParameters;
import project.services.ServiceEditions;
import project.services.FactoryServices;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoSerie extends TypedDao<Serie> {

    private static final ServiceEditions svcEditions = FactoryServices.get(ServiceEditions.class);

    private static int COL_POS = 2;

    public static final String COL_KIND = "kind";
    public static final int POS_KIND = COL_POS++;

    public static final String COL_ORIGIN = "origin";
    public static final int POS_ORIGIN = COL_POS++;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_SEARCH_NAME = "searchName";
    public static final int POS_SEARCH_NAME = COL_POS++;

    public static final String COL_ENDED = "ended";
    public static final int POS_ENDED = COL_POS++;

    public static final String COL_STORY = "story";
    public static final int POS_STORY = COL_POS++;


    public DaoSerie() {
        super();
    }

    @Override
    public String getTableName() {
        return "serie";
    }

    @Override
    public void setContentValues(final ContentValues values, final Serie serie) {

        values.put(COL_KIND, serie.getKind());
        values.put(COL_ORIGIN, serie.getOrigin());
        values.put(COL_NAME, serie.getName());
        values.put(COL_SEARCH_NAME, serie.getSearchName());
        values.put(COL_ENDED, serie.isEnded());
        values.put(COL_STORY, serie.getStory());
    }

    protected Serie cursorToBo(Cursor cursor) {

        Serie bo = new Serie();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setKind(cursorToString(cursor, POS_KIND));
        bo.setOrigin(cursorToString(cursor, POS_ORIGIN));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setSearchName(cursorToString(cursor, POS_SEARCH_NAME));
        bo.setEnded(cursorToBoolean(cursor, POS_ENDED));
        bo.setStory(cursorToString(cursor, POS_STORY));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_KIND + " text not null,"
                + COL_ORIGIN + " text,"
                + COL_NAME + " text not null,"
                + COL_SEARCH_NAME + " text not null,"
                + COL_ENDED + " boolean not null,"
                + COL_STORY + " text";

    }

    public long searchCount(final DaoSearchParameters parameters) {

        long result;

        try {
            final String filters = getFilters(parameters);

            result = executeCountQuery("SELECT COUNT(*) FROM " + getTableName() + filters);

        } catch (IllegalArgumentException e) {
            result = 0;
        }


        return result;

    }

    public List<Serie> search(final DaoSearchParameters parameters) {

        List<Serie> seriesList;

        try {

            seriesList = searchInEditions(parameters);

            if (parameters.getName() != null) {

                final String seriesFilters = getFilters(parameters);
                final List<Serie> seriesListFromSeries = executeRawQuery(" SELECT * FROM " + getTableName() + seriesFilters);

                if (seriesListFromSeries.size() > 0) {

                    Map<Long, Serie> mapSeries = new HashMap<>();

                    for (Serie s : seriesList) {
                        mapSeries.put(s.getId(), s);
                    }

                    for (Serie s : seriesListFromSeries) {
                        mapSeries.put(s.getId(), s);
                    }

                    seriesList.clear();
                    seriesList.addAll(mapSeries.values());
                }
            }

            Collections.sort(seriesList);

        } catch (IllegalArgumentException e) {
            seriesList = new ArrayList<>();
        }


        return seriesList;

    }

    private List<Serie> searchInEditions(final DaoSearchParameters parameters) {

        List<Serie> seriesList;
        final List<Long> idSeriesList = new ArrayList<>();

        List<Edition> editionsList = svcEditions.search(parameters);

        if (editionsList.size() > 0) {

            for (Edition edition : editionsList) {
                idSeriesList.add(edition.getIdSerie());
            }

            seriesList = getById(idSeriesList);

        } else {
            seriesList = new ArrayList<>();
        }

        return seriesList;
    }

    private String getFilters(final DaoSearchParameters parameters){

        DaoFilters filters = new DaoFilters();

        filters.add(getNameFilter(parameters));

        return filters.toString();
    }

    private String getNameFilter(final DaoSearchParameters parameters) {

        String nameFilter = parameters.getName();
        String filter = null;

        if (nameFilter != null) {
            if (nameFilter.length() == 1) {
                filter = "(" + COL_SEARCH_NAME + " LIKE '" + nameFilter + "%')";
            } else {
                filter = "(" + COL_SEARCH_NAME + " LIKE '%" + nameFilter + "%')";
            }
        }

        return filter;
    }

}
