package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import project.donnees.extendedBo.Editor;
import framework.donnees.dao.TypedDao;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoEditor extends TypedDao<Editor> {

    private static int COL_POS = 2;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public DaoEditor() {
        super();
    }

    @Override
    public String getTableName() {
        return "editor";
    }

    @Override
    public void setContentValues(final ContentValues values, final Editor editor) {

        values.put(COL_NAME, editor.getName());

    }

    protected Editor cursorToBo(Cursor cursor) {

        Editor bo = new Editor();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setName(cursorToString(cursor, POS_NAME));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_NAME + " text not null";

    }

    @Override
    public List<Editor> getAll() {

        final String rqt
                = " SELECT *"
                + " FROM ("
                + " SELECT *, upper(" + COL_NAME + ") AS sort_name"
                + " FROM " + getTableName()
                + " )"
                + " ORDER BY sort_name ASC";

        return executeRawQuery(rqt);
    }

    public List<Editor> getByName(final String filter) {

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
