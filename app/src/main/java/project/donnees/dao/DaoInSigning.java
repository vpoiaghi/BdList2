package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;

import framework.donnees.dao.TypedDao;
import project.donnees.bo.botypes.SqlDate;
import project.donnees.extendedBo.Editor;
import project.donnees.extendedBo.Festival;
import project.donnees.extendedBo.InSigning;

/**
 * Created by VINCENT on 15/12/2018.
 *
 */
public class DaoInSigning extends TypedDao<InSigning> {

    private static int COL_POS = 2;

    public static final String COL_ID_FESTIVAL = "idFestival";
    public static final int POS_ID_FESTIVAL = COL_POS++;

    public static final String COL_ID_AUTHOR = "idAuthor";
    public static final int POS_ID_AUTHOR = COL_POS++;

    public static final String COL_ID_EDITOR = "idEditor";
    public static final int POS_ID_EDITOR = COL_POS++;

    public static final String COL_START_HOUR = "startHour";
    public static final int POS_START_HOUR = COL_POS++;

    public static final String COL_END_HOUR = "endHour";
    public static final int POS_END_HOUR = COL_POS++;

    public static final String COL_COMMENTS = "comments";
    public static final int POS_COMENTS = COL_POS++;

    public DaoInSigning() {
        super();
    }

    @Override
    public String getTableName() {
        return "insigning";
    }

    @Override
    public void setContentValues(final ContentValues values, final InSigning inSigning) {

        values.put(COL_ID_FESTIVAL, inSigning.getIdFestival());
        values.put(COL_ID_AUTHOR, inSigning.getIdAuthor());
        values.put(COL_ID_EDITOR, inSigning.getIdEditor());
        values.put(COL_START_HOUR, inSigning.getBegin().getValue());
        values.put(COL_END_HOUR, inSigning.getEnd().getValue());
        values.put(COL_COMMENTS, inSigning.getComments());

    }

    protected InSigning cursorToBo(Cursor cursor) {

        InSigning bo = new InSigning();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setIdFestival(cursorToLong(cursor, POS_ID_FESTIVAL));
        bo.setIdAuthor(cursorToLong(cursor, POS_ID_AUTHOR));
        bo.setIdEditor(cursorToLong(cursor, POS_ID_EDITOR));
        bo.setBegin(cursorToDate(cursor, POS_START_HOUR));
        bo.setEnd(cursorToDate(cursor, POS_END_HOUR));
        bo.setComments(cursorToString(cursor, POS_COMENTS));

        return bo;
    }

    @Override
    public String getColumnsCreateRequest() {

        return COL_ID_FESTIVAL + " long not null, "
                + COL_ID_AUTHOR + " long not null, "
                + COL_ID_EDITOR + " long, "
                + COL_START_HOUR + " text not null, "
                + COL_END_HOUR + " text not null, "
                + COL_COMMENTS + " text";
    }

    public List<InSigning> getByFestival(final Festival festival, @NonNull final Date day) {

        final SqlDate sqlDayFrom = new SqlDate(day, 0, 0, 1);
        final SqlDate sqlDayTo = new SqlDate(day, 23, 59, 59);

        String rqt = "SELECT *"
                + " FROM " + getTableName()
                + " WHERE (" + COL_ID_FESTIVAL + " = " + festival.getId() + ")"
                + " AND (" + COL_START_HOUR + " >= '" + sqlDayFrom.getValue() + "')"
                + " AND (" + COL_START_HOUR + " <= '" + sqlDayTo.getValue() + "')"
                + " ORDER BY " + COL_START_HOUR + " ASC, " + COL_END_HOUR + " ASC";

        return executeRawQuery(rqt);
    }

    public List<InSigning> getByFestival(@NonNull final Festival festival, final Editor editor, @NonNull final Date day) {

        final List<InSigning> result;

        if (editor == null) {
            result = getByFestival(festival, day);

        } else {

            final SqlDate sqlDayFrom = new SqlDate(day, 0, 0, 1);
            final SqlDate sqlDayTo = new SqlDate(day, 23, 59, 59);

            final DaoEditor daoEditor = new DaoEditor();
            final String editorTableName = daoEditor.getTableName();

            String rqt = "SELECT *"
                    + " FROM " + getTableName()
                    + " LEFT JOIN " + editorTableName
                    + " ON (" + editorTableName + ".Id = " + COL_ID_EDITOR + ")"
                    + " WHERE (" + COL_ID_FESTIVAL + " = " + festival.getId() + ")"
                    + " AND (" + COL_ID_EDITOR + " = " + editor.getId() + ")"
                    + " AND (" + COL_START_HOUR + " >= '" + sqlDayFrom.getValue() + "')"
                    + " AND (" + COL_START_HOUR + " <= '" + sqlDayTo.getValue() + "')"
                    + " ORDER BY " + COL_START_HOUR + " ASC, " + COL_END_HOUR + " ASC";

            result = executeRawQuery(rqt);
        }

        return result;
    }

}
