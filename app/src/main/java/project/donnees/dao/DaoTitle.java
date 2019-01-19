package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import project.donnees.extendedBo.Title;
import framework.donnees.dao.TypedDao;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoTitle extends TypedDao<Title> {

    private static int COL_POS = 2;

    public static final String COL_ID_SERIE = "idSerie";
    public static final int POS_ID_SERIE = COL_POS++;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_ORDER_NUMBER = "orderNumber";
    public static final int POS_ORDER_NUMBER = COL_POS++;

    public static final String COL_STORY = "story";
    public static final int POS_STORY = COL_POS++;

    public static final String COL_OUT_SERIE = "outSerie";
    public static final int POS_OUT_SERIE = COL_POS++;

    public static final String COL_IN_POSSESSION = "inPossession";
    public static final int POS_IN_POSSESSION = COL_POS++;


    public DaoTitle() {
        super();
    }

    @Override
    public String getTableName() {
        return "title";
    }

    @Override
    public void setContentValues(final ContentValues values, final Title title) {

        values.put(COL_ID_SERIE, title.getIdSerie());
        values.put(COL_NAME, title.getName());
        values.put(COL_ORDER_NUMBER, title.getOrderNumber());
        values.put(COL_STORY, title.getStory());
        values.put(COL_OUT_SERIE, title.isOutSerie());
        values.put(COL_IN_POSSESSION, title.isInPossession());
    }

    protected Title cursorToBo(Cursor cursor) {

        Title bo = new Title();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setIdSerie(cursorToLong(cursor, POS_ID_SERIE));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setOrderNumber(cursorToInteger(cursor, POS_ORDER_NUMBER));
        bo.setStory(cursorToString(cursor, POS_STORY));
        bo.setOutSerie(cursorToBoolean(cursor, POS_OUT_SERIE));
        bo.setInPossession(cursorToBoolean(cursor, POS_IN_POSSESSION));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_ID_SERIE + " long not null,"
                + COL_NAME + " text not null,"
                + COL_ORDER_NUMBER + " int not null,"
                + COL_STORY + " text,"
                + COL_OUT_SERIE + " boolean not null,"
                + COL_IN_POSSESSION + " boolean not null";
    }

    public List<Title> getBySerie(final Long idSerie) {

        final String query = " SELECT * FROM " + getTableName() + " WHERE " + COL_ID_SERIE + " = " + idSerie;

        return executeRawQuery(query);

    }

}
