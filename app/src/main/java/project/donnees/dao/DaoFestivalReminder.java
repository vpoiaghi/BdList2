package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import framework.donnees.dao.TypedDao;
import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Festival;
import project.donnees.extendedBo.FestivalReminder;

/**
 *
 * Created by VINCENT on 05/03/2016.
 */
public class DaoFestivalReminder extends TypedDao<FestivalReminder> {

    private static int COL_POS = 2;

    public static final String COL_ID_FESTIVAL = "idFestival";
    public static final int POS_ID_FESTIVAL = COL_POS++;

    public static final String COL_ID_EDITOR = "idEditor";
    public static final int POS_ID_EDITOR = COL_POS++;

    public static final String COL_NAME = "name";
    public static final int POS_NAME = COL_POS++;

    public static final String COL_KIND = "kind";
    public static final int POS_KIND = COL_POS++;

    public static final String COL_COMMENTS = "comments";
    public static final int POS_COMMENTS = COL_POS++;

    public DaoFestivalReminder() {
        super();
    }

    @Override
    public String getTableName() {
        return "festivalReminder";
    }

    @Override
    public void setContentValues(final ContentValues values, final FestivalReminder festivalReminder) {

        values.put(COL_ID_FESTIVAL, festivalReminder.getIdFestival());
        values.put(COL_ID_EDITOR, festivalReminder.getIdEditor());
        values.put(COL_NAME, festivalReminder.getName());
        values.put(COL_KIND, festivalReminder.getKind());
        values.put(COL_COMMENTS, festivalReminder.getComments());
    }

    protected FestivalReminder cursorToBo(Cursor cursor) {

        FestivalReminder bo = new FestivalReminder();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setIdFestival(cursorToLong(cursor, POS_ID_FESTIVAL));
        bo.setIdEditor(cursorToLong(cursor, POS_ID_EDITOR));
        bo.setName(cursorToString(cursor, POS_NAME));
        bo.setKind(cursorToInteger(cursor, POS_KIND));
        bo.setComments(cursorToString(cursor, POS_COMMENTS));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_ID_FESTIVAL + " long not null, "
                + COL_ID_EDITOR + " long, "
                + COL_NAME + " text not null, "
                + COL_KIND + " int not null, "
                + COL_COMMENTS + " text";

    }

    @Override
    public List<FestivalReminder> getAll() {

        final String rqt = "SELECT * FROM ("
                + "SELECT *, upper(" + COL_NAME + ") AS sort_name FROM " + getTableName() + ")"
                + "ORDER BY sort_name ASC";

        return executeRawQuery(rqt);
    }

    public List<FestivalReminder> getByFestival(final Festival festival) {

        String rqt = "SELECT *"
                + " FROM " + getTableName()
                + " WHERE (" + COL_ID_FESTIVAL + " = " + festival.getId() + ")"
                + " ORDER BY " + COL_NAME + " ASC";

        return executeRawQuery(rqt);
    }

    public List<FestivalReminder> getByFestival(final Festival festival, final Editor editor) {

        List<FestivalReminder> result;

        if (editor == null) {
            result = getByFestival(festival);

        } else {

            DaoEditor daoEditor = new DaoEditor();
            String editorTableName = daoEditor.getTableName();

            String rqt = "SELECT *"
                    + " FROM " + getTableName()
                    + " LEFT JOIN " + editorTableName
                    + " ON (" + editorTableName + ".Id = " + COL_ID_EDITOR + ")"
                    + " WHERE (" + COL_ID_FESTIVAL + " = " + festival.getId() + ")"
                    + " AND (" + COL_ID_EDITOR + " = " + editor.getId() + ")"
                    + " ORDER BY " + editorTableName + "." + DaoEditor.COL_NAME + ", " + getTableName() + "." + COL_NAME + " ASC";

            result = executeRawQuery(rqt);
        }

        return result;
    }
}
