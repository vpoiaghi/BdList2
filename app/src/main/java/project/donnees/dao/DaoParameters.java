package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import project.donnees.extendedBo.Parameters;
import framework.donnees.dao.TypedDao;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoParameters extends TypedDao<Parameters> {

    private static int COL_POS = 2;

    public static final String COL_DATA_UPDATE_DATE = "dataUpdateDate";
    public static final int POS_DATA_UPDATE_DATE = COL_POS++;

    public static final String COL_NEWS_DAYS_COUNT = "newsDaysCount";
    public static final int POS_NEWS_DAYS_COUNT = COL_POS++;

    public static final String COL_FIRST_COMING_DATE = "firstComingDate";
    public static final int POS_FIRST_COMING_DATE = COL_POS++;

    public static final String COL_USE_DEFAULT_FIRST_COMING_DATE = "useDefaultFirstComingDate";
    public static final int POS_USE_DEFAULT_FIRST_COMING_DATE = COL_POS++;

    public static final String COL_EVENTS_PAST_DAYS_COUNT = "eventPastDaysCount";
    public static final int POS_EVENTS_PAST_DAYS_COUNT = COL_POS++;


    public DaoParameters() {
        super();
    }

    @Override
    public String getTableName() {
        return "parameters";
    }

    @Override
    public void setContentValues(final ContentValues values, final Parameters parameters) {

        values.put(COL_DATA_UPDATE_DATE, parameters.getDataUpdateDate().getValue());
        values.put(COL_NEWS_DAYS_COUNT, parameters.getNewsDaysCount());
        values.put(COL_FIRST_COMING_DATE, parameters.getFirstComingDate().getValue());
        values.put(COL_USE_DEFAULT_FIRST_COMING_DATE, parameters.getUseDefaultComingDate());
        values.put(COL_EVENTS_PAST_DAYS_COUNT, parameters.getEventsPastDaysCount());
    }

    protected Parameters cursorToBo(Cursor cursor) {

        Parameters bo = new Parameters();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setDataUpdateDate(cursorToDate(cursor, POS_DATA_UPDATE_DATE));
        bo.setNewsDaysCount(cursorToInteger(cursor, POS_NEWS_DAYS_COUNT));
        bo.setFirstComingDate(cursorToDate(cursor, POS_FIRST_COMING_DATE));
        bo.setUseDefaultComingDate(cursorToBoolean(cursor, POS_USE_DEFAULT_FIRST_COMING_DATE));
        bo.setEventsPastDaysCount(cursorToInteger(cursor, POS_EVENTS_PAST_DAYS_COUNT));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_DATA_UPDATE_DATE + " text not null default '19000101_000000',"
                + COL_NEWS_DAYS_COUNT + " int not null,"
                + COL_FIRST_COMING_DATE + " text not null,"
                + COL_USE_DEFAULT_FIRST_COMING_DATE + " boolean not null,"
                + COL_EVENTS_PAST_DAYS_COUNT + " int not null";

    }

    public Parameters getParameters() {

        Parameters parameters = null;

        List<Parameters> parametersList = executeRawQuery("SELECT * FROM " + getTableName());

        if (parametersList.size() > 0) {
            parameters = parametersList.get(0);
        }

        return parameters;
    }

}
