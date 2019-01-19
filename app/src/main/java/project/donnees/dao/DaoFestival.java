package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import framework.donnees.dao.TypedDao;
import project.donnees.extendedBo.Festival;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoFestival extends TypedDao<Festival> {

    private static int COL_POS = 2;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_BEGIN_DATE = "beginDate";
    public static final int POS_BEGIN_DATE = COL_POS++;

    public static final String COL_END_DATE = "endDate";
    public static final int POS_END_DATE = COL_POS++;


    public DaoFestival() {
        super();
    }

    @Override
    public String getTableName() {
        return "festival";
    }

    @Override
    public void setContentValues(final ContentValues values, final Festival festival) {

        values.put(COL_NAME, festival.getName());
        values.put(COL_BEGIN_DATE, festival.getBeginDate().getValue());
        values.put(COL_END_DATE, festival.getEndDate().getValue());

    }

    protected Festival cursorToBo(Cursor cursor) {

        Festival bo = new Festival();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setBeginDate(cursorToDate(cursor, POS_BEGIN_DATE));
        bo.setEndDate(cursorToDate(cursor, POS_END_DATE));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_NAME + " text not null,"
                + COL_BEGIN_DATE + " text,"
                + COL_END_DATE + " text";

    }

    @Override
    public List<Festival> getAll() {

        final String rqt
                = " SELECT *"
                + " FROM ("
                + " SELECT *, upper(" + COL_NAME + ") AS sort_name"
                + " FROM " + getTableName()
                + " )"
                + " ORDER BY sort_name ASC";

        return executeRawQuery(rqt);
    }

    public List<Festival> getByName(final String filter) {

        String filterName = null;

        if (filter != null) {
            filterName = filter.trim().toUpperCase();

            if (filterName.length() == 0) {
                filterName = null;
            }
        }

        String rqt
                = " SELECT *"
                + " FROM ("
                + " SELECT *, upper(" + COL_NAME + ") AS sort_name"
                + " FROM " + getTableName()
                + " )";

        if (filterName != null) {
            rqt += " WHERE sort_name LIKE '%" + filterName + "%'";
        }

        rqt += " ORDER BY sort_name ASC";

        return executeRawQuery(rqt);
    }

}
