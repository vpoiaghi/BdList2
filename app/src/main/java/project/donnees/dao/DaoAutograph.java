package project.donnees.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.List;

import project.donnees.extendedBo.Autograph;
import framework.donnees.dao.TypedDao;


/**
 *
 * Created by VINCENT on 24/04/2016.
 */
public class DaoAutograph extends TypedDao<Autograph> {

    private static int COL_POS = 2;

    public static final String COL_ID_EDITION = "idEdition";
    public static final int POS_ID_EDITION = COL_POS++;

    public static final String COL_ID_GOODY = "idGoody";
    public static final int POS_ID_GOODY = COL_POS++;

    public static final String COL_ID_AUTHOR = "idAuthor";
    public static final int POS_ID_AUTHOR = COL_POS++;

    public static final String COL_ID_AUTHORS = "idAuthors";
    public static final int POS_ID_AUTHORS = COL_POS++;

    public static final String COL_DATE = "autographDate";
    public static final int POS_DATE = COL_POS++;

    public static final String COL_PLACE = "place";
    public static final int POS_PLACE = COL_POS++;

    public static final String COL_EVENT = "event";
    public static final int POS_EVENT = COL_POS++;

    public static final String COL_COMMENTS = "comments";
    public static final int POS_COMMENTS = COL_POS++;


    public DaoAutograph() {
        super();
    }

    @Override
    public String getTableName() {
        return "autograph";
    }

    @Override
    protected void setContentValues(ContentValues values, Autograph autograph) {

        values.put(COL_ID_EDITION, autograph.getIdEdition());
        values.put(COL_ID_GOODY, autograph.getIdGoody());
        values.put(COL_ID_AUTHOR, autograph.getIdAuthor());
        values.put(COL_ID_AUTHORS, autograph.getIdAuthors());
        values.put(COL_DATE, autograph.getDate().getValue());
        values.put(COL_PLACE, autograph.getPlace());
        values.put(COL_EVENT, autograph.getEvent());
        values.put(COL_COMMENTS, autograph.getComments());

    }

    @Override
    protected Autograph cursorToBo(Cursor cursor) {

        Autograph bo = new Autograph();
        bo.setId(cursorToLong(cursor, POS_ID));
        bo.setTsp(cursorToDate(cursor, POS_TSP));
        bo.setIdEdition(cursorToLong(cursor, POS_ID_EDITION));
        bo.setIdGoody(cursorToLong(cursor, POS_ID_GOODY));
        bo.setIdAuthor(cursorToLong(cursor, POS_ID_AUTHOR));
        bo.setIdAuthors(cursorToString(cursor, POS_ID_AUTHORS));
        bo.setDate(cursorToDate(cursor, POS_DATE));
        bo.setPlace(cursorToString(cursor, POS_PLACE));
        bo.setEvent(cursorToString(cursor, POS_EVENT));
        bo.setComments(cursorToString(cursor, POS_COMMENTS));

        return bo;
    }

    @Override
    protected String getColumnsCreateRequest() {

        return COL_ID_EDITION + " long,"
                + COL_ID_GOODY + " long,"
                + COL_ID_AUTHOR + " long,"
                + COL_ID_AUTHORS + " text,"
                + COL_DATE + " text,"
                + COL_PLACE + " text,"
                + COL_EVENT + " text,"
                + COL_COMMENTS + " text";

    }

    public List<Autograph> getByEdition(final Long idEdition) {

        String rqt = "SELECT * FROM " + getTableName()
                + " WHERE (" + COL_ID_EDITION + " IN (" + idEdition.toString() + "))"
                + " ORDER BY " + COL_DATE;

        return executeRawQuery(rqt);

    }

    public List<Autograph> getByGoody(final Long idGoody) {

        String rqt = "SELECT * FROM " + getTableName()
                + " WHERE (" + COL_ID_GOODY + " IN (" + idGoody.toString() + "))"
                + " ORDER BY " + COL_DATE;

        return executeRawQuery(rqt);

    }

}
