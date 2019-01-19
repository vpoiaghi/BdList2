package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import framework.tools.StringUtils;
import project.donnees.extendedBo.Edition;
import framework.donnees.dao.DaoFilters;
import framework.donnees.dao.TypedDao;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.search.DaoSearchParameters;
import project.donnees.search.SearchOrder;
import framework.tools.DateUtils;
import project.utils.PossessionStates;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoEdition extends TypedDao<Edition> {

    private static int COL_POS = 2;

    public static final String COL_ID_EDITOR = "idEditor";
    public static final int POS_ID_EDITOR = COL_POS++;

    public static final String COL_ID_SERIE = "idSerie";
    public static final int POS_ID_SERIE = COL_POS++;

    public static final String COL_ID_SERIES = "idSeries";
    public static final int POS_ID_SERIES = COL_POS++;

    public static final String COL_ID_TITLE = "idTitle";
    public static final int POS_ID_TITLE = COL_POS++;

    public static final String COL_ID_TITLES = "idTitles";
    public static final int POS_ID_TITLES = COL_POS++;

    public static final String COL_SERIE_NAME = "serieName";
    public static final int POS_SERIE_NAME = COL_POS++;

    public static final String COL_SERIE_SEARCH_NAME = "serieSearchName";
    public static final int POS_SERIE_SEARCH_NAME = COL_POS++;

    public static final String COL_ORDER_NUMBER = "orderNumber";
    public static final int POS_ORDER_NUMBER = COL_POS++;

    public static final String COL_COLLECTION = "collection";
    public static final int POS_COLLECTION = COL_POS++;

    public static final String COL_STATE = "state";
    public static final int POS_STATE = COL_POS++;

    public static final String COL_POSSESSION_STATE = "possessionState";
    public static final int POS_POSSESSION_STATE = COL_POS++;

    public static final String COL_EDITION_NUMBER = "editionNumber";
    public static final int POS_EDITION_NUMBER = COL_POS++;

    public static final String COL_ISBN = "isbn";
    public static final int POS_ISBN = COL_POS++;

    public static final String COL_INTEGRAL = "isIntegral";
    public static final int POS_INTEGRAL = COL_POS++;

    public static final String COL_SET = "isSet";
    public static final int POS_SET = COL_POS++;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_SEARCH_NAME = "searchName";
    public static final int POS_SEARCH_NAME = COL_POS++;

    public static final String COL_PARUTION_DATE = "parutionDate";
    public static final int POS_PARUTION_DATE = COL_POS++;

    public static final String COL_VERSION_NUMBER = "versionNumber";
    public static final int POS_VERSION_NUMBER = COL_POS++;

    public static final String COL_SPECIAL_EDITION = "specialEdition";
    public static final int POS_SPECIAL_EDITION = COL_POS++;

    public static final String COL_BOARDS_COUNT = "boardsCount";
    public static final int POS_BOARDS_COUNT = COL_POS++;

    public static final String COL_PRINTING_MAX = "printingMax";
    public static final int POS_PRINTING_MAX = COL_POS++;

    public static final String COL_BOARDS_COLOR = "boardsColor";
    public static final int POS_BOARDS_COLOR = COL_POS++;

    public static final String COL_TEXT_AUTHOR_NAME = "textAuthorName";
    public static final int POS_TEXT_AUTHOR_NAME = COL_POS++;

    public static final String COL_DRAW_AUTHOR_NAME = "drawAuthorName";
    public static final int POS_DRAW_AUTHOR_NAME = COL_POS++;

    public static final String COL_COLOR_AUTHOR_NAME = "colorAuthorName";
    public static final int POS_COLOR_AUTHOR_NAME = COL_POS++;

    public static final String COL_WITH_AUTOGRAPH = "isWithAutograph";
    public static final int POS_WITH_AUTOGRAPH = COL_POS++;

    public static final String COL_HAS_ANOTHER_EDITIONS = "hasAnotherEditions";
    public static final int POS_HAS_ANOTHER_EDITIONS = COL_POS++;

    public static final String COL_EDITOR_NAME = "editorName";
    public static final int POS_EDITOR_NAME = COL_POS++;



    public DaoEdition() {
        super();
    }

    @Override
    public String getTableName() {
        return "edition";
    }

    @Override
    public void setContentValues(final ContentValues values, final Edition edition) {

        values.put(COL_ID_EDITOR, edition.getIdEditor());
        values.put(COL_ID_SERIE, edition.getIdSerie());
        values.put(COL_ID_SERIES, edition.getIdSeries());
        values.put(COL_ID_TITLE, edition.getIdTitle());
        values.put(COL_ID_TITLES, edition.getIdTitles());
        values.put(COL_SERIE_NAME, edition.getSerieName());
        values.put(COL_SERIE_SEARCH_NAME, edition.getSerieSearchName());
        values.put(COL_ORDER_NUMBER, edition.getOrderNumber());
        values.put(COL_COLLECTION, edition.getCollection());
        values.put(COL_STATE, edition.getState());
        values.put(COL_POSSESSION_STATE, edition.getPossessionState());
        values.put(COL_EDITION_NUMBER, edition.getEditionNumber());
        values.put(COL_ISBN, edition.getIsbn());
        values.put(COL_INTEGRAL, edition.isIntegral());
        values.put(COL_SET, edition.isSet());
        values.put(COL_NAME, edition.getName());
        values.put(COL_SEARCH_NAME, edition.getSearchName());
        values.put(COL_PARUTION_DATE, edition.getParutionDate().getValue());
        values.put(COL_VERSION_NUMBER, edition.getVersionNumber());
        values.put(COL_SPECIAL_EDITION, edition.getSpecialEdition());
        values.put(COL_BOARDS_COUNT, edition.getBoardsCount());
        values.put(COL_PRINTING_MAX, edition.getPrintingMax());
        values.put(COL_BOARDS_COLOR, edition.getBoardsColor());
        values.put(COL_TEXT_AUTHOR_NAME, edition.getTextAuthorName());
        values.put(COL_DRAW_AUTHOR_NAME, edition.getDrawAuthorName());
        values.put(COL_COLOR_AUTHOR_NAME, edition.getColorAuthorName());
        values.put(COL_WITH_AUTOGRAPH, edition.isWithAutograph());
        values.put(COL_HAS_ANOTHER_EDITIONS, edition.hasAnotherEditions());
        values.put(COL_EDITOR_NAME, edition.getEditorName());

    }

    protected Edition cursorToBo(Cursor cursor) {

        Edition bo = new Edition();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setIdEditor(cursorToLong(cursor, POS_ID_EDITOR));
        bo.setIdSerie(cursorToLong(cursor, POS_ID_SERIE));
        bo.setIdSeries(cursorToString(cursor, POS_ID_SERIES));
        bo.setIdTitle(cursorToLong(cursor, POS_ID_TITLE));
        bo.setIdTitles(cursorToString(cursor, POS_ID_TITLES));
        bo.setSerieName(cursorToString(cursor, POS_SERIE_NAME));
        bo.setSerieSearchName(cursorToString(cursor, POS_SERIE_SEARCH_NAME));
        bo.setOrderNumber(cursorToInteger(cursor, POS_ORDER_NUMBER));
        bo.setCollection(cursorToString(cursor, POS_COLLECTION));
        bo.setState(cursorToInteger(cursor, POS_STATE));
        bo.setPossessionState(cursorToInteger(cursor, POS_POSSESSION_STATE));
        bo.setEditionNumber(cursorToString(cursor, POS_EDITION_NUMBER));
        bo.setIsbn(cursorToString(cursor, POS_ISBN));
        bo.setIntegral(cursorToBoolean(cursor, POS_INTEGRAL));
        bo.setSet(cursorToBoolean(cursor, POS_SET));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setSearchName(cursorToString(cursor, POS_SEARCH_NAME));
        bo.setParutionDate(cursorToDate(cursor, POS_PARUTION_DATE));
        bo.setVersionNumber(cursorToInteger(cursor, POS_VERSION_NUMBER));
        bo.setSpecialEdition(cursorToString(cursor, POS_SPECIAL_EDITION));
        bo.setBoardsCount(cursorToInteger(cursor, POS_BOARDS_COUNT));
        bo.setPrintingMax(cursorToInteger(cursor, POS_PRINTING_MAX));
        bo.setBoardsColor(cursorToString(cursor, POS_BOARDS_COLOR));
        bo.setTextAuthorName(cursorToString(cursor, POS_TEXT_AUTHOR_NAME));
        bo.setDrawAuthorName(cursorToString(cursor, POS_DRAW_AUTHOR_NAME));
        bo.setColorAuthorName(cursorToString(cursor, POS_COLOR_AUTHOR_NAME));
        bo.setWithAutograph(cursorToBoolean(cursor, POS_WITH_AUTOGRAPH));
        bo.setHasAnotherEditions(cursorToBoolean(cursor, POS_HAS_ANOTHER_EDITIONS));
        bo.setEditorName(cursorToString(cursor, POS_EDITOR_NAME));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_ID_EDITOR + " long not null,"
                + COL_ID_SERIE + " long,"
                + COL_ID_SERIES + " text,"
                + COL_ID_TITLE + " long,"
                + COL_ID_TITLES + " text,"
                + COL_SERIE_NAME + " text not null,"
                + COL_SERIE_SEARCH_NAME + " text not null,"
                + COL_ORDER_NUMBER + " int not null,"
                + COL_COLLECTION + " text,"
                + COL_STATE + " int,"
                + COL_POSSESSION_STATE + " int not null,"
                + COL_EDITION_NUMBER + " text,"
                + COL_ISBN + " text,"
                + COL_INTEGRAL + " boolean not null,"
                + COL_SET + " boolean not null,"
                + COL_NAME + " text not null,"
                + COL_SEARCH_NAME + " text not null,"
                + COL_PARUTION_DATE + " text,"
                + COL_VERSION_NUMBER + " int,"
                + COL_SPECIAL_EDITION + " text,"
                + COL_BOARDS_COUNT + " int,"
                + COL_PRINTING_MAX + " int,"
                + COL_BOARDS_COLOR + " text,"
                + COL_TEXT_AUTHOR_NAME + " text,"
                + COL_DRAW_AUTHOR_NAME + " text,"
                + COL_COLOR_AUTHOR_NAME + " text,"
                + COL_WITH_AUTOGRAPH + " boolean not null,"
                + COL_HAS_ANOTHER_EDITIONS + " boolean not null,"
                + COL_EDITOR_NAME + " text";
    }

    public List<Edition> getByIdTitle(Long idTitle) {

        String rqt = "SELECT * FROM " + getTableName()
                + " WHERE (" + COL_ID_TITLE + "=" + idTitle + ")"
                + getSearchOrderPart(SearchOrder.SerieDesc);

        return executeRawQuery(rqt);
    }

    public List<Edition> getByIdTitles(List<Long> idTitlesList) {

        List<Edition> editionsList;

        if (idTitlesList != null && idTitlesList.size() > 0) {

            StringBuilder s = new StringBuilder();

            for (Long idTitle : idTitlesList) {
                s.append(",").append(idTitle.toString());
            }

            String rqt = "SELECT * FROM " + getTableName()
                    + " WHERE (" + COL_ID_TITLE + " IN (" + s.substring(1) + "))"
                    + getSearchOrderPart(SearchOrder.SerieDesc);

            editionsList = executeRawQuery(rqt);

        } else {
            editionsList = new ArrayList<>();
        }

        return editionsList;
    }

    public List<Edition> getByAuthor(Long idAuthor) {

        String rqt = "SELECT * FROM " + getTableName()
                + " WHERE ((' ' || textAuthorName) LIKE '% " + idAuthor + ",%')"
                + " OR ((' ' || drawAuthorName) LIKE '% " + idAuthor + ",%')"
                + " OR ((' ' || colorAuthorName) LIKE '% " + idAuthor + ",%')"
                + getSearchOrderPart(SearchOrder.SerieDesc);

        return executeRawQuery(rqt);

    }

    public List<Edition> getSimpleEditionsByIdTitles(List<Long> idTitlesList) {

        List<Edition> editionsList;

        if (idTitlesList != null && idTitlesList.size() > 0) {

            StringBuilder s = new StringBuilder();

            for (Long idTitle : idTitlesList) {
                s.append(",").append(idTitle.toString());
            }

            String rqt = "SELECT * FROM " + getTableName()
                    + " WHERE (" + COL_ID_TITLE + " IN (" + s.substring(1) + ")) AND (NOT (" + COL_INTEGRAL + " OR " + COL_SET + "))"
                    + getSearchOrderPart(SearchOrder.SerieDesc);

            editionsList = executeRawQuery(rqt);

        } else {
            editionsList = new ArrayList<>();
        }

        return editionsList;
    }

    public List<Edition> getPress() {

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

    public List<Edition> getToUseAsEvent() {

        final SqlDate lastReservedDate = new SqlDate(DateUtils.getDayAfterADay(DateUtils.getToday(), 30));

        String rqt = "SELECT * FROM " + getTableName() +
                " WHERE (" + COL_POSSESSION_STATE + " = " + PossessionStates.TO_RESERVE_AT_CULTURA + ")" +
                " OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.TO_ORDER_AT_BDFUGUE + ")" +
                " OR ((" + COL_POSSESSION_STATE + " = " + PossessionStates.RESERVED + ") AND (" + COL_PARUTION_DATE + " <= '" + lastReservedDate.getValue() + "'))";

        return executeRawQuery(rqt);
    }

    public List<Edition> search(final DaoSearchParameters parameters) {

        List<Edition> result = null;

        if (StringUtils.isNullOrEmpty(parameters.getKindOfGoody())) {

            try {

                final String filters = getSearchFiltersPart(parameters);
                final String orderPart = getSearchOrderPart(parameters);

                result = executeRawQuery(" SELECT * FROM " + getTableName() + filters + orderPart);

            } catch (IllegalArgumentException e) {
                // rien à faire
            }

        }

        if (result == null) {
            result = new ArrayList<>();
        }

        return result;

    }

    private String getSearchFiltersPart(final DaoSearchParameters parameters){

        DaoFilters filters = new DaoFilters();

        filters.add(getNameFilter(parameters));
        filters.add(getEditorFilter(parameters));
        filters.add(getSerieFilter(parameters));
        filters.add(getPossessionFilter(parameters));
        filters.add(getSearchDateFilter(parameters));

        return filters.toString();
    }

    private String getSearchOrderPart(final DaoSearchParameters parameters){
        return getSearchOrderPart(parameters.getOrder());
    }

    private String getSearchOrderPart(final SearchOrder searchOrder){

        final String orderPart;

        if (searchOrder != null) {
            switch (searchOrder) {
                case SerieDesc:
                    orderPart = " ORDER BY " + COL_SERIE_NAME + " ASC, " + COL_SET + ", " + COL_INTEGRAL + ", " + COL_ORDER_NUMBER + " ASC";
                    break;
                case ParutionDateAsc:
                    orderPart = " ORDER BY " + COL_PARUTION_DATE + " ASC, " + COL_SERIE_NAME + " ASC, " + COL_SET + ", " + COL_INTEGRAL + ", " + COL_ORDER_NUMBER + " ASC";
                    break;
                case ParutionDateDesc:
                    orderPart = " ORDER BY " + COL_PARUTION_DATE + " DESC, " + COL_SERIE_NAME + " ASC, " + COL_SET + ", " + COL_INTEGRAL + ", " + COL_ORDER_NUMBER + " ASC";
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

            } else if (!wanted && !notWanted) {
                // Si les en possession seulement
                filters = COL_POSSESSION_STATE + " = " + PossessionStates.IN_POSSESSION;

            } else if (!inPossession && !notWanted) {
                // Si les recherchés seulement
                filters = "(" + COL_POSSESSION_STATE + " != " + PossessionStates.IN_POSSESSION + ") AND ("  + COL_POSSESSION_STATE + " != " + PossessionStates.NOT_WANTED + ")" ;

            } else if (!inPossession && !wanted) {
                // Si les non recherchés seulement
                filters = COL_POSSESSION_STATE + " = " + PossessionStates.NOT_WANTED;

            } else if (inPossession && wanted) {
                // Si les en possession et les recherchés
                filters = "(" + COL_POSSESSION_STATE + " != " + PossessionStates.NOT_WANTED + ")";

            } else if (inPossession && notWanted) {
                // Si les en possession et les non recherchés
                filters = "(" + COL_POSSESSION_STATE + " = " + PossessionStates.IN_POSSESSION + ") OR (" + COL_POSSESSION_STATE + " = " + PossessionStates.NOT_WANTED + ")";

            } else if (wanted && notWanted) {
                // Si les recherchés et les non recherchés (les manquants en somme)
                filters = "(" + COL_POSSESSION_STATE + " != " + PossessionStates.IN_POSSESSION + ")";
            }

            filters = "(" + filters + ")";
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

}
