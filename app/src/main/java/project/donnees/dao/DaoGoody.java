package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.donnees.extendedBo.Goody;
import framework.donnees.dao.DaoFilters;
import framework.donnees.dao.TypedDao;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.search.DaoSearchGoodiesParameters;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchOrder;
import framework.tools.DateUtils;
import project.utils.PossessionStates;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoGoody extends TypedDao<Goody> {

    private static int COL_POS = 2;

    public static final String COL_ID_EDITOR = "idEditor";
    public static final int POS_ID_EDITOR = COL_POS++;

    public static final String COL_ID_SERIE = "idSerie";
    public static final int POS_ID_SERIE = COL_POS++;

    public static final String COL_ID_SERIES = "idSeries";
    public static final int POS_ID_SERIES = COL_POS++;

    public static final String COL_ID_AUTHOR = "idAuthor";
    public static final int POS_ID_AUTHOR = COL_POS++;

    public static final String COL_ID_AUTHORS = "idAuthors";
    public static final int POS_ID_AUTHORS = COL_POS++;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_SEARCH_NAME = "searchName";
    public static final int POS_SEARCH_NAME = COL_POS++;

    public static final String COL_SERIE_NAME = "serieName";
    public static final int POS_SERIE_NAME = COL_POS++;

    public static final String COL_SERIE_SEARCH_NAME = "serieSearchName";
    public static final int POS_SERIE_SEARCH_NAME = COL_POS++;

    public static final String COL_KIND_NAME = "kindName";
    public static final int POS_KIND_NAME = COL_POS++;

    public static final String COL_EDITOR_NAME = "editorName";
    public static final int POS_EDITOR_NAME = COL_POS++;

    public static final String COL_STATE = "state";
    public static final int POS_STATE = COL_POS++;

    public static final String COL_WITH_AUTOGRAPH = "isWithAutograph";
    public static final int POS_WITH_AUTOGRAPH = COL_POS++;

    public static final String COL_PARUTION_DATE = "parutionDate";
    public static final int POS_PARUTION_DATE = COL_POS++;

    public static final String COL_POSSESSION_STATE = "possessionState";
    public static final int POS_POSSESSION_STATE = COL_POS++;

    public static final String COL_COPY_NUMBER = "copyNumber";
    public static final int POS_COPY_NUMBER = COL_POS++;

    public static final String COL_COPY_COUNT = "copyCount";
    public static final int POS_COPY_COUNT = COL_POS++;

    public static final String COL_SIZE_X = "sizeX";
    public static final int POS_SIZE_X = COL_POS++;

    public static final String COL_SIZE_Y = "sizeY";
    public static final int POS_SIZE_Y = COL_POS++;

    public static final String COL_SIZE_Z = "sizeZ";
    public static final int POS_SIZE_Z = COL_POS++;

    public static final String COL_ORDER_NUMBER = "orderNumber";
    public static final int POS_ORDER_NUMBER = COL_POS++;


    public DaoGoody() {
        super();
    }

    @Override
    public String getTableName() {
        return "goody";
    }

    @Override
    public void setContentValues(final ContentValues values, final Goody goody) {

        values.put(COL_ID_EDITOR, goody.getIdEditor());
        values.put(COL_ID_SERIE, goody.getIdSerie());
        values.put(COL_ID_SERIES, goody.getIdSeries());
        values.put(COL_ID_AUTHOR, goody.getIdAuthor());
        values.put(COL_ID_AUTHORS, goody.getIdAuthors());
        values.put(COL_NAME, goody.getName());
        values.put(COL_SEARCH_NAME, goody.getSearchName());
        values.put(COL_SERIE_NAME, goody.getSerieName());
        values.put(COL_SERIE_SEARCH_NAME, goody.getSerieSearchName());
        values.put(COL_KIND_NAME, goody.getKindName());
        values.put(COL_EDITOR_NAME, goody.getEditorName());
        values.put(COL_STATE, goody.getState());
        values.put(COL_WITH_AUTOGRAPH, goody.isWithAutograph());
        values.put(COL_PARUTION_DATE, goody.getParutionDate().getValue());
        values.put(COL_POSSESSION_STATE, goody.getPossessionState());
        values.put(COL_COPY_NUMBER, goody.getCopyNumber());
        values.put(COL_COPY_COUNT, goody.getCopyCount());
        values.put(COL_SIZE_X, goody.getSizeX());
        values.put(COL_SIZE_Y, goody.getSizeY());
        values.put(COL_SIZE_Z, goody.getSizeZ());
        values.put(COL_ORDER_NUMBER, goody.getOrderNumber());

    }

    protected Goody cursorToBo(Cursor cursor) {

        Goody bo = new Goody();

        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setIdEditor(cursorToLong(cursor, POS_ID_EDITOR));
        bo.setIdSerie(cursorToLong(cursor, POS_ID_SERIE));
        bo.setIdSeries(cursorToString(cursor, POS_ID_SERIES));
        bo.setIdAuthor(cursorToLong(cursor, POS_ID_AUTHOR));
        bo.setIdAuthors(cursorToString(cursor, POS_ID_AUTHORS));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setSearchName(cursorToString(cursor, POS_SEARCH_NAME));
        bo.setSerieName(cursorToString(cursor, POS_SERIE_NAME));
        bo.setSerieSearchName(cursorToString(cursor, POS_SERIE_SEARCH_NAME));
        bo.setKindName(cursorToString(cursor, POS_KIND_NAME));
        bo.setEditorName(cursorToString(cursor, POS_EDITOR_NAME));
        bo.setState(cursorToInteger(cursor, POS_STATE));
        bo.setWithAutograph(cursorToBoolean(cursor, POS_WITH_AUTOGRAPH));
        bo.setParutionDate(cursorToDate(cursor, POS_PARUTION_DATE));
        bo.setPossessionState(cursorToInteger(cursor, POS_POSSESSION_STATE));
        bo.setCopyNumber(cursorToString(cursor, POS_COPY_NUMBER));
        bo.setCopyCount(cursorToInteger(cursor, POS_COPY_COUNT));
        bo.setSizeX(cursorToInteger(cursor, POS_SIZE_X));
        bo.setSizeY(cursorToInteger(cursor, POS_SIZE_Y));
        bo.setSizeZ(cursorToInteger(cursor, POS_SIZE_Z));
        bo.setOrderNumber(cursorToInteger(cursor, POS_ORDER_NUMBER));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_ID_EDITOR + " long,"
                + COL_ID_SERIE + " long,"
                + COL_ID_SERIES + " text,"
                + COL_ID_AUTHOR + " long,"
                + COL_ID_AUTHORS + " text,"
                + COL_NAME + " text not null,"
                + COL_SEARCH_NAME + " text not null,"
                + COL_SERIE_NAME + " text,"
                + COL_SERIE_SEARCH_NAME + " text,"
                + COL_KIND_NAME + " text not null,"
                + COL_EDITOR_NAME + " text,"
                + COL_STATE + " int,"
                + COL_WITH_AUTOGRAPH + " boolean not null,"
                + COL_PARUTION_DATE + " text,"
                + COL_POSSESSION_STATE + " int not null,"
                + COL_COPY_NUMBER + " text,"
                + COL_COPY_COUNT + " int,"
                + COL_SIZE_X + " int,"
                + COL_SIZE_Y + " int,"
                + COL_SIZE_Z + " int,"
                + COL_ORDER_NUMBER + " int";
    }

    public List<Goody> getPress() {

        Date dte = DateUtils.getToday();
        dte = DateUtils.getFirstOfMonth(dte);
        dte = DateUtils.getMonthBeforeADay(dte, 2);
        SqlDate sqlDate = new SqlDate(dte);
        String strDate = sqlDate.getValue();

        String rqt = "SELECT * FROM " + getTableName()
                + " WHERE " + COL_ID_SERIE + " IN (501,610,678,859,903)"
                + " AND (" + COL_PARUTION_DATE + " > '" + strDate + "' OR " + COL_POSSESSION_STATE + " != " + PossessionStates.IN_POSSESSION + ")"
                + " ORDER BY " + COL_PARUTION_DATE + " ASC, " + COL_ID_SERIE + " ASC";

        return executeRawQuery(rqt);
    }

    public long searchCount(final DaoSearchParameters parameters) {

        long result;

        try {
            final String filters = getSearchFiltersPart(parameters);

            result = executeCountQuery("SELECT COUNT(*) FROM " + getTableName() + filters);

        } catch (IllegalArgumentException e) {
            result = 0;
        }


        return result;

    }

    public List<Goody> search(final DaoSearchParameters parameters) {

        List<Goody> result;

        try {

            final String filters = getSearchFiltersPart(parameters);
            final String orderPart = getSearchOrderPart(parameters);

            result = executeRawQuery(" SELECT * FROM " + getTableName() + filters + orderPart);

        } catch (IllegalArgumentException e) {
            result = new ArrayList<>();
        }


        return result;

    }

    public List<Goody> search(final DaoSearchGoodiesParameters parameters) {

        List<Goody> result;

        try {

            final String filters = getSearchFiltersPart(parameters);

            result = executeRawQuery(" SELECT * FROM " + getTableName() + filters);

        } catch (IllegalArgumentException e) {
            result = new ArrayList<>();
        }


        return result;

    }

    public List<Goody> getToUseAsEvent() {

        final SqlDate lastReservedDate = new SqlDate(DateUtils.getDayAfterADay(DateUtils.getToday(), 30));

        String rqt = "SELECT * FROM " + getTableName() +
                " WHERE (" + COL_POSSESSION_STATE + " = " + PossessionStates.TO_RESERVE_AT_CULTURA + ")" +
                " OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.TO_ORDER_AT_BDFUGUE + ")" +
                " OR ((" + COL_POSSESSION_STATE + " = " + PossessionStates.RESERVED + ") AND (" + COL_PARUTION_DATE + " <= '" + lastReservedDate.getValue() + "'))";

        return executeRawQuery(rqt);
    }


    private String getSearchFiltersPart(final DaoSearchParameters parameters){

        DaoFilters filters = new DaoFilters();

        filters.add(getNameFilter(parameters));
        filters.add(getEditorFilter(parameters));
        filters.add(getSerieFilter(parameters));
        filters.add(getPossessionFilter(parameters));
        filters.add(getSearchDateFilter(parameters));
        filters.add(getKindOfGoodiesFilter(parameters));

        return filters.toString();
    }

    private String getSearchFiltersPart(final DaoSearchGoodiesParameters parameters){

        DaoFilters filters = new DaoFilters();

        filters.add(getKindOfGoodiesFilter(parameters));
        filters.add(getAuthorFilter(parameters));

        return filters.toString();
    }

    private String getSearchOrderPart(final DaoSearchParameters parameters){

        final String orderPart;
        final SearchOrder searchOrder = parameters.getOrder();

        if (searchOrder != null) {
            switch (searchOrder) {
                case SerieDesc:
                    orderPart = " ORDER BY " + COL_SERIE_NAME + " ASC, " + COL_PARUTION_DATE + " ASC";
                    break;
                case ParutionDateAsc:
                    orderPart = " ORDER BY " + COL_PARUTION_DATE + " ASC, " + COL_SERIE_NAME + " ASC";
                    break;
                case ParutionDateDesc:
                    orderPart = " ORDER BY " + COL_PARUTION_DATE + " ASC, " + COL_SERIE_NAME + " ASC";
                    break;
                case OrderNumberDesc:
                    orderPart = " ORDER BY " + COL_ORDER_NUMBER + " ASC, " + COL_SERIE_NAME + " ASC";
                    break;
                default:
                    orderPart = "";
                    break;
            }
        } else {
            orderPart = "";
        }

        return orderPart;
    }

    private String getEditorFilter(final DaoSearchParameters parameters) {

        Long idEditor = parameters.getIdEditor();
        String filter = null;

        if (idEditor != null) {
            filter = "(" + COL_ID_EDITOR + "=" + idEditor + ")";
        }

        return filter;
    }

    private String getSerieFilter(final DaoSearchParameters parameters) {

        Long idSerie = parameters.getIdSerie();
        String filter = null;

        if (idSerie != null) {
            filter = "((" + COL_ID_SERIE + " = " + idSerie + ") OR ((" + COL_ID_SERIE + " IS NULL) AND (" + COL_ID_SERIES + " LIKE '%;" + idSerie + ";%')))";
        }

        return filter;
    }

    private String getKindOfGoodiesFilter(final DaoSearchParameters parameters) {

        String result = null;
        String kindOfGoody = parameters.getKindOfGoody();

        if (kindOfGoody != null) {
            List<String> kindsOfGoodies = new ArrayList<>();
            kindsOfGoodies.add(kindOfGoody);

            result = getKindOfGoodiesFilter(kindsOfGoodies);
        }

        return result;
    }

    private String getKindOfGoodiesFilter(final DaoSearchGoodiesParameters parameters) {

        return getKindOfGoodiesFilter(parameters.getKindsNames());
    }

    private String getKindOfGoodiesFilter(final List<String> kindsOfGoodies) {

        String filter = null;
        String kindOfGoody = null;

        if (kindsOfGoodies != null) {

            final int kinsCount = kindsOfGoodies.size();

            if (kinsCount == 1) {
                kindOfGoody = kindsOfGoodies.get(0);

                if (kindOfGoody != null) {
                    filter = "(" + COL_KIND_NAME + "='" + kindOfGoody + "')";
                }

            } else if (kinsCount > 1) {

                String s = "";

                for (String kind : kindsOfGoodies) {
                    if (kind != null) {
                        s += " OR (" + COL_KIND_NAME + "='" + kind + "')";
                    }
                }

                if (s.length() > 0) {
                    filter = "(" + s.substring(4) + ")";
                }
            }
        }

        return filter;
    }

    private String getNameFilter(final DaoSearchParameters parameters) {

        String nameFilter = parameters.getName();
        String filter = null;

        if (nameFilter != null) {
            if (nameFilter.length() == 1) {
                filter = "((" + COL_SEARCH_NAME + " LIKE '" + nameFilter + "%') OR (" + COL_SERIE_SEARCH_NAME + " LIKE '" + nameFilter + "%'))";
            } else {
                filter = "((" + COL_SEARCH_NAME + " LIKE '%" + nameFilter + "%') OR (" + COL_SERIE_SEARCH_NAME + " LIKE '%" + nameFilter + "%'))";
            }
        }

        return filter;
    }

    private String getPossessionFilter(final DaoSearchParameters parameters) {

        String filters = null;

        final boolean inPossession = parameters.getInPossession();
        final boolean wanted = parameters.getWanted();
        final boolean notWanted = parameters.getNotWanted();

        if (!(inPossession && wanted && notWanted)) {

            if (! (inPossession || wanted || notWanted)) {
                // Si aucun critère sélectionné
                throw new IllegalArgumentException("Aucun critère de possession sélectionné (en possession, recherché, non recherché).");

            } else {
                if (inPossession) {
                    filters = "OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.IN_POSSESSION + ")";
                }
                if (wanted) {
                    filters = "OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.WANTED + ") OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.IN_DELIVERY + ") OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.RESERVED + ")";
                }
                if (notWanted) {
                    filters = "OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.NOT_WANTED + ")";
                }

                if (filters.length() > 0) {
                    filters = "(" + filters.substring(3) + ")";
                }
            }
        }

        return filters;
    }

    private String getSearchDateFilter(final DaoSearchParameters parameters) {

        String filters = null;

        final boolean showNews = parameters.getNews();
        final boolean showComings = parameters.getComings();
        final boolean showOthers = parameters.getOthers();

        if (! (showNews && showComings && showOthers)) {

            final SqlDate today = parameters.getToday();
            final SqlDate firstNewsDate = new SqlDate(DateUtils.getDayBeforeADay(today.getDate(), parameters.getNewsDaysCount() - 1));

            if (! (showNews || showOthers || showComings)) {
                // Si aucun critère sélectionné
                throw new IllegalArgumentException("Aucun critère de date sélectionné (à paraitre, nouveauté, autre).");

            } else if (! (showNews || showOthers)) {
                // Si les à paraître seulement
                filters = COL_PARUTION_DATE + " > '" + today.getValue() + "'";

            } else if (! (showComings || showOthers)) {
                // Si les nouveautés seulement
                filters = "(" + COL_PARUTION_DATE + " >= '" + firstNewsDate.getValue() + "') AND (" + COL_PARUTION_DATE + " <= '" + today.getValue() + "')";

            } else if (! (showNews || showComings)) {
                // Si autres seulement
                filters = "(" + COL_PARUTION_DATE + " < '" + firstNewsDate.getValue() + "') OR (" + COL_PARUTION_DATE + " IS NULL)";

            } else if (!showOthers) {
                // Si les nouveautés et les à paraître
                filters = COL_PARUTION_DATE + " >= '" + firstNewsDate.getValue() + "'";

            } else if (!showNews) {
                // Si les autres et les à paraître
                filters = "(" + COL_PARUTION_DATE + " < '" + firstNewsDate.getValue() + "')  OR (" + COL_PARUTION_DATE + " IS NULL) OR (" + COL_PARUTION_DATE + " > '" + today.getValue() + "')";

            }  else {
                // Si les nouveautés et les autres
                filters = "(" + COL_PARUTION_DATE + " <= '" + today.getValue() + "') OR (" + COL_PARUTION_DATE + " IS NULL)";
            }

            filters = "(" + filters + ")";
        }

        return filters;
    }

    private String getAuthorFilter(final DaoSearchGoodiesParameters parameters) {

        Long idAuthor = parameters.getIdAuthor();
        String filter = null;

        if (idAuthor != null) {
            filter = "((" + COL_ID_AUTHOR + " = " + idAuthor + ") OR ((" + COL_ID_AUTHOR + " IS NULL) AND (" + COL_ID_AUTHORS + " LIKE '%;" + idAuthor + ";%')))";
        }

        return filter;
    }

    public List<String> getKinds() {

        String rqt = "SELECT DISTINCT " + COL_KIND_NAME + " FROM " + getTableName() + " ORDER BY " + COL_KIND_NAME + " ASC";

        return executeStringListResultQuery(rqt);

    }
}
